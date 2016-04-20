/*
 * Copyright (C), 2014-2015, 杭州小卡科技有限公司
 * Created by ciwei@xiaokakeji.com on 2016/04/17.
 */
package com.micromall.service;

import com.micromall.entity.cash.CashAccount;
import com.micromall.repository.CashAccountMapper;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zhangzxiang91@gmail.com
 * @date 2016/04/17.
 */
public class CashAccountService {

	@Resource
	private CashAccountMapper mapper;

	@Transactional(readOnly = true)
	public CashAccount getCashAccount(Integer uid) {
		return mapper.selectByPrimaryKey(uid);
	}

	@Transactional
	public void createCashAccount(Integer uid) {
		CashAccount cashAccount = new CashAccount();
		cashAccount.setUid(uid);
		cashAccount.setBalance(new BigDecimal(0));
		cashAccount.setCommission(new BigDecimal(0));
		cashAccount.setTotalSales(new BigDecimal(0));
		cashAccount.setCreateTime(new Date());
		mapper.insert(cashAccount);
	}
}