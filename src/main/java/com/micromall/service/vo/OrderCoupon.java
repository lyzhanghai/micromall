package com.micromall.service.vo;

/**
 * Created by zhangzx on 16/3/28.
 */
public class OrderCoupon {
	// 优惠劵id
	private int    couponId;
	// 金额
	private int    amount;
	// 优惠劵描述
	private String descr;

	public int getCouponId() {
		return couponId;
	}

	public void setCouponId(int couponId) {
		this.couponId = couponId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}
}
