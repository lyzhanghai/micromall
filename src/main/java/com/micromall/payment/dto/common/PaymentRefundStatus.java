package com.micromall.payment.dto.common;

public interface PaymentRefundStatus {

	// 退款中
	public String REFUND_PROCESSING = "1";
	// 全部退款
	public String REFUND_ALL        = "9";
	// 未发生退款
	public String UNREFUND          = "0";
}
