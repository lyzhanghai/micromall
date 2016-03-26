package com.micromall.payment.dto;

public class FundInResult extends BaseResult {

	// 金额
	private Long   amount;
	// 支付签名
	private String sign;

	/**
	 * wap   版
	 * socket
	 * token
	 */
	// private String type;
	// 表单信息
	// private String form;
	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
}
