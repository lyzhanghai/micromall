package com.micromall.entity;

import java.util.Date;

/**
 * Created by zhangzx on 16/3/23.
 */
public class Coupon extends IdEntity {

	// 金额
	private int     amount;
	// 优惠劵描述
	private String  descr;
	// 所属用户
	private int     uid;
	// 过期时间
	private Date    expiraTime;
	// 是否已经使用
	private boolean used;
	// 使用时关联的订单编号
	private String  useOrderNo;
	// 创建时间
	private Date    createTime;

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

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public Date getExpiraTime() {
		return expiraTime;
	}

	public void setExpiraTime(Date expiraTime) {
		this.expiraTime = expiraTime;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public String getUseOrderNo() {
		return useOrderNo;
	}

	public void setUseOrderNo(String useOrderNo) {
		this.useOrderNo = useOrderNo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
