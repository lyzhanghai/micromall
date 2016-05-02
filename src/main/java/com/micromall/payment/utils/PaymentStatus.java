package com.micromall.payment.utils;

/**
 * 入款订单状态
 * @author Administrator
 *
 */
public interface PaymentStatus {

	//等待支付
	public String WAIT_PAY = "00";
	//支付成功
	public String SUCCESS = "99";
	//订单关闭
	public String CLOSE = "01";
	
}
