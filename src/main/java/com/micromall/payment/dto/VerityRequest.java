package com.micromall.payment.dto;

public class VerityRequest extends BaseRequest {

	// 机构返回的通知信息
	private String responseMsg;

	public String getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}

}
