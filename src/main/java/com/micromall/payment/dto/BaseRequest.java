package com.micromall.payment.dto;

import java.util.Map;

public class BaseRequest {

	// 渠道编码
	protected String              channelCode;
	// 订单号
	protected String              orderNum;
	// 金额
	protected Long                amount;
	// 通知地址
	protected String              notifyUrl;
	// 扩展字段
	protected Map<String, String> extendParams;

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public Map<String, String> getExtendParams() {
		return extendParams;
	}

	public void setExtendParams(Map<String, String> extendParams) {
		this.extendParams = extendParams;
	}
}
