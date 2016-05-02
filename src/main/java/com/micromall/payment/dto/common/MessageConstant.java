package com.micromall.payment.dto.common;

public interface MessageConstant {

	public String AMOUNT_NOT_RIGHT = "金额参数不正确";

	public String SOURCE_PAYMENT_NO_IS_EMPTY = "原支付订单号不能为空";

	public String CAN_REFUND_AMOUNT_LESS_REFUND_AMOUNT = "退款金额大于可退金额";

	public String NO_AVAILABLE_CHANNEL_FUND = "没有可用的渠道";

	public String VERITY_FAIL = "鉴权失败";

	public String INST_CODE_NOT_FUND = "缺少必填的输入项(instCode)";

	public String IP_REQUEST = "ip地址必须要填写";

	public String REMOTE_SERVICE_ERROR = "调用远程服务失败";

	public String PARMAS_NOT_FIT = "填写的参数不完整";

	public String INSERT_DATA_HAPPEN_EXCEPTION = "保存数据过程中发生异常";

	public String SYSTEM_ERROR = "系统内部错误，请联系支付系统人员";

	public String REFUND_ORDER_PROCESSING = "存在未处理完毕的退款订单";

	public String REDUND_ORDER_SUCCESS = "订单退款成功";

	public String ORDER_HAD_BEEN_PAYED = "订单已支付成功";

	public String ORDER_EXIST_BUT_PARAMS_NOT_MATHC = "该订单号已经生成支付订单，参数信息不匹配，请确认参数信息，或生成新的订单号";

	public String ORDER_NO_NOT_EXIST = "请检查输入项，订单号不存在";

	public String ORDER_NOT_EXIST = "订单不存在";

	public String ORDER_SUCCESS = "订单处理成功";

	public String ORDER_PROCESSING = "订单处理中";

	public String ORDER_PAY_FAIL = "订单支付失败";

	public String ORDER_CLOSE = "订单关闭";

	public String REFUND_ORDER_FAIL = "退款处理失败";
}
