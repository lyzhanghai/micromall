package com.micromall.payment.dto;

import java.util.Date;

public class RefundRequest extends BaseRequest {

	//原支付订单号
	private String sourcePaymentNo;
	//原支付时间
	private Date   sourcePaymentTime;
	//退款金额
	private Long   refundAmount;
	//原始支付金额
	private Long   sourcePaymentAmount;

	public String getSourcePaymentNo() {
		return sourcePaymentNo;
	}

	public void setSourcePaymentNo(String sourcePaymentNo) {
		this.sourcePaymentNo = sourcePaymentNo;
	}

	public Date getSourcePaymentTime() {
		return sourcePaymentTime;
	}

	public void setSourcePaymentTime(Date sourcePaymentTime) {
		this.sourcePaymentTime = sourcePaymentTime;
	}

	public Long getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Long refundAmount) {
		this.refundAmount = refundAmount;
	}

	public Long getSourcePaymentAmount() {
		return sourcePaymentAmount;
	}

	public void setSourcePaymentAmount(Long sourcePaymentAmount) {
		this.sourcePaymentAmount = sourcePaymentAmount;
	}
}
