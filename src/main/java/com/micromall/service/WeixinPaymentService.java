package com.micromall.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.micromall.repository.PaymentRecordMapper;
import com.micromall.repository.entity.Order;
import com.micromall.repository.entity.PaymentRecord;
import com.micromall.repository.entity.common.PaymentStatus;
import com.micromall.utils.*;
import com.micromall.utils.Condition.Criteria;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

/**
 * @author zhangzxiang91@gmail.com
 * @date 2016/04/27.
 */
@Service
public class WeixinPaymentService {

	private static Logger logger   = LoggerFactory.getLogger(WeixinPaymentService.class);
	private static String SIGN_KEY = "sign";
	@Resource
	private PaymentRecordMapper paymentRecordMapper;
	@Resource
	private OrderService        orderService;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("/Users/zhangzx/work/my-work/micromall/src/main/java/com/micromall/utils/x.xml"));
		StringBuilder builder = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			builder.append(line);
		}
		reader.close();
		ResponseEntity<String> responseEntity = HttpUtils
				.executePost("https://api.mch.weixin.qq.com/pay/unifiedorder", builder.toString(), String.class);
		System.out.println(responseEntity.getBody());
	}

	public void payNotify(String requestData) {
		logger.info("[微信支付回调]:通知内容:{}", requestData);
		Map<String, Object> resultMap = XmlUtils.convertToMap(requestData);
		if (!"SUCCESS".equals(resultMap.get("return_code"))) {
			logger.error("[微信支付回调]:支付失败, 通知内容:{}", requestData);
		} else if (!"SUCCESS".equals(resultMap.get("result_code"))) {
			logger.error("[微信支付回调]:支付失败, 通知内容:{}", requestData);
		} else if (!verifySign(resultMap)) {
			logger.error("[微信支付回调]:签名验证失败, 通知内容:{}", requestData);
		} else {
			String orderNo = (String)resultMap.get("out_trade_no");
			String tradeNo = (String)resultMap.get("transaction_id");

			List<PaymentRecord> records = paymentRecordMapper
					.selectMultiByWhereClause(Criteria.create().andEqualTo("deleted", false).andEqualTo("order_no", orderNo).build());
			PaymentRecord paymentRecord = null;
			for (PaymentRecord record : records) {
				if (record.getPayStatus().equals(PaymentStatus.SUCCESS)) {
					logger.error("[微信支付回调]:重复支付, 订单号:{}  通知内容:{}", orderNo, requestData);
					return;
				} /*else if (record.getPayStatus().equals(PaymentStatus.CLOSED)) {
					logger.info("[微信支付回调]:订单已关闭, 订单号:{}  通知内容:{}", orderNo, requestData);
					return;
				}*/ else if (record.getPayStatus().equals(PaymentStatus.WAIT_PAY)) {
					paymentRecord = record;
				}
			}
			if (paymentRecord == null) {
				logger.error("[微信支付回调]:原始订单不存, 订单号:{}  通知内容:{}", orderNo, requestData);
				return;
			}
			try {
				logger.info("[微信支付回调]:修改支付记录状态, 支付记录:{}  通知内容:{}", JSON.toJSONString(paymentRecord), requestData);
				paymentRecord.setPayStatus(PaymentStatus.SUCCESS);
				paymentRecord.setTradeNo(tradeNo);
				paymentRecord.setDeleted(false);
				paymentRecord.setUpdateTime(new Date());
				paymentRecordMapper.updateByPrimaryKey(paymentRecord);
			} catch (Exception e) {
				logger.error("[微信支付回调]:修改支付记录状态出错, 支付记录:{}  通知内容:{}", JSON.toJSONString(paymentRecord), requestData, e);
			}
			try {
				logger.info("[微信支付回调]:通知订单支付成功, 订单号:{}", orderNo);
				orderService.paySuccess(orderNo);
			} catch (Exception e) {
				logger.error("[微信支付回调]:通知订单支付成功接口调用出错, 订单号:{}", orderNo, e);
			}
		}
	}

	@Transactional
	public Map<String, String> pay(Order order, String openid, String ip) {
		logger.info("[微信支付请求参数]:[订单号:{}, openid:{}, ip:{}]", order.getOrderNo(), openid, ip);

		// 判断所有的订单，看是否有已支付的
		List<PaymentRecord> paymentRecords = paymentRecordMapper
				.selectMultiByWhereClause(Criteria.create().andEqualTo("deleted", false).andEqualTo("order_no", order.getOrderNo()).build());

		if (!paymentRecords.isEmpty()) {
			for (PaymentRecord record : paymentRecords) {
				if (record.getPayStatus().equals(PaymentStatus.SUCCESS)) {
					throw new LogicException("已经支付成功，请勿重复支付");
				} else {
					PaymentRecord _update = new PaymentRecord();
					_update.setId(record.getId());
					_update.setDeleted(true);
					_update.setUpdateTime(new Date());
					paymentRecordMapper.updateByPrimaryKey(record);
				}
			}
		}
		PaymentRecord paymentRecord = new PaymentRecord();
		paymentRecord.setOrderNo(order.getOrderNo());
		paymentRecord.setAmount(order.getTotalAmount());
		paymentRecord.setIp(ip);
		paymentRecord.setPayStatus(PaymentStatus.WAIT_PAY);
		paymentRecord.setDeleted(false);
		paymentRecord.setCreateTime(new Date());
		paymentRecordMapper.insert(paymentRecord);

		Map<String, String> params = Maps.newHashMap();
		params.put("appid", CommonEnvConstants.WEIXIN_APPID);
		params.put("mch_id", CommonEnvConstants.WEIXIN_MCHID);
		params.put("device_info", "WEB");
		params.put("nonce_str", RandomStringUtils.random(16, true, true));
		// params.put("sign", _INNER_SIGN_REPLACE_TAG);
		params.put("body", "Unon优农直供");
		// params.put("detail", "");
		// params.put("attach", request.getPurpose());
		params.put("out_trade_no", order.getOrderNo());
		// params.put("fee_type", FEE_TYPE);
		params.put("total_fee", MoneyUtils.yuan2Fen(order.getTotalAmount().doubleValue()));
		params.put("spbill_create_ip", ip);
		params.put("time_start", DateUtils.format(new Date(), DateUtils.YYYYMMDDHHMMSS));
		// params.put("time_expire", "");
		// params.put("goods_tag", request.getExtendParams().get(GOODS_TAGS_KEY));
		params.put("notify_url", CommonEnvConstants.WEIXIN_NOTIFY_URL);
		params.put("trade_type", "JSAPI");
		// params.put("product_id", "");
		// params.put("limit_pay", "");
		params.put("openid", openid);
		params.put("sign", _get_signature(params));

		String request_content = XmlUtils.convertToXml(params, true);
		ResponseEntity<String> responseEntity = HttpUtils.executePost(CommonEnvConstants.WEIXIN_UNIFIEDORDER_URL, request_content, String.class);
		if (responseEntity == null || responseEntity.getStatusCode() != HttpStatus.OK || StringUtils.isEmpty(responseEntity.getBody())) {
			logger.error("[微信支付失败]:调用统一下单接口出错 | [订单号:{}, openid:{}, ip:{}] | [微信调用参数:{}] | [微信返回结果:{}]", order.getOrderNo(), openid, ip,
					JSON.toJSONString(request_content), responseEntity != null ? responseEntity.getBody() : "没有返回结果");
			throw new LogicException("支付失败，请稍后再试");
		}

		Map<String, Object> response = XmlUtils.convertToMap(responseEntity.getBody());
		if (!"SUCCESS".equals(response.get("return_code"))) {
			logger.error("[微信支付失败]:调用统一下单接口失败 | [订单号:{}, openid:{}, ip:{}] | [微信调用参数:{}] | [微信返回结果:{}]", order.getOrderNo(), openid, ip,
					JSON.toJSONString(request_content), responseEntity.getBody());
			throw new LogicException("支付失败，请稍后再试");
		}
		if (!"SUCCESS".equals(response.get("result_code"))) {
			logger.error("[微信支付失败]:调用统一下单接口失败 | [订单号:{}, openid:{}, ip:{}] | [微信调用参数:{}] | [微信返回结果:{}]", order.getOrderNo(), openid, ip,
					JSON.toJSONString(request_content), responseEntity.getBody());
			throw new LogicException("支付失败，请稍后再试");
		}

		if (!verifySign(response)) {
			logger.error("[微信支付失败]:调用统一下单接口结果签名验证失败 | [订单号:{}, openid:{}, ip:{}] | [微信调用参数:{}] | [微信返回结果:{}]", order.getOrderNo(), openid, ip,
					JSON.toJSONString(request_content), responseEntity.getBody());
			throw new LogicException("支付失败，请稍后再试");
		}
		Map<String, String> result = _get_paydata(response);
		logger.info("微信支付处理结果:{}", JSON.toJSONString(result));
		return result;
	}

	private Map<String, String> _get_paydata(Map<String, Object> prepayData) {
		Map<String, String> param = new HashMap<>();
		param.put("appId", CommonEnvConstants.WEIXIN_APPID);
		param.put("package", "prepay_id=" + prepayData.get("prepay_id"));
		param.put("nonceStr", RandomStringUtils.random(16, true, true));
		param.put("timeStamp", (System.currentTimeMillis() / 1000) + "");
		param.put("signType", "MD5");
		param.put("paySign", _get_signature(param));
		return param;
	}

	private boolean verifySign(Map<String, Object> params) {
		Map<String, String> _params = Maps.newHashMap();
		for (Entry<String, Object> entry : params.entrySet()) {
			if (!SIGN_KEY.equals(entry.getKey())) {
				_params.put(entry.getKey(), (String)entry.getValue());
			}
		}
		String sign = (String)params.get(SIGN_KEY);
		return _get_signature(_params).equals(sign);
	}

	private String _get_signature(Map<String, String> params) {
		StringBuilder builder = new StringBuilder();
		List<String> keys = new ArrayList<>(params.keySet());
		Collections.sort(keys);
		for (String key : keys) {
			if (StringUtils.isEmpty(params.get(key)) || SIGN_KEY.equals(key)) {
				continue;
			}
			builder.append(key).append("=").append(params.get(key)).append("&");
		}
		builder.append("key=").append(CommonEnvConstants.WEIXIN_SECRET_KEY);
		return DigestUtils.md5Hex(builder.toString()).toUpperCase();
	}

}
