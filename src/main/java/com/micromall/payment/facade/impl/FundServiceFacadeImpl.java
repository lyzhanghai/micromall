package com.micromall.payment.facade.impl;

import com.alibaba.fastjson.JSON;
import com.micromall.payment.dto.*;
import com.micromall.payment.dto.common.NotifyType;
import com.micromall.payment.dto.common.PayChannel;
import com.micromall.payment.dto.common.PaymentStatus;
import com.micromall.payment.dto.common.ResultCode;
import com.micromall.payment.facade.FundService;
import com.micromall.repository.PaymentRecordMapper;
import com.micromall.repository.entity.PaymentRecord;
import com.micromall.utils.Condition.Criteria;
import com.micromall.utils.SpringBeanUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangzxiang91@gmail.com
 * @date 2016/04/27.
 */
//@Service
public class FundServiceFacadeImpl implements FundService {

	private final Map<PayChannel, FundService> _Fund_Services = new HashMap<>();
	@Resource
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
			result.setResultCode(ResultCode.INVALID_PARAM);
			return result;
		}
		if (null == request.getPayMethod()) {
			result.setResultMessage("缺少必需的参数(payMethod)");
			result.setResultCode(ResultCode.INVALID_PARAM);
			return result;
		}
		if (null == request.getPlatformType()) {
			result.setResultMessage("缺少必需的参数(platformType)");
			result.setResultCode(ResultCode.INVALID_PARAM);
			return result;
		}
		if (StringUtils.isEmpty(request.getOrderNo())) {
			result.setResultMessage("缺少必需的参数(orderNo)");
			result.setResultCode(ResultCode.INVALID_PARAM);
			return result;
		}
		if (request.getAmount() == null) {
			result.setResultMessage("缺少必需的参数(amount)");
			result.setResultCode(ResultCode.INVALID_PARAM);
			return result;
		}
		if (request.getAmount().compareTo(new BigDecimal(0)) <= 0) {
			result.setResultMessage("订单金额参数不正确");
			result.setResultCode(ResultCode.INVALID_PARAM);
			return result;
		}
		if (StringUtils.isEmpty(request.getBackNotifyUrl())) {
			result.setResultMessage("缺少必需的参数(backNotifyUrl)");
			result.setResultCode(ResultCode.INVALID_PARAM);
			return result;
		}
		if (StringUtils.isEmpty(request.getGoodsName())) {
			result.setResultMessage("缺少必需的参数(goodsName)");
			result.setResultCode(ResultCode.INVALID_PARAM);
			return result;
		}
		if (StringUtils.isEmpty(request.getPayIp())) {
			result.setResultMessage("缺少必需的参数(payIp)");
			result.setResultCode(ResultCode.INVALID_PARAM);
			return result;
		}

		// 2.判断渠道是否存在
		if (!_Fund_Services.containsKey(request.getPayChannel())) {
			result.setResultMessage("支付渠道不可用");
			result.setResultCode(ResultCode.PAY_CHANNEL_NOT_AVAILABLE);
			return result;
		}

		// 判断所有的订单，看是否有已支付的
		PaymentRecord paymentRecord = paymentRecordMapper.selectOneByWhereClause(
				Criteria.create().andEqualTo("status", PaymentStatus.SUCCESS).andEqualTo("order_no", request.getOrderNo()).build());

		if (paymentRecord != null) {
			result.setResultMessage("已经支付成功，请勿重复支付");
			result.setResultCode(ResultCode.REPEAT_PAYMENT);
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
		VerityResult result = _Fund_Services.get(request.getPayChannel()).verity(request);
		try {
			// 判断是否调用成功
			if (result.getResultCode() == ResultCode.SUCCESS) {
				// 入款通知处理
				if (request.getNotifyType().equals(NotifyType.FUND_IN_NOTIFY)) {
					// 判断订单是否已经支付，如果已经支付就不做处理
					Criteria criteria = Criteria.create();
					criteria.andEqualTo("status", PaymentStatus.SUCCESS);
					criteria.andEqualTo("order_no", result.getOrderNo());
					criteria.andEqualTo("deleted", false);
					PaymentRecord paymentRecord = paymentRecordMapper.selectOneByWhereClause(criteria.build());

					if (paymentRecord != null) {
						return result;
					}

					// 获取原订单
					criteria.andEqualTo("", request.getPayChannel().getCode());
					// fundInfo.setAmount(Long.valueOf(result.getExtendsion().get(ExtendsionKey.AMOUNT)));
					paymentRecord = paymentRecordMapper.selectOneByWhereClause(criteria.build());
					if (paymentRecord == null) {
						throw new Exception("原始订单不存在");
					}

					/*// 判断订单的状态,如果是成功或者关闭了就不做处理
					if (paymentRecord.getPayStatus().equals(PaymentStatus.SUCCESS) || paymentRecord.getPayStatus().equals(PaymentStatus.CLOSE)) {
						return result;
					}
					// 判断金额是否一致
					if (!paymentRecord.getAmount().toPlainString().equals(result.getExtendsion().get(ExtendsionKey.AMOUNT))) {
						throw new Exception("金额不一致");
					}
					// 对于成功的订单切包含余额的，需要对余额进行扣款处理
					if (result.getResultCode().equals(ResultCodeEnum.SUCCESS)) {
						if (fundInfo.getExtendsion() != null && fundInfo.getExtendsion().indexOf(ExtendsionKey.BALANCE) >= 0) {
							cashChange(JSONUtils.fromJson(fundInfo.getExtendsion(), HashMap.class), fundInfo.getAmount(), FundTypeEnum.FUND_IN);
						}

					}
					// 订单金额一致修改数据库信息
					fundInfo.setStatus(result.getResultCode().equals(ResultCodeEnum.SUCCESS) ? FundInStatus.SUCCESS : FundInStatus.CLOSE);
					fundInfo.setReturnOrderNo(result.getInstReturnNo());
					String payType = null;
					if (StringUtils.isEmpty(fundInfo.getExtendsion())) {
						fundInfo.setExtendsion(JSONUtils.toJson(result.getExtendsion()));
					} else {
						Map<String, String> extendsion = JSONUtils.fromJson(fundInfo.getExtendsion(), HashMap.class);
						for (String key : result.getExtendsion().keySet()) {
							if (extendsion.containsKey(key)) {
								extendsion.put("resp" + key, result.getExtendsion().get(key));
							} else {
								extendsion.put(key, String.valueOf(result.getExtendsion().get(key)));
							}
						}
						fundInfo.setExtendsion(JSONUtils.toJson(extendsion));
						if (extendsion.containsKey(ExtendsionKey.PAY_TYPE)) {
							payType = extendsion.get(ExtendsionKey.PAY_TYPE);
						}
					}

					String content = NotifyUtils
							.notifyContent(fundInfo.getOutOrderNo(), "" + fundInfo.getAmount(), fundInfo.getStatus(), fundInfo.getInstCode(),
									fundInfo.getChannelCode(), payType, result.getExtendsion().containsKey(ExtendsionKey.BUYER_MAIL) ?
											result.getExtendsion().get(ExtendsionKey.BUYER_MAIL) :
											"");
					logger.info("通知内容{}", content);
					// 发送通知
					fundInfo.setNotifyStatus(NotifyUtils
							.notifyPE(fundInfo.getBackednNotifyUrl(), fundInfo.getInstOrderNo(), content, NotifyType.FUND_IN_NOTIFY,
									paymentTradeNotifyInfoService));
					// 更新订单信息
					this.paymentFundInInfoService.updateById(fundInfo);*/
				}
			}
		} catch (Exception e) {
			result.setResultMessage(e.getMessage());
		}
		return result;
	}
}
