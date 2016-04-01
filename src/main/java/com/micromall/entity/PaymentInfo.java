package com.micromall.entity;

import java.util.Date;

/**
 * Created by zhangzx on 16/3/26.
 * 支付信息
 */
public class PaymentInfo extends IdEntity {

	// 支付订单编号
	private String orderNo;
	// 支付类型（微信、支付宝）
	private int    payType;
	// 支付金额
	private float  amount;
	// 支付时间
	private Date   payTime;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
}
