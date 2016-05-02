package com.micromall.payment.dto.common;

public enum ResultCode {

	SUCCESS,
	UNKNOWN_ERROR,
	PAY_CHANNEL_NOT_AVAILABLE,
	REPEAT_PAYMENT,
	INVALID_PARAM

	/*// 处理成功
	String SUCCESS = "00000";
	// 处理中
	String S00001  = "00001";
	// 处理失败
	String F9999   = "F9999";
	// 异常错误
	String E9999   = "E9999";
	// 必填的参数没有填写
	String F30001  = "30001";
	// 退款金额大于可退金额
	String F30002  = "30002";
	// 验签失败
	String F30003  = "30003";
	// 订单不存在
	String F30004  = "30004";
	// 渠道不可用
	String E40001  = "40001";
	// 存在未处理完毕的退款订单
	String F30005  = "30005";*/
}
