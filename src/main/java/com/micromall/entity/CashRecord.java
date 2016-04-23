package com.micromall.entity;

import com.micromall.entity.IdEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhangzx on 16/3/23.
 * 账户资金变动记录
 */
public class CashRecord extends IdEntity {

	private int        uid;// 归属账户
	private int        cashType;//资金类型
	private BigDecimal amount;// 变动金额
	private BigDecimal balance;// 会员账户余额
	private BigDecimal commission;// 佣金账户金额
	private int        type;// 会员充值，订单支付，订单退款，佣金提现，佣金提现退款，佣金结算
	private String     content;// 内容明细
	private Date       createTime;// 创建时间

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getCashType() {
		return cashType;
	}

	public void setCashType(int cashType) {
		this.cashType = cashType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getCommission() {
		return commission;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
