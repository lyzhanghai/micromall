package com.micromall.payment.dto;

import com.micromall.payment.dto.ext.ResultCodeEnum;

import java.util.Map;

/**
 * 支付结果
 */
public class PaymentResult {

	private String              result;
	private ResultCodeEnum      resultCode;
	private String              resultMessage;
	private Map<String, String> extendResult;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public ResultCodeEnum getResultCode() {
		return resultCode;
	}

	public void setResultCode(ResultCodeEnum resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public Map<String, String> getExtendResult() {
		return extendResult;
	}

	public void setExtendResult(Map<String, String> extendResult) {
		this.extendResult = extendResult;
	}
}
