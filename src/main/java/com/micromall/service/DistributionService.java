package com.micromall.service;

import com.google.common.collect.Maps;
import com.micromall.repository.CommissionRecordMapper;
import com.micromall.repository.DistributionRelationMapper;
import com.micromall.repository.OrderMapper;
import com.micromall.repository.WithdrawApplyRecordMapper;
import com.micromall.repository.entity.CashAccount;
import com.micromall.repository.entity.DistributionRelation;
import com.micromall.repository.entity.Member;
import com.micromall.repository.entity.common.WithdrawStatus;
import com.micromall.service.vo.DistributionMember;
import com.micromall.utils.Condition.Criteria;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zhangzxiang91@gmail.com
 * @date 2016/04/24.
 */
@Service
@Transactional
public class DistributionService {

	@Resource
	private CashAccountService         cashAccountService;
	@Resource
	private WithdrawApplyRecordMapper  withdrawApplyRecordMapper;
	@Resource
	private OrderMapper                orderMapper;
	@Resource
	private CommissionRecordMapper     commissionRecordMapper;
	@Resource
	private DistributionRelationMapper distributionRelationMapper;
	@Resource
	private MemberService              memberService;

	public void relation(int uid, int lowerUid) {
		Member member = memberService.get(uid);
		if (distributionRelationMapper
				.countByWhereClause(Criteria.create().andEqualTo("uid", member.getId()).andEqualTo("lower_uid", lowerUid).build()) == 0) {
			DistributionRelation relation = new DistributionRelation(member.getId(), lowerUid, 1, new Date());
			distributionRelationMapper.insert(relation);
		}
		if (member.getParentUid() != null && distributionRelationMapper
				.countByWhereClause(Criteria.create().andEqualTo("uid", member.getParentUid()).andEqualTo("lower_uid", lowerUid).build()) == 0) {
			DistributionRelation relation = new DistributionRelation(member.getParentUid(), lowerUid, 2, new Date());
			distributionRelationMapper.insert(relation);
		}
	}

	@Transactional(readOnly = true)
	public Map<String, Object> commissionStat(int uid) {
		Map<String, Object> data = Maps.newHashMap();

		CashAccount cashAccount = cashAccountService.getCashAccount(uid);
		//提现中佣金
		BigDecimal withdrawOf = withdrawApplyRecordMapper
				.withdrawStat(Criteria.create().andEqualTo("uid", uid).andEqualTo("status", WithdrawStatus.待审核).build());
		//已提现佣金
		BigDecimal hasWithdraw = withdrawApplyRecordMapper
				.withdrawStat(Criteria.create().andEqualTo("uid", uid).andEqualTo("status", WithdrawStatus.审核通过).build());

		data.put("commissionIncome", commissionRecordMapper.withdrawStat(uid));//佣金总收入
		data.put("canWithdraw", cashAccount.getCommission());//可提现佣金
		data.put("hasWithdraw", hasWithdraw);//已提现佣金
		data.put("withdrawOf", withdrawOf);//提现中佣金
		data.put("totalSales", cashAccount.getTotalSales());//销售总额

		Map<String, Object> lv1 = Maps.newHashMap();
		lv1.put("waitPay", new BigDecimal(0));//未付款订单
		lv1.put("waitDelivery", new BigDecimal(0));//待发货订单
		lv1.put("waitReceive", new BigDecimal(0));//待收货订单
		lv1.put("complete", new BigDecimal(0));//已完成订单
		lv1.put("refund", new BigDecimal(0));//已退款订单
		lv1.put("closed", new BigDecimal(0));//已取消订单
		Map<String, Object> lv2 = Maps.newHashMap();
		lv2.put("waitPay", new BigDecimal(0));//未付款订单
		lv2.put("waitDelivery", new BigDecimal(0));//待发货订单
		lv2.put("waitReceive", new BigDecimal(0));//待收货订单
		lv2.put("complete", new BigDecimal(0));//已完成订单
		lv2.put("refund", new BigDecimal(0));//已退款订单
		lv2.put("closed", new BigDecimal(0));//已取消订单
		data.put("lv1", lv1);//一级分销商
		data.put("lv2", lv2);//一级分销商

		List<Map<String, Object>> _lv1 = orderMapper.distributionOrderAmountSum(uid, 1);
		List<Map<String, Object>> _lv2 = orderMapper.distributionOrderAmountSum(uid, 2);

		// 一级分销商：
		for (Map<String, Object> map : _lv1) {
			lv1.put((String)map.get("name"), map.get("amount"));
		}
		// 二级分销商：
		for (Map<String, Object> map : _lv2) {
			lv2.put((String)map.get("name"), map.get("amount"));
		}
		return data;
	}

	@Transactional(readOnly = true)
	public List<DistributionMember> lowerDistributorsList(int uid, Integer level, RowBounds bounds) {
		return distributionRelationMapper.selectLowerMembers(uid, level, bounds);
	}

	@Transactional(readOnly = true)
	public Map<String, Object> lowerDistributorsStat(int uid) {
		int lv1 = distributionRelationMapper.lowerMemberStat(uid, 1);
		int lv2 = distributionRelationMapper.lowerMemberStat(uid, 2);
		Map<String, Object> data = Maps.newHashMap();
		data.put("all", (lv1 + lv2));// 分销商总数
		data.put("lv1", lv1);//一级分销商数量
		data.put("lv2", lv2);//二级分销商数量
		return data;
	}
}
