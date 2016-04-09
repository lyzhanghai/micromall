package com.micromall.entity.cash;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 佣金收入记录
 *
 * @author zhangzxiang91@gmail.com
 * @date 2016/04/09.
 */
public class CommissionRecord {

	// 所属用户id
	private int        uid;
	// 产生佣金下级分销商id
	private int        lowerUid;
	// 产生佣金的订单
	private String     orderNo;
	// 产生佣金的订单
	private BigDecimal orderAmount;
	// 佣金金额
	private BigDecimal commissionAmount;
	// 佣金收入时间
	private Date       createTime;

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public int getLowerUid() {
		return lowerUid;
	}

	public void setLowerUid(int lowerUid) {
		this.lowerUid = lowerUid;
	}

	public BigDecimal getCommissionAmount() {
		return commissionAmount;
	}

	public void setCommissionAmount(BigDecimal commissionAmount) {
		this.commissionAmount = commissionAmount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
