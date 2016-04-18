package com.micromall.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

/**
 * Created by zhangzx on 16/3/23.
 * 用户优惠券
 */
public class Coupon extends IdEntity {

	// 所属用户id
	@JsonIgnore
	private Integer uid;
	// 金额
	private Integer amount;
	// 优惠劵描述
	private String  descr;
	// 过期时间
	private Date    expiraTime;
	// 是否已经使用
	private Boolean used;
	// 使用的订单号
	@JsonIgnore
	private String  usedOrderNo;
	// 使用时间
	@JsonIgnore
	private Date    usedTime;
	// 创建时间
	@JsonIgnore
	private Date    createTime;

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Date getExpiraTime() {
		return expiraTime;
	}

	public void setExpiraTime(Date expiraTime) {
		this.expiraTime = expiraTime;
	}

	public Boolean isUsed() {
		return used;
	}

	public void setUsed(Boolean used) {
		this.used = used;
	}

	public String getUsedOrderNo() {
		return usedOrderNo;
	}

	public void setUsedOrderNo(String usedOrderNo) {
		this.usedOrderNo = usedOrderNo;
	}

	public Date getUsedTime() {
		return usedTime;
	}

	public void setUsedTime(Date usedTime) {
		this.usedTime = usedTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
