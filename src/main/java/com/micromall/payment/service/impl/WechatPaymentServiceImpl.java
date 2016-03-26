//package com.micromall.payment.service.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.micromall.payment.MessageConstant;
//import com.micromall.payment.ResultCodeEnum;
//import com.micromall.payment.WeChatHttp;
//import com.micromall.payment.WechatConfig;
//import com.micromall.payment.dto.*;
//import com.micromall.payment.service.PaymentService;
//import com.micromall.utils.DateUtils;
//import org.apache.commons.codec.digest.DigestUtils;
//import org.apache.commons.lang3.RandomStringUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//
//@Service
//public class WechatPaymentServiceImpl implements PaymentService {
//
//	private final static Logger logger = LoggerFactory.getLogger(WechatPaymentServiceImpl.class);
//
//	private final static String REPLACE_SIGN = "_REPLACE_SIGN";
//
//	public RefundResult refund(RefundRequest request) {
//		logger.info("退款请求参数:{}", JSON.toJSONString(request));
//		RefundResult result = new RefundResult();
//		result.setResult(false);
//		result.setResultCode(ResultCodeEnum.F9999);
//
//		// 文档：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_4
//		HashMap<String, String> params = new HashMap<String, String>();
//		params.put("appid", WechatConfig.APP_ID);
//		params.put("mch_id", WechatConfig.MCH_ID);
//		params.put("device_info", "");
//		params.put("nonce_str", _RandomSequence());
//		params.put("sign", REPLACE_SIGN);
//		params.put("transaction_id", null);// 微信生成的订单号，在支付通知中有返回
//		params.put("out_trade_no", request.getSourcePaymentNo());// 商户侧传给微信的订单号
//		params.put("out_refund_no", request.getOrderNum());// 商户系统内部的退款单号，商户系统内部唯一，同一退款单号多次请求只退一笔
//		params.put("total_fee", String.valueOf(request.getAmount()));
//		params.put("refund_fee", String.valueOf(request.getRefundAmount()));
//		params.put("refund_fee_type", null);
//		params.put("op_user_id", WechatConfig.MCH_ID);
//
//		String content = _composeXml(params);
//		logger.info("提交微信请求参数{}", content);
//		String response = null;
//		try {
//			response = WeChatHttp.post(WechatConfig.REFUND_URL, content);
//			logger.info("微信返回的参数{}", response);
//		} catch (Exception e) {
//			logger.error("发送微信退款请求错误", e);
//		}
//		if (StringUtils.isEmpty(response)) {
//			result.setResultMessage(MessageConstant.REMOTE_SERVICE_ERROR);
//			return result;
//		}
//		if (!verityResponse(response, null, result, WechatConfig.REFUND_RESULT_PARAMS)) {
//			result.setResult(true);
//			return result;
//		}
//		logger.info("校验通过,组装参数进行返回");
//		result.setResult(true);
//		result.setResultCode(ResultCodeEnum.SUCCESS);
//		result.setResultMessage("退款成功");
//		return result;
//	}
//
//
//	public FundInResult fundIn(FundInRequest request) {
//		logger.info("入款请求参数{}", JSON.toJSONString(request));
//		FundInResult result = new FundInResult();
//		result.setResult(false);
//		result.setResultCode(ResultCodeEnum.F9999);
//
//		// 2组合参数
//		request.setNotifyUrl(WechatConfig.NOTIFY_URL);
//		// request.setCreateTime(new Date());
//		String tradeType = "JSAPI";
//		String openid = request.getExtendParams().get("openid");
//
//		String[] values = {WechatConfig.WAP_APP_ID, WechatConfig.WAP_MCH_ID, request.getExtendParams().get(WechatConfig.DEVIC_INFO) == null ? "WEB"
//				: request.getExtendParams().get(WechatConfig.DEVIC_INFO), _RandomSequence(), NEED_REPLACE_SIGN, request.getGoodsName(), "", request
//				.getPurpose(), request.getInstOrderNo(), WechatConfig.FEE_TYPE, request.getAmount().toString(), request.getIp(), DateUtils.now
//				("yyyyMMddHHmmss"), "", request.getExtendParams().get(WechatConfig.GOODS_TAGS), WechatConfig.NOTIFY_URL, tradeType, "", openid};
//		// 3、格式化成xml，并签名
//		String xml = _composeXml(WechatConfig.FUND_IN_PARAMS, values);
//		logger.info("提交微信请求参数{}", xml);
//		// 4、请求微信服务器
//		String response = null;
//		try {
//			response = WeChatHttp.post(WechatConfig.FUND_IN_URL, xml);
//			if (!StringUtils.isEmpty(response) && response.indexOf("CDATA[FAIL]") >= 0) {
//				result.setResultCode(ResultCodeEnum.F9999);
//				result.setResultMessage(response.substring(response.indexOf("<return_msg>") + 12, response.indexOf("</return_msg>")));
//				result.setResult(false);
//				return result;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		logger.info("微信返回的参数{}", response);
//		// 5、格式化返回值，构建返回对象
//		if (StringUtils.isEmpty(response)) {
//			result.setResultMessage(MessageConstant.REMOTE_SERVICE_ERROR);
//			return result;
//		}
//		Map<String, String> resultMap = new HashMap<String, String>();
//		if (!formatterResult(response, resultMap, result, WechatConfig.FUND_IN_RESULT_PARAM)) {
//			return result;
//		}
//		logger.info("校验通过,组装参数进行返回");
//		result.setResultCode(ResultCodeEnum.SUCCESS);
//		result.setResultMessage("调用成功");
//		result.setResult(true);
//		result.setInstReturnNo(resultMap.get("prepay_id"));
//		result.setSign(getPayData(result.getInstReturnNo(), request.getChannelCode()));
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("code_url", resultMap.get("code_url"));
//		map.put("trade_type", resultMap.get("trade_type"));
//		result.setExtendParams(map);
//		return result;
//	}
//
//	public VerityResult verity(VerityRequest request) {
//		VerityResult result = new VerityResult();
//		result.isSuccess(false);
//		result.setResultCode(ResultCodeEnum.F9999);
//		if (StringUtils.isEmpty(request.getResponseMsg())) {
//			result.setResultMessage("没有回调内容");
//			return result;
//		}
//
//		String clientKey = InstCodeEnum.微信;
//		if (request.getKey() != null) {
//			clientKey += request.getKey();
//		}
//		// 通过传入的对象去判断类型，选择不同的方式进行解析
//		Map<String, String> resultMap = new HashMap<String, String>();
//		Map<String, String> extendsion = request.getExtendsion();
//		if (request.getExtendsion().get(ExtendsionKey.NOTIFY_TYPE).equals(NotifyType.FUND_IN_NOTIFY)) {
//			if (formatterResponse(request.getResponseMsg(), WechatConfig.VERTITY_PARAMS, resultMap, clientKey)) {
//				result.isSuccess(true);
//				if (resultMap.get("result_code") != null && resultMap.get("result_code").equals("SUCCESS")) {
//					result.setResultCode(ResultCodeEnum.SUCCESS);
//					result.setResultMessage(MessageConstant.ORDER_SUCCESS);
//					result.setInstOrderNo(resultMap.get("out_trade_no"));
//					result.setInstReturnNo(resultMap.get("transaction_id"));
//					extendsion.put(ExtendsionKey.AMOUNT, resultMap.get("total_fee"));
//					extendsion.put(ExtendsionKey.NOTIFY_DATA, request.getResponseMsg());
//				} else {
//					result.setResultCode(ResultCodeEnum.F9999);
//					result.setResultMessage(MessageConstant.ORDER_PAY_FAIL);
//				}
//			}
//
//		} else {
//			// 退款通知处理
//		}
//		extendsion.put(ExtendsionKey.returnMsg, "<xml><return_code>SUCCESS</return_code><return_msg>OK</return_msg></xml>");
//		result.setExtendsion(extendsion);
//		return result;
//	}
//
//	// =======================================工具方法===============================================
//
//	private String _composeXml(Map<String, String> params) {
//		StringBuilder builder = new StringBuilder();
//		Map<String, String> _params = new HashMap<String, String>();
//		builder.append("<xml>");
//		for (Map.Entry<String, String> entry : params.entrySet()) {
//			if (!StringUtils.isEmpty(entry.getValue())) {
//				builder.append("<").append(entry.getKey()).append(">").append(entry.getValue()).append("</").append(entry.getKey()).append(">");
//				_params.put(entry.getKey(), entry.getValue());
//			}
//		}
//		builder.append("</xml>");
//		return builder.toString().replace(REPLACE_SIGN, _signature(_params));
//	}
//
//	private String _signature(Map<String, String> params) {
//		StringBuilder signParams = new StringBuilder();
//		List<String> keys = new ArrayList<String>(params.keySet());
//		Collections.sort(keys);
//		for (String key : keys) {
//			if (key.equals("sign") || StringUtils.isEmpty(params.get(key))) {
//				continue;
//			}
//			signParams.append(key).append("=").append(params.get(key)).append("&");
//		}
//		signParams.append("key=").append(WechatConfig.SECURITY_KEY);
//		return DigestUtils.md5Hex(signParams.toString());
//	}
//
//	/**
//	 * 回对
//	 */
//	private boolean formatterResponse(String response, String[] resultParams, final Map<String, String> resultMap) {
//		// 1、去掉冗余的数据
//		response = response.replaceAll("<!\\[CDATA\\[", "");
//		response = response.replace("]]>", "");
//		// 2、判断回包中的参数，放入map对象当中
//		for (String key : resultParams) {
//			if (response.contains("<" + key + ">") && !key.equals("sign")) {
//				resultMap.put(key, response.substring(response.indexOf("<" + key + ">") + key.length() + 2, response.lastIndexOf("</" + key + ">")));
//			}
//		}
//		// 3、验签通过，则为可信任的返回数据
//		if (response.contains("sign")) {
//			String sign = response.substring(response.indexOf("sign") + 5, response.lastIndexOf("sign") - 2);
//			if (sign.equals(_signature(resultMap))) {
//				return true;
//			}
//		}
//		return false;
//	}
//
//	private boolean verityResponse(String response, final Map<String, String> resultMap, BaseResult result, String[] resultParams) {
//		if (!this.formatterResponse(response, resultParams, resultMap)) {
//			result.setResultMessage(MessageConstant.VERITY_FAIL);
//		} else if (!resultMap.get("return_code").equals("SUCCESS")) {
//			result.setResultMessage(resultMap.get("return_msg"));
//		} else if (!resultMap.get("result_code").equals("SUCCESS")) {
//			result.setResultCode(resultMap.get("err_code"));
//			result.setResultMessage(resultMap.get("err_code_des"));
//		} else {
//			return true;
//		}
//		return false;
//	}
//
//	// 获取随机序列
//	private String _RandomSequence() {
//		return RandomStringUtils.random(16, true, true);
//	}
//
//	// 获取app的支付信息
//	private String getPayData(String prepayid) {
//		Map<String, String> param = new HashMap<String, String>();
//		param.put("appId", WechatConfig.WAP_APP_ID);
//		param.put("package", "prepay_id=" + prepayid);
//		param.put("nonceStr", _RandomSequence());
//		param.put("timeStamp", (System.currentTimeMillis() / 1000) + "");
//		param.put("signType", "MD5");
//		param.put("paySign", _signature(param));
//		return JSON.toJSONString(param);
//	}
//}
