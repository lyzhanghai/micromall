package com.micromall.payment;

public interface WechatConfig {

	// 应用id
	public String   APP_ID               = "appid";
	// 商家编码
	public String   MCH_ID               = "mchid";
	// 货币类型
	public String   FEE_TYPE             = "feeType";
	// 证书文件地址
	public String   CERT_PATH            = "certLocalPath";
	// 通知地址
	public String   NOTIFY_URL           = "notifyUrl";
	// 交易类型
	public String   TRADE_TYPE           = "tradeType";
	// 交易接口url
	public String   FUND_IN_URL          = "fundInUrl";
	// 查询订单url
	public String   FUND_IN_QUERY_URL    = "fundInQueryUrl";
	// 取消订单url
	public String   CLOSE_ORDER_URL      = "closeOrderUrl";
	// 申请退款url
	public String   REFUND_URL           = "refundUrl";
	// 退款查询url
	public String   REFUND_QUERY_URL     = "refundQueryUrl";
	// 企业密钥
	public String   SECURITY_KEY         = "secuityKey";
	// 商品字段
	public String   BODY                 = "body";
	// 设备号
	public String   DEVIC_INFO           = "device_info";
	// 商品标记
	public String   GOODS_TAGS           = "goods_tag";
	// 支付请求参数
	public String[] FUND_IN_PARAMS       = {"appid", "mch_id", "device_info", "nonce_str", "sign", "body", "detail", "attach", "out_trade_no",
			"fee_type", "total_fee", "spbill_create_ip", "time_start", "time_expire", "goods_tag", "notify_url", "trade_type", "product_id",
			"openid"};
	// 支付可能返回的信息
	public String[] FUND_IN_RESULT_PARAM = {"return_code", "return_msg", "appid", "mch_id", "device_info", "nonce_str", "sign", "result_code",
			"err_code", "err_code_des", "trade_type", "prepay_id", "code_url"};

	// 退款参数
	public String[] REFUND_PARAMS = {"appid", "mch_id", "device_info", "nonce_str", "sign", "transaction_id", "out_trade_no", "out_refund_no",
			"total_fee", "refund_fee", "refund_fee_type", "op_user_id"};

	// 退款可能返回的信息
	public String[] REFUND_RESULT_PARAMS = {"return_code", "return_msg", "result_code", "err_code", "err_code_des", "appid", "mch_id",
			"device_info", "nonce_str", "sign", "transaction_id", "out_trade_no", "out_refund_no", "refund_id", "refund_channel", "refund_fee",
			"total_fee", "fee_type", "cash_fee", "cash_refund_fee", "coupon_refund_fee", "coupon_refund_count"};

	// 退款查询参数
	public String[] REFUND_QUERY_PARAMS = {"appid", "mch_id", "device_info", "nonce_str", "sign", "transaction_id", "out_trade_no", "out_refund_no",
			"refund_id"};

	// 退款可能返回的结果参数
	public String[] REFUND_QUERY_RESULT_PARAMS = {"return_code", "return_msg", "result_code", "err_code", "err_code_des", "appid", "mch_id",
			"device_info", "nonce_str", "sign", "transaction_id", "out_trade_no", "total_fee", "fee_type", "cash_fee", "cash_fee_type",
			"refund_fee", "refund_count"};

	// 入款查询参数
	public String[] FUND_IN_QUERY_PARAMS = {"appid", "mch_id", "transaction_id", "out_trade_no", "nonce_str", "sign"};

	// 入款查询可能返回的参数
	public String[] FUND_IN_QUERY_RESULT_PARAMS = {"return_code", "return_msg", "appid", "mch_id", "nonce_str", "sign", "result_code", "err_code",
			"err_code_des", "device_info", "openid", "is_subscribe", "trade_type", "trade_state", "bank_type", "total_fee", "fee_type", "cash_fee",
			"cash_fee_type", "coupon_fee", "coupon_count", "transaction_id", "out_trade_no", "time_end", "trade_state_desc"};

	// 对账文件下载参数
	public String[] DOWNLOAD_FILE_PARAMS = {};

	// 对账文件下载可能返回参数
	public String[] DOWNLOAD_FILE_RESULT_PARAMS = {};

	// 取消订单参数
	public String[] CANCEL_ORDER_PARAMS        = {"appid", "mch_id", "out_trade_no", "nonce_str", "sign"};
	// 取消订单可能返回的结果
	public String[] CNACEL_ORDER_RESULT_PARAMS = {"return_code", "return_msg", "appid", "mch_id", "nonce_str", "sign", "err_code", "err_code_des"};

	// 验签参数
	public String[] VERTITY_PARAMS = {"appid", "attach", "bank_type", "cash_fee", "device_info", "fee_type", "is_subscribe", "mch_id", "nonce_str",
			"openid", "out_trade_no", "result_code", "return_code", "sign", "time_end", "total_fee", "trade_type", "transaction_id"};
}
