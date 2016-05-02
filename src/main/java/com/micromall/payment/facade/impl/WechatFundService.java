package com.micromall.payment.facade.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.micromall.payment.dto.*;
import com.micromall.payment.dto.ext.MessageConstant;
import com.micromall.payment.dto.ext.PayMethod;
import com.micromall.payment.dto.ext.PayMethod.Wechat;
import com.micromall.payment.dto.ext.ResultCodeEnum;
import com.micromall.payment.utils.MoneyUtils;
import com.micromall.payment.utils.WechatConfig;
import com.micromall.payment.utils.XmlUtils;
import com.micromall.utils.DateUtils;
import com.micromall.utils.HttpUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;
import java.util.Map.Entry;

/**
 * @author zhangzxiang91@gmail.com
 * @date 2016/04/27.
 */
public class WechatFundService extends FundServiceFacadeImpl {

	private static Logger logger = LoggerFactory.getLogger(WechatFundService.class);

	@Override
	public PaymentResult payment(PaymentRequest request) {
		PaymentResult result = new PaymentResult();

		String openid = "";
		String tradeType = "";
		if (request.getPayMethod() == Wechat.公众号支付) {
			tradeType = "JSAPI";
			openid = request.getExtendParams().get(WechatConfig.OPENID_KEY);
			if (StringUtils.isEmpty(openid)) {
				result.setResultCode(ResultCodeEnum.UNKNOWN_ERROR);
				result.setResultMessage(MessageConstant.REMOTE_SERVICE_ERROR);
				return result;
			}
		} else if (request.getPayMethod() == Wechat.APP支付) {
			tradeType = "APP";
		} else if (request.getPayMethod() == Wechat.扫码支付) {
			tradeType = "NATIVE";
		}

		Map<String, String> params = Maps.newHashMap();
		params.put("appid", WechatConfig.APP_ID);
		params.put("mch_id", WechatConfig.MCH_ID);
		params.put("device_info", "WEB");
		params.put("nonce_str", RandomStringUtils.random(16, true, true));
		// params.put("sign", _INNER_SIGN_REPLACE_TAG);
		params.put("body", request.getGoodsName());
		// params.put("detail", "");
		// params.put("attach", request.getPurpose());
		params.put("out_trade_no", request.getOrderNo());
		// params.put("fee_type", WechatConfig.FEE_TYPE);
		params.put("total_fee", MoneyUtils.yuan2Fen(request.getAmount().doubleValue()));
		params.put("spbill_create_ip", request.getPayIp());
		// params.put("time_start", DateUtils.format(new Date(),DateUtils.YYYYMMDDHHMMSS));
		// params.put("time_expire", "");
		params.put("goods_tag", request.getExtendParams().get(WechatConfig.GOODS_TAGS_KEY));
		params.put("notify_url", WechatConfig.NOTIFY_URL);
		params.put("trade_type", tradeType);
		// params.put("product_id", "");
		// params.put("limit_pay", "");
		params.put("openid", openid);
		params.put("sign", _get_signature(params));

		String request_content = XmlUtils.convertToXml(params, true);
		ResponseEntity<String> responseEntity = HttpUtils.executeRequest(WechatConfig.UNIFIEDORDER_URL, request_content, String.class);
		if (responseEntity == null || responseEntity.getStatusCode() != HttpStatus.OK || StringUtils.isEmpty(responseEntity.getBody())) {
			result.setResultMessage(MessageConstant.REMOTE_SERVICE_ERROR);
			return result;
		}

		Map<String, Object> response = XmlUtils.convertToMap(responseEntity.getBody());
		if (!"SUCCESS".equals(response.get("return_code"))) {
			result.setResultMessage(MessageConstant.REMOTE_SERVICE_ERROR);
			// response.get("return_msg");
			return result;
		}
		if (!"SUCCESS".equals(response.get("result_code"))) {
			result.setResultMessage(MessageConstant.REMOTE_SERVICE_ERROR);
			// response.get("err_code");
			// response.get("err_code_des");
			return result;
		}

		if (!verifySign(response)) {
			result.setResultMessage(MessageConstant.REMOTE_SERVICE_ERROR);
			return result;
		}

		result.setResultCode(ResultCodeEnum.SUCCESS);
		result.setResult(_get_paydata(response, request.getPayMethod()));
		return result;
	}

	private String _get_paydata(Map<String, Object> prepayData, PayMethod payMethod) {
		Map<String, String> param = new HashMap<String, String>();
		if (payMethod == Wechat.公众号支付) {
			param.put("appId", WechatConfig.APP_ID);
			param.put("package", "prepay_id=" + prepayData.get("prepay_id"));
			param.put("nonceStr", RandomStringUtils.random(16, true, true));
			param.put("timeStamp", (System.currentTimeMillis() / 1000) + "");
			param.put("signType", "MD5");
			param.put("paySign", _get_signature(param));
		} else if (payMethod == Wechat.APP支付) {
			param.put("appid", WechatConfig.APP_ID);
			param.put("partnerid", WechatConfig.MCH_ID);
			param.put("prepayid", (String)prepayData.get("prepay_id"));
			param.put("package", "Sign=WXPay");
			param.put("noncestr", RandomStringUtils.random(16, true, true));
			param.put("timestamp", DateUtils.format(new Date(), "yyyyMMddHH"));
			param.put("sign", _get_signature(param));
		} else if (payMethod == Wechat.扫码支付) {
			return (String)prepayData.get("code_url");
		}
		return JSON.toJSONString(param);
	}

	private boolean verifySign(Map<String, Object> params) {
		Map<String, String> _params = Maps.newHashMap();
		for (Entry<String, Object> entry : params.entrySet()) {
			if (!WechatConfig.SIGN_KEY.equals(entry.getKey())) {
				_params.put(entry.getKey(), (String)entry.getValue());
			}
		}
		String sign = (String)params.get(WechatConfig.SIGN_KEY);
		return _get_signature(_params).equals(sign);
	}

	private String _get_signature(Map<String, String> params) {
		StringBuilder builder = new StringBuilder();
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		for (String key : keys) {
			if (StringUtils.isEmpty(params.get(key)) || WechatConfig.SIGN_KEY.equals(key)) {
				continue;
			}
			builder.append(key).append("=").append(params.get(key)).append("&");
		}
		builder.append("key=").append(WechatConfig.SECURITY_KEY);
		return DigestUtils.md5Hex(builder.toString()).toUpperCase();
	}

	@Override
	public PaymentResult paymentQuery(PaymentQueryRequest request) {
		return null;
	}

	@Override
	public RefundQueryResult refundQuery(RefundQueryRequest request) {
		return null;
	}

	@Override
	public RefundResult refund(RefundRequest request) {
		return null;
	}

	@Override
	public TransferResult transfer(TransferRequest request) {
		return null;
	}

	@Override
	public TransferQueryResult transferQuery(TransferQueryRequest request) {
		return null;
	}

	@Override
	public VerityResult verity(VerityRequest request) {
		return null;
	}
}
