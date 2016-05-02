package com.micromall.payment.utils;

import com.micromall.payment.dto.ext.PayChannel;

public interface WechatConfig {

	/*-------------------------支付商户配置----------------------------------------------*/
	// 公众账号ID
	public String APP_ID       = PropertiesHelper.get(PayChannel.微信.getCode()).getProperty("appid");
	// 商户号
	public String MCH_ID       = PropertiesHelper.get(PayChannel.微信.getCode()).getProperty("mchid");
	// API密钥
	public String SECURITY_KEY = PropertiesHelper.get(PayChannel.微信.getCode()).getProperty("secuityKey");
	// 证书文件地址
	public String CERT_PATH    = PropertiesHelper.get(PayChannel.微信.getCode()).getProperty("certPath");
	// 异步通知回调地址
	public String NOTIFY_URL   = PropertiesHelper.get(PayChannel.微信.getCode()).getProperty("notifyUrl");

	// 统一下单url
	public String UNIFIEDORDER_URL = PropertiesHelper.get(PayChannel.微信.getCode()).getProperty("unifiedorderUrl");


	/*-------------------------常量Key定义----------------------------------------------*/

	public String OPENID_KEY     = "openid";
	public String SIGN_KEY       = "sign";
	public String GOODS_TAGS_KEY = "goods_tag";
}
