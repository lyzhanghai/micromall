package com.micromall.entity.cash;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 资金账户信息
 *
 * @author zhangzxiang91@gmail.com
 * @date 2016/04/09.
 */
public class CashAccount {

	// 所属用户id
	private Integer    uid;
	// 会员账户余额
	private BigDecimal balance;
	// 佣金账户金额
	private BigDecimal commission;
	// 可提现佣金
	// private BigDecimal canWithdraw;
	// 提现中佣金
	// private BigDecimal withdrawOf;
	// 已提现佣金
	// private BigDecimal hasWithdraw;
	// 佣金总收入
	// private BigDecimal commissionIncome;
	// 销售总额
	private BigDecimal totalSales;
	// 创建时间
	private Date       createTime;
	// 更新时间
	private Date       updateTime;

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
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

	public BigDecimal getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(BigDecimal totalSales) {
		this.totalSales = totalSales;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
