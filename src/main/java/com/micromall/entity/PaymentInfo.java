package com.micromall.entity;

import com.micromall.entity.ext.PayTypes;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhangzx on 16/3/26.
 * 订单支付信息
 */
public class PaymentInfo extends IdEntity {

	// 支付订单编号
	private String     orderNo;
	/**
	 * 支付渠道（微信、支付宝、余额支付）{@link PayTypes}
	 */
	private Integer    payType;
	// 支付金额
	private BigDecimal amount;
	// 支付时间
	private Date       payTime;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

}
