package com.micromall.service;

import com.github.pagehelper.Page;
import com.micromall.repository.CashRecordMapper;
import com.micromall.repository.WithdrawApplyRecordMapper;
import com.micromall.repository.entity.CashAccount;
import com.micromall.repository.entity.CashRecord;
import com.micromall.repository.entity.WithdrawApplyRecord;
import com.micromall.repository.entity.common.CashChangeTypes;
import com.micromall.repository.entity.common.CashTypes;
import com.micromall.repository.entity.common.WithdrawStatus;
import com.micromall.utils.Condition;
import com.micromall.utils.LogicException;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

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
	 * @param bounds
	 * @return
	 */
	@Transactional(readOnly = true)
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
	@Transactional
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

		cashAccount = cashAccountService.getCashAccount(uid);

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
		withdrawRecord.setBalance(cashAccount.getCommission());
		withdrawRecord.setChannel(channel);
		withdrawRecord.setStatus(WithdrawStatus.待审核);
		withdrawRecord.setApplyTime(new Date());
		withdrawRecordMapper.insert(withdrawRecord);
	}

	public boolean withdrawAudit(int id, String auditlog, boolean agreed) {
		WithdrawApplyRecord record = withdrawRecordMapper.selectByPrimaryKey(id);
		if (record == null) {
			throw new LogicException("提现申请记录不存在");
		}
		if (record.getStatus() != WithdrawStatus.待审核) {
			throw new LogicException("不能重复审核");
		}

		WithdrawApplyRecord _Modify = new WithdrawApplyRecord();
		_Modify.setId(record.getId());
		_Modify.setAuditTime(new Date());
		if (agreed) {
			_Modify.setStatus(WithdrawStatus.审核通过);
			_Modify.setAuditlog("通过审核");
			_Modify.setCompleteTime(new Date());
			withdrawRecordMapper.updateByPrimaryKey(_Modify);
		} else {
			_Modify.setStatus(WithdrawStatus.审核不通过);
			_Modify.setAuditlog(auditlog);
			withdrawRecordMapper.updateByPrimaryKey(_Modify);

			CashAccount cashAccount = cashAccountService.getCashAccount(record.getUid());
			if (cashAccount == null) {
				throw new LogicException("会员账户信息错误");
			}
			// 账户金额恢复
			if (cashAccountService.incrementCommission(record.getUid(), record.getAmount()) == 0) {
				throw new LogicException("退回会员佣金账户提现金额失败");
			}
			cashAccount = cashAccountService.getCashAccount(record.getUid());

			// 账户资金变动记录
			CashRecord cashRecord = new CashRecord();
			cashRecord.setUid(record.getUid());
			cashRecord.setCashType(CashTypes.佣金账户余额);
			cashRecord.setAmount(record.getAmount());//取负
			cashRecord.setType(CashChangeTypes.佣金提现退款);
			cashRecord.setBalance(cashAccount.getBalance());
			cashRecord.setCommission(cashAccount.getCommission());
			cashRecord.setContent("佣金提现失败回退");
			cashRecord.setCreateTime(new Date());
			cashRecordMapper.insert(cashRecord);
		}
		return true;
	}

}
