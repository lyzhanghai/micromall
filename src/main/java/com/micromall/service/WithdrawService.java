/*
 * Copyright (C), 2014-2015, 杭州小卡科技有限公司
 * Created by ciwei@xiaokakeji.com on 2016/04/23.
 */
package com.micromall.service;

import com.github.pagehelper.Page;
import com.micromall.entity.CashAccount;
import com.micromall.entity.CashRecord;
import com.micromall.entity.WithdrawApplyRecord;
import com.micromall.entity.ext.CashChangeTypes;
import com.micromall.entity.ext.CashTypes;
import com.micromall.entity.ext.WithdrawStatus;
import com.micromall.repository.CashRecordMapper;
import com.micromall.repository.WithdrawApplyRecordMapper;
import com.micromall.utils.Condition;
import com.micromall.utils.LogicException;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 提现
 *
 * @author zhangzxiang91@gmail.com
 * @date 2016/04/23.
 */
@Service
public class WithdrawService {

	@Resource
	private WithdrawApplyRecordMapper withdrawRecordMapper;
	@Resource
	private CashAccountService        cashAccountService;
	@Resource
	private CashRecordMapper          cashRecordMapper;

	/**
	 * 提现记录
	 *
	 * @param condition
	 * @return
	 */
	public List<WithdrawApplyRecord> records(Condition condition) {
		return withdrawRecordMapper.selectMultiByWhereClause(condition);
	}

	/**
	 * 提现记录
	 *
	 * @param condition
	 * @param bounds
	 * @return
	 */
	public Page<WithdrawApplyRecord> records(Condition condition, RowBounds bounds) {
		return withdrawRecordMapper.selectPageByWhereClause(condition, bounds);
	}

	/**
	 * 申请提现
	 *
	 * @param uid
	 * @param amount
	 * @param channel
	 */
	public void applyWithdraw(int uid, float amount, String channel) {
		CashAccount cashAccount = cashAccountService.getCashAccount(uid);

		if (cashAccount == null) {
			throw new LogicException("账户信息错误");
		}

		// 提现金额
		BigDecimal withdraw_amount = new BigDecimal(amount);
		if (cashAccount.getCommission().compareTo(withdraw_amount) < 0) {
			throw new LogicException("提现金额超出可提现佣金总额");
		}

		// 更新扣款账余额
		if (cashAccountService.decrementCommission(uid, withdraw_amount) == 0) {
			cashAccount = cashAccountService.getCashAccount(uid);
			if (cashAccount.getCommission().compareTo(withdraw_amount) < 0) {
				throw new LogicException("提现金额超出可提现佣金总额");
			} else {
				throw new LogicException("申请提现失败");
			}
		}

		// 账户资金变动记录
		CashRecord cashRecord = new CashRecord();
		cashRecord.setUid(uid);
		cashRecord.setCashType(CashTypes.佣金账户余额);
		cashRecord.setAmount(withdraw_amount.multiply(new BigDecimal(-1)));//取负
		cashRecord.setType(CashChangeTypes.佣金提现);
		cashRecord.setBalance(cashAccount.getBalance());
		cashRecord.setCommission(cashAccount.getCommission());
		cashRecord.setContent("佣金提现");
		cashRecord.setCreateTime(new Date());
		cashRecordMapper.insert(cashRecord);

		// 提现记录
		WithdrawApplyRecord withdrawRecord = new WithdrawApplyRecord();
		withdrawRecord.setUid(uid);
		withdrawRecord.setAmount(withdraw_amount);
		withdrawRecord.setChannel(channel);
		withdrawRecord.setStatus(WithdrawStatus.待审核);
		withdrawRecord.setApplyTime(new Date());
		withdrawRecordMapper.insert(withdrawRecord);
	}

}
