package com.micromall.payment.dto;

import java.util.Map;

public class BaseResult {

	// 渠道编码
	protected String              channelCode;
	// 订单号
	protected String              orderNum;
	// 是否调用成功
	protected Boolean             result;
	// 结果编码
	protected String              resultCode;
	// 结果信息
	protected String              resultMessage;
	// 机构交易流水号
	protected String              instReturnNo;
	// 扩展信息
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

	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public String getInstReturnNo() {
		return instReturnNo;
	}

	public void setInstReturnNo(String instReturnNo) {
		this.instReturnNo = instReturnNo;
	}

	public Map<String, String> getExtendParams() {
		return extendParams;
	}

	public void setExtendParams(Map<String, String> extendParams) {
		this.extendParams = extendParams;
	}
}
