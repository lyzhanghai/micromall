package com.micromall.service;

import com.micromall.repository.CashAccountMapper;
import com.micromall.repository.entity.CashAccount;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zhangzxiang91@gmail.com
 * @date 2016/04/17.
 */
@Service
@Transactional
public class CashAccountService {

	@Resource
	private CashAccountMapper mapper;

	@Transactional(readOnly = true)
	public CashAccount getCashAccount(Integer uid) {
		return mapper.selectByPrimaryKey(uid);
	}

	public void createCashAccount(Integer uid) {
		CashAccount cashAccount = new CashAccount();
		cashAccount.setUid(uid);
		cashAccount.setBalance(new BigDecimal(0));
		cashAccount.setCommission(new BigDecimal(0));
		cashAccount.setTotalSales(new BigDecimal(0));
		cashAccount.setCreateTime(new Date());
		mapper.insert(cashAccount);
	}

	public int decrementCommission(int uid, BigDecimal amount) {
		return mapper.decrementCommission(uid, amount, new Date());
	}

	public int incrementCommission(int uid, BigDecimal amount) {
		return mapper.incrementCommission(uid, amount, new Date());
	}

	public int decrementBalance(int uid, BigDecimal amount) {
		return mapper.decrementBalance(uid, amount, new Date());
	}

	public int incrementBalance(int uid, BigDecimal amount) {
		return mapper.incrementBalance(uid, amount, new Date());
	}

	public int incrementTotalSales(int uid, BigDecimal amount) {
		return mapper.incrementTotalSales(uid, amount, new Date());
	}
}
