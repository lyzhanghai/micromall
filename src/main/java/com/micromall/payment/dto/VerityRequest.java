package com.micromall.payment.dto;

import com.micromall.payment.dto.common.PayChannel;

/**
 * 验签请求
 */
public class VerityRequest {

	// 支付渠道
	private PayChannel payChannel;
	private String     requestData;
	private String     requestIp;
	private String     notifyType;

	public PayChannel getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(PayChannel payChannel) {
		this.payChannel = payChannel;
	}

	public String getRequestData() {
		return requestData;
	}

	public void setRequestData(String requestData) {
		this.requestData = requestData;
	}

	public String getRequestIp() {
		return requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}

	public String getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}
}
