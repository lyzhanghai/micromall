package com.micromall.repository.entity;

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
	// 支付渠道
	private String     payChannel;
	// 支付方式
	private String     payMethod;
	// 支付平台
	private String     platformType;
	// 第三方支付平台交易流水号
	private String     tradeNo;
	// 异步通知地址
	private String     backNotifyUrl;
	// 前端通知地址
	private String     frontNotifyUrl;
	// 支付IP
	private String     payIp;
	// 扩展数据
	private String     extend;
	// 支付状态
	private String     payStatus;
	// 支付通知状态
	private String     notifyStatus;
	// 退款状态
	private String     refundStatus;
	// 是否删除
	private Boolean    deleted;
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

	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}

	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public String getPlatformType() {
		return platformType;
	}

	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getBackNotifyUrl() {
		return backNotifyUrl;
	}

	public void setBackNotifyUrl(String backNotifyUrl) {
		this.backNotifyUrl = backNotifyUrl;
	}

	public String getFrontNotifyUrl() {
		return frontNotifyUrl;
	}

	public void setFrontNotifyUrl(String frontNotifyUrl) {
		this.frontNotifyUrl = frontNotifyUrl;
	}

	public String getPayIp() {
		return payIp;
	}

	public void setPayIp(String payIp) {
		this.payIp = payIp;
	}

	public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getNotifyStatus() {
		return notifyStatus;
	}

	public void setNotifyStatus(String notifyStatus) {
		this.notifyStatus = notifyStatus;
	}

	public String getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

	public Boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
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
