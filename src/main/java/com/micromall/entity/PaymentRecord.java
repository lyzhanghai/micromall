package com.micromall.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhangzx on 16/3/23.
 * 支付记录
 */
public class PaymentRecord extends IdEntity {

	// 订单号
	private String     orderNo;
	// 订单金额
	private BigDecimal amount;
	// 支付类型
	private String     payType;
	// 第三方支付平台交易流水号
	private String     trade_no;
	// 前端通知地址
	private String     frontNotifyUrl;
	// 支付通知状态
	private String     notifyStatus;
	// 扩展数据
	private String     extend;
	// 支付状态
	private String     status;
	// 创建时间
	private Date       createTime;
	// 修改时间
	private Date       updateTime;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getTrade_no() {
		return trade_no;
	}

	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}

	public String getFrontNotifyUrl() {
		return frontNotifyUrl;
	}

	public void setFrontNotifyUrl(String frontNotifyUrl) {
		this.frontNotifyUrl = frontNotifyUrl;
	}

	public String getNotifyStatus() {
		return notifyStatus;
	}

	public void setNotifyStatus(String notifyStatus) {
		this.notifyStatus = notifyStatus;
	}

	public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
