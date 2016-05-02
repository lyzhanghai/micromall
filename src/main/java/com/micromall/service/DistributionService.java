/*
 * Copyright (C), 2014-2015, 杭州小卡科技有限公司
 * Created by ciwei@xiaokakeji.com on 2016/04/24.
 */
package com.micromall.service;

import com.google.common.collect.Maps;
import com.micromall.entity.CashAccount;
import com.micromall.entity.ext.WithdrawStatus;
import com.micromall.repository.CommissionRecordMapper;
import com.micromall.repository.DistributionRelationMapper;
import com.micromall.repository.OrderMapper;
import com.micromall.repository.WithdrawApplyRecordMapper;
import com.micromall.service.vo.DistributionMember;
import com.micromall.utils.Condition.Criteria;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author zhangzxiang91@gmail.com
 * @date 2016/04/24.
 */
@Service
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

	public Map<String, Object> commissionStat(int uid) {
		Map<String, Object> data = Maps.newHashMap();
		Map<String, Object> lv1 = Maps.newHashMap();
		Map<String, Object> lv2 = Maps.newHashMap();

		CashAccount cashAccount = cashAccountService.getCashAccount(uid);
		//提现中佣金
		BigDecimal withdrawOf = withdrawApplyRecordMapper
				.withdrawStat(Criteria.create().andEqualTo("uid", uid).andEqualTo("status", WithdrawStatus.待审核).build());
		//已提现佣金
		BigDecimal hasWithdraw = withdrawApplyRecordMapper
				.withdrawStat(Criteria.create().andEqualTo("uid", uid).andEqualTo("status", WithdrawStatus.审核通过).build());

		data.put("canWithdraw", cashAccount.getCommission());//可提现佣金
		data.put("withdrawOf", withdrawOf);//提现中佣金
		data.put("hasWithdraw", hasWithdraw);//已提现佣金

		data.put("commissionIncome", commissionRecordMapper.withdrawStat(uid));//佣金总收入
		data.put("totalSales", cashAccount.getTotalSales());//销售总额
		data.put("lv1", lv1);//一级分销商
		data.put("lv2", lv2);//一级分销商

		Object _lv1 = orderMapper.distributionOrderAmountSum(uid, 1);
		Object _lv2 = orderMapper.distributionOrderAmountSum(uid, 2);

		// 一级分销商：
		lv1.put("waitPay", "");//未付款订单
		lv1.put("waitDelivery", "");//待发货订单
		lv1.put("waitReceive", "");//待收货订单
		lv1.put("complete", "");//已完成订单
		lv1.put("closed", "");//已取消订单
		// 二级分销商：
		lv2.put("waitPay", "");//未付款订单
		lv2.put("waitDelivery", "");//待发货订单
		lv2.put("waitReceive", "");//待收货订单
		lv2.put("complete", "");//已完成订单
		lv2.put("closed", "");//已取消订单

		return data;
	}

	public List<DistributionMember> lowerDistributorsList(int uid, Integer level, RowBounds bounds) {
		return distributionRelationMapper.selectLowerMembers(uid, level, bounds);
	}

	public Map<String, Object> lowerDistributorsStat(int uid) {
		distributionRelationMapper.lowerMemberStat(uid);
		Map<String, Object> data = Maps.newHashMap();
		data.put("all", 0);// 分销商总数
		data.put("lv1", 0);//一级分销商数量
		data.put("lv2", 0);//二级分销商数量
		return data;
	}
}
