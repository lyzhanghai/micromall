/*
 * Copyright (C), 2014-2015, 杭州小卡科技有限公司
 * Created by ciwei@xiaokakeji.com on 2016/04/27.
 */
package com.micromall.payment.facade.impl;

import com.alibaba.fastjson.JSON;
import com.micromall.entity.PaymentRecord;
import com.micromall.payment.dto.*;
import com.micromall.payment.dto.ext.PayChannel;
import com.micromall.payment.dto.ext.ResultCodeEnum;
import com.micromall.payment.facade.FundService;
import com.micromall.payment.utils.PaymentStatus;
import com.micromall.repository.PaymentRecordMapper;
import com.micromall.utils.Condition.Criteria;
import com.micromall.utils.SpringBeanUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangzxiang91@gmail.com
 * @date 2016/04/27.
 */
public class FundServiceFacadeImpl implements FundService {

	private final Map<PayChannel, FundService> _Fund_Services = new HashMap<>();
	private PaymentRecordMapper paymentRecordMapper;

	@PostConstruct
	public void initPayChannels() {
		_Fund_Services.put(PayChannel.支付宝, (FundService)SpringBeanUtils.get(""));
		_Fund_Services.put(PayChannel.微信, (FundService)SpringBeanUtils.get(""));
		_Fund_Services.put(PayChannel.银联, (FundService)SpringBeanUtils.get(""));
		_Fund_Services.put(PayChannel.百度钱包, (FundService)SpringBeanUtils.get(""));
		_Fund_Services.put(PayChannel.余额支付, (FundService)SpringBeanUtils.get(""));
	}

	@Override
	public PaymentResult payment(PaymentRequest request) {
		PaymentResult result = new PaymentResult();

		// 1.校验输入项
		if (null == request.getPayChannel()) {
			result.setResultMessage("缺少必需的参数(payChannel)");
			result.setResultCode(ResultCodeEnum.INVALID_PARAM);
			return result;
		}
		if (null == request.getPayMethod()) {
			result.setResultMessage("缺少必需的参数(payMethod)");
			result.setResultCode(ResultCodeEnum.INVALID_PARAM);
			return result;
		}
		if (null == request.getPlatformType()) {
			result.setResultMessage("缺少必需的参数(platformType)");
			result.setResultCode(ResultCodeEnum.INVALID_PARAM);
			return result;
		}
		if (StringUtils.isEmpty(request.getOrderNo())) {
			result.setResultMessage("缺少必需的参数(orderNo)");
			result.setResultCode(ResultCodeEnum.INVALID_PARAM);
			return result;
		}
		if (request.getAmount() == null) {
			result.setResultMessage("缺少必需的参数(amount)");
			result.setResultCode(ResultCodeEnum.INVALID_PARAM);
			return result;
		}
		if (request.getAmount().compareTo(new BigDecimal(0)) <= 0) {
			result.setResultMessage("订单金额参数不正确");
			result.setResultCode(ResultCodeEnum.INVALID_PARAM);
			return result;
		}
		if (StringUtils.isEmpty(request.getBackNotifyUrl())) {
			result.setResultMessage("缺少必需的参数(backNotifyUrl)");
			result.setResultCode(ResultCodeEnum.INVALID_PARAM);
			return result;
		}
		if (StringUtils.isEmpty(request.getGoodsName())) {
			result.setResultMessage("缺少必需的参数(goodsName)");
			result.setResultCode(ResultCodeEnum.INVALID_PARAM);
			return result;
		}
		if (StringUtils.isEmpty(request.getPayIp())) {
			result.setResultMessage("缺少必需的参数(payIp)");
			result.setResultCode(ResultCodeEnum.INVALID_PARAM);
			return result;
		}

		// 2.判断渠道是否存在
		if (!_Fund_Services.containsKey(request.getPayChannel())) {
			result.setResultMessage("支付渠道不可用");
			result.setResultCode(ResultCodeEnum.PAY_CHANNEL_NOT_AVAILABLE);
			return result;
		}

		// 判断所有的订单，看是否有已支付的
		PaymentRecord paymentRecord = paymentRecordMapper.selectOneByWhereClause(
				Criteria.create().andEqualTo("status", PaymentStatus.SUCCESS).andEqualTo("order_no", request.getOrderNo()).build());

		if (paymentRecord != null) {
			result.setResultMessage("已经支付成功，请勿重复支付");
			result.setResultCode(ResultCodeEnum.REPEAT_PAYMENT);
			return result;
		}

		// TODO 重复支付请求处理

		paymentRecord = new PaymentRecord();
		paymentRecord.setOrderNo(request.getOrderNo());
		paymentRecord.setAmount(request.getAmount());
		paymentRecord.setPayChannel(request.getPayChannel().getCode());
		paymentRecord.setPayMethod(request.getPayMethod().getCode());
		paymentRecord.setPlatformType(request.getPlatformType().getCode());
		paymentRecord.setBackNotifyUrl(request.getBackNotifyUrl());
		paymentRecord.setFrontNotifyUrl(request.getFrontNotifyUrl());
		paymentRecord.setPayIp(request.getPayIp());
		paymentRecord.setExtend(request.getExtendParams() != null ? JSON.toJSONString(request.getExtendParams()) : null);
		paymentRecord.setPayStatus(PaymentStatus.WAIT_PAY);
		paymentRecord.setNotifyStatus(null);
		paymentRecord.setRefundStatus(null);
		paymentRecord.setCreateTime(new Date());
		paymentRecord.setUpdateTime(null);

		// 5、调用相应的渠道进行处理
		return _Fund_Services.get(request.getPayChannel()).payment(request);
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
