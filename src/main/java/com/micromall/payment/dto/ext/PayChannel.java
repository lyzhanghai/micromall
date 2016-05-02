package com.micromall.payment.dto.ext;

/**
 * @author zhangzxiang91@gmail.com
 * @date 2016/04/27.
 */
public enum PayChannel {
	/*
		微信支付：
		>公众号支付：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=7_1
		>扫码支付：https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=6_1
		>APP支付：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=8_1
		>转账：https://pay.weixin.qq.com/wiki/doc/api/tools/mch_pay.php?chapter=14_1

		支付宝支付：
		>移动支付：https://doc.open.alipay.com/doc2/detail?treeId=59&articleId=103563&docType=1
		>手机网站支付(新版接口)：https://doc.open.alipay.com/doc2/detail?treeId=60&articleId=103564&docType=1
		>PC网页支付(同时提供扫码支付)：https://doc.open.alipay.com/doc2/detail?treeId=62&articleId=103566&docType=1
		>扫码支付(当面付>扫码支付)：https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.6LxgQU&treeId=26&articleId=103289&docType=1
		>转账：https://doc.open.alipay.com/doc2/detail?treeId=64&articleId=103569&docType=1

		其他支付：
		微信扫码支付，文档地址：https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=6_1
		银联在线支付，文档地址：https://merchant.unionpay.com/join/product/detail?id=1
		网上银行支付（通过支付宝直接跳转到各大银行支付-纯网关支付，不需要登录支付宝）文档地址：https://doc.open.alipay.com/doc2/detail.htm?spm=0.0.0.0
		.dACHOe&treeId=63&articleId=103567&docType=1
		快捷支付-支持信用卡和储蓄卡（通过百度钱包过渡支付）
	*/


	微信("WECHAT"),
	支付宝("ALIPAY"),
	银联("UPMP"),
	百度钱包("BAIFUBAO"),
	余额支付("BALANCE");

	private String code;

	PayChannel(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
