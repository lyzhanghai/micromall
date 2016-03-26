//package com.micromall.payment.service.impl;
//
//import com.daze.common.constants.CommonConstants;
//import com.daze.common.constants.CommonResultCode;
//import com.daze.common.utils.DateUtils;
//import com.daze.common.utils.JSONUtils;
//import com.daze.intergation.dto.*;
//import com.daze.intergation.rpc.service.FundRPCFacade;
//import com.daze.intergation.utils.BusinessTypeEnum;
//import com.daze.intergation.utils.ExtendsionKey;
//import com.daze.intergation.utils.InstCodeEnum;
//import com.daze.intergation.utils.ResultCodeEnum;
//import com.daze.server.entity.PaymentFundInInfo;
//import com.daze.server.entity.PaymentRefundInfo;
//import com.daze.server.entity.User;
//import com.daze.server.entity.UserCashRecord;
//import com.daze.server.service.*;
//import com.daze.server.utils.*;
//import com.micromall.payment.MessageConstant;
//import com.micromall.payment.dto.*;
//import com.micromall.payment.service.PaymentService;
//import org.apache.commons.lang3.RandomStringUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.math.BigDecimal;
//import java.util.*;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ConcurrentMap;
//import java.util.concurrent.atomic.AtomicInteger;
//
///**
// * 核心处理类，主要是进行前置校验，和渠道路由，同时会进行订单落地的操作 业务过于庞大，在接下来会把查询部分剥离掉，放到任务管理器当中进行处理
// *
// * @author Mountain
// * @Date 2015-04-28
// */
//@Service("fundRPCFacade")
//public class PaymentServiceFacadeImpl implements PaymentService {
//
//	private final static Logger logger = LoggerFactory.getLogger(PaymentServiceFacadeImpl.class);
//
//	private static final ConcurrentMap<String, PaymentService> map              = new ConcurrentHashMap<String, PaymentService>();
//	// 默认推迟关闭时间，单位是天
//	private static final int                                   CLOSE_DAYS       = 2;
//	// 编辑有哪些可用的渠道
//	private static final StringBuffer                          availableChannel = new StringBuffer();
//	private static       AtomicInteger                         counter          = new AtomicInteger(1000);
//
//	@Autowired
//	private WechatChannelFacade           wechatChunnelFacade;
//	@Autowired
//	private PaymentFundInInfoService      paymentFundInInfoService;
//	@Autowired
//	private PaymentRefundInfoService      paymentRefundInfoService;
//	@Autowired
//	private PaymentTradeNotifyInfoService paymentTradeNotifyInfoService;
//
//	// 处理退款请求
//	@Transactional(rollbackFor = Exception.class)
//	public RefundResult refund(RefundRequest request) {
//		logger.info("[退款请求参数:{}]", JSONUtils.toJson(request));
//		RefundResult result = new RefundResult();
//		result.isSuccess(false);
//		result.setResultCode(ResultCodeEnum.F30001);
//		// 记录下原始的退款金额
//		Long sourceRefundAmount = request.getRefundAmount();
//		// 1、校验输入项
//		if (request.getRefundAmount() == null || request.getRefundAmount() <= 0) {
//			result.setResultMessage(MessageConstant.AMOUNT_NOT_RIGHT);
//			return result;
//		}
//
//		if (StringUtils.isEmpty(request.getOrderNo())
//				&& (request.getExtendsion() == null || StringUtils
//						.isEmpty(request.getExtendsion().get("orderId")))) {
//			result.setResultMessage(MessageConstant.SOURCE_PAYMENT_NO_IS_EMPTY);
//			return result;
//		}
//
//		// 2.获取原支付订单校验退款金额和支付金额
//		PaymentFundInInfo fundInfo = new PaymentFundInInfo();
//		fundInfo.setOutOrderNo(request.getOrderNo());
//		fundInfo.setDeleted("F");
//		fundInfo.setStatus(FundInStatus.SUCCESS);
//		List<PaymentFundInInfo> fundInList = paymentFundInInfoService
//				.find(fundInfo);
//		if (fundInList == null || fundInList.size() != 1) {
//			result.setResultCode(ResultCodeEnum.F30004);
//			result.setResultMessage("原支付订单不存在");
//			result.setOrderNo(request.getOrderNo());
//			return result;
//		} else {
//			fundInfo = fundInList.get(0);
//		}
//		request.setInstCode(fundInfo.getInstCode());
//		request.setChannelCode(fundInfo.getChannelCode());
//		request.setInstOrderNo(generatorOrderNo(fundInfo.getInstCode(),
//				BusinessTypeEnum.REFUND_TYPE));
//		Map<String, String> extendsion = request.getExtendsion() == null ? new HashMap<String, String>()
//				: request.getExtendsion();
//		@SuppressWarnings("unchecked")
//		Map<String, String> fundInExtendsion = JSONUtils.fromJson(
//				fundInfo.getExtendsion(), HashMap.class);
//		// 3、校验渠道是否存在
//		if (!(request.getInstCode().equals(InstCodeEnum.余额支付) || isChannelAvaliable(
//				request.getInstCode(), result))) {
//			return result;
//		}
//
//		// 未发生退款的情况下直接判断
//		if (fundInfo.getRefundStatus() == null
//				|| fundInfo.getRefundStatus().equals(
//						FundInRefundStatus.UNREFUND)) {
//			if (request.getInstCode().equals(InstCodeEnum.余额支付)) {
//				if (request.getRefundAmount() > fundInfo.getAmount()) {
//					result.setResultCode(ResultCodeEnum.F30002);
//					result.setResultMessage(MessageConstant.CAN_REFUND_AMOUNT_LESS_REFUND_AMOUNT);
//					return result;
//				}
//			} else if (fundInExtendsion.containsKey(ExtendsionKey.BALANCE)
//					&& request.getRefundAmount() > Long
//							.valueOf(fundInExtendsion
//									.get(ExtendsionKey.TOTAL_AMOUNT))) {
//				result.setResultCode(ResultCodeEnum.F30002);
//				result.setResultMessage(MessageConstant.CAN_REFUND_AMOUNT_LESS_REFUND_AMOUNT);
//				return result;
//			} else if (!fundInExtendsion.containsKey(ExtendsionKey.BALANCE)
//					&& request.getRefundAmount() > fundInfo.getAmount()) {
//				result.setResultCode(ResultCodeEnum.F30002);
//				result.setResultMessage(MessageConstant.CAN_REFUND_AMOUNT_LESS_REFUND_AMOUNT);
//				return result;
//			}
//
//		} else if (fundInfo.getRefundStatus().equals(
//				FundInRefundStatus.REFUND_ALL)) {
//			result.setResultCode(ResultCodeEnum.F30002);
//			result.setResultMessage(MessageConstant.CAN_REFUND_AMOUNT_LESS_REFUND_AMOUNT);
//			return result;
//		} else {
//			// 判断是否有未处理的订单
//			if (paymentRefundInfoService.findProcessByOrderNo(
//					request.getOrderNo(), new Date()) != null) {
//				result.setResultCode(ResultCodeEnum.F30005);
//				result.setResultMessage(MessageConstant.REFUND_ORDER_PROCESSING);
//				return result;
//			}
//			Long hadRefund = paymentRefundInfoService.findRefundAmount(
//					request.getOrderNo(), request.getSourcePaymentTime());
//			if (hadRefund == null) {
//				hadRefund = 0l;
//			}
//			// 通过订单号获取已经退款的金额
//			long totalAmount = fundInExtendsion
//					.containsKey(ExtendsionKey.TOTAL_AMOUNT) ? Long
//					.valueOf(fundInExtendsion.get(ExtendsionKey.TOTAL_AMOUNT))
//					: fundInfo.getAmount();
//			if (request.getRefundAmount() > totalAmount - hadRefund) {
//				result.setResultCode(ResultCodeEnum.F30002);
//				result.setResultMessage(MessageConstant.CAN_REFUND_AMOUNT_LESS_REFUND_AMOUNT);
//				return result;
//			}
//		}
//
//		// 4、通过支付订单号获取原支付订单信息,获取原支付渠道，补全信息
//		request.setInstOrderNo(generatorOrderNo(request.getInstCode(),
//				BusinessTypeEnum.REFUND_TYPE));
//		request.setCreateTime(new Date());
//		request.setAmount(fundInfo.getAmount());
//		request.setSourcePaymentNo(fundInfo.getInstOrderNo());
//		extendsion.put(ExtendsionKey.SOURCE_PAYMENT_INST_RETURN_NO,
//				fundInfo.getReturnOrderNo());
//		request.setExtendsion(extendsion);
//		// 5、保存请求信息
//		PaymentRefundInfo refundInfo = converRefundRequest(request);
//		if (this.paymentRefundInfoService.insert(refundInfo) <= 0) {
//			result.setResultCode(ResultCodeEnum.E9999);
//			result.setResultMessage(MessageConstant.SYSTEM_ERROR);
//			return result;
//		}
//		// 修改原始订单退款状态
//		fundInfo.setRefundStatus(FundInRefundStatus.REFUND_PROCESSING);
//		paymentFundInInfoService.updateById(fundInfo);
//		// 6、调用具体的处理方法
//		// 假如包含了余额支付
//		if (fundInfo.getInstCode().equals(InstCodeEnum.余额支付)
//				|| (request.getExtendsion() != null && fundInExtendsion
//						.containsKey(ExtendsionKey.BALANCE))) {
//			// 余额支付分支流程
//			try {
//				if (balanceRefund(request)) {
//					result.isSuccess(true);
//					result.setAmount(request.getRefundAmount());
//					result.setResultCode(ResultCodeEnum.SUCCESS);
//					result.setResultMessage(MessageConstant.REDUND_ORDER_SUCCESS);
//					return result;
//				}
//			} catch (Exception e) {
//				logger.error("余额退款失败", e);
//				result.isSuccess(false);
//				result.setResultCode(ResultCodeEnum.F9999);
//				result.setResultMessage(MessageConstant.REFUND_ORDER_FAIL);
//				return result;
//			}
//		}
//		result = getMethod(request.getInstCode()).refund(request);
//		// 7、保存返回的结果
//		if (result.isSuccess()) {
//			if (result.getResultCode().equals(ResultCodeEnum.SUCCESS)) {
//				refundInfo.setStatus(RefundStatus.SUCCESS);
//				// 渠道退款成功判断是否有余额
//				if (request.getRefundAmount() != sourceRefundAmount) {
//					// 余额变更
//					try {
//						cashChange(fundInExtendsion, sourceRefundAmount
//								- request.getRefundAmount(),
//								FundTypeEnum.REFUND);
//					} catch (Exception e) {
//						logger.error("退款余额修改失败");
//					}
//				}
//			} else if (result.getResultCode().equals(ResultCodeEnum.S00001)) {
//				refundInfo.setStatus(RefundStatus.PROCCESSING);
//			} else {
//				refundInfo.setStatus(RefundStatus.FAIL);
//			}
//			refundInfo.setVersion(refundInfo.getVersion());
//			this.paymentRefundInfoService.updateById(refundInfo);
//			// 修改原订单
//			if (!refundInfo.getStatus().equals(RefundStatus.FAIL)) {
//				fundInfo.setRefundStatus(FundInRefundStatus.REFUND_PROCESSING);
//				paymentFundInInfoService.updateById(fundInfo);
//			}
//		}
//		logger.info("[退款处理结果:{}]", JSONUtils.toJson(result));
//		return result;
//	}
//
//	// 处理入款请求
//	public FundInResult fundIn(FundInRequest request) {
//		logger.info("[入款订单参数:{}]", JSONUtils.toJson(request));
//		FundInResult result = new FundInResult();
//		result.isSuccess(false);
//		result.setResultCode(ResultCodeEnum.F30001);
//		if (request.getExtendsion() != null
//				&& request.getExtendsion().containsKey("closeTime")) {
//			request.setCloseTime(new Date(System.currentTimeMillis()
//					+ Long.valueOf(request.getExtendsion().get("closeTime"))));
//		} else {
//			request.setCloseTime(null);
//		}
//		// 判断所有的订单，看是否有已支付的
//		PaymentFundInInfo fundInfo = new PaymentFundInInfo();
//		fundInfo.setStatus(FundInStatus.SUCCESS);
//		fundInfo.setOutOrderNo(request.getOrderNo());
//		List<PaymentFundInInfo> list = this.paymentFundInInfoService
//				.find(fundInfo);
//		if (list != null && list.size() > 0) {
//			result.isSuccess(false);
//			result.setResultMessage("已经支付成功，请勿重复支付");
//			result.setInstOrderNo(request.getOrderNo());
//			return result;
//		}
//		// 如果余额足够支付使用余额进行支付，如果余额不够支付或者出现异常，直接返回给调用放
//		try {
//			if (balancePay(request)) {
//				logger.info("使用余额支付成功");
//				result.isSuccess(true);
//				result.setAmount(request.getAmount());
//				result.setResultCode(ResultCodeEnum.SUCCESS);
//				result.setResultMessage(MessageConstant.ORDER_SUCCESS);
//				result.setSign("success");
//				return result;
//			}
//		} catch (Exception e) {
//			logger.error("余额支付出错{}", e);
//			result.isSuccess(false);
//			result.setAmount(request.getAmount());
//			result.setResultCode(ResultCodeEnum.F9999);
//			result.setResultMessage(MessageConstant.ORDER_PAY_FAIL);
//			return result;
//		}
//
//		// 1、校验输入项
//		if (request.getAmount() == null || request.getAmount() <= 0) {
//			result.setResultMessage(MessageConstant.AMOUNT_NOT_RIGHT);
//			return result;
//		}
//		if (StringUtils.isEmpty(request.getInstCode())) {
//			result.setResultMessage(MessageConstant.INST_CODE_NOT_FUND);
//			return result;
//		}
//		if (StringUtils.isEmpty(request.getOrderNo())) {
//			result.setResultMessage(MessageConstant.PARMAS_NOT_FIT);
//			return result;
//		}
//		if (StringUtils.isEmpty(request.getIp())) {
//			result.setResultMessage(MessageConstant.PARMAS_NOT_FIT);
//			return result;
//		}
//		// 2、判断渠道是否存在
//		if (!isChannelAvaliable(request.getInstCode(), result)) {
//			return result;
//		}
//		if (request.getCreateTime() == null) {
//			request.setCreateTime(DateUtils.now());
//		}
//
//		fundInfo = null;
//		try {
//			fundInfo = getOrderInfo(request.getOrderNo(), request.getInstCode());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		if (fundInfo != null) {
//			if (fundInfo.getStatus().equals(FundInStatus.SUCCESS)) {
//				result.setResultCode(ResultCodeEnum.SUCCESS);
//				result.setResultMessage(MessageConstant.ORDER_SUCCESS);
//				result.setAmount(fundInfo.getAmount());
//				result.setOrderNo(fundInfo.getOutOrderNo());
//				return result;
//			}
//
//			if (fundInfo.getStatus().equals(FundInStatus.CLOSE)) {
//				result.setResultCode(ResultCodeEnum.F9999);
//				result.setResultMessage(MessageConstant.ORDER_CLOSE);
//				result.setAmount(fundInfo.getAmount());
//				result.setOrderNo(fundInfo.getOutOrderNo());
//				return result;
//			}
//
//		}
//
//		// 3、生成支付订单号
//		request.setInstOrderNo(request.getOrderNo());
//		// 4、把请求转换成entity入款
//		fundInfo = converFundInRequest(request);
//		if (this.paymentFundInInfoService.insert(fundInfo) <= 0) {
//			result.setResultCode(ResultCodeEnum.E9999);
//			result.setResultMessage(MessageConstant.INSERT_DATA_HAPPEN_EXCEPTION);
//			return result;
//		}
//		request.setCreateTime(fundInfo.getCreateTime());
//
//		// 5、调用相应的渠道进行处理
//		result = getMethod(request.getInstCode()).fundIn(request);
//		logger.info("[入款订单处理结果:{}]", JSONUtils.toJson(result));
//		return result;
//	}
//
//	@SuppressWarnings("unchecked")
//	@Transactional
//	public VerityResult verity(VerityRequest request) {
//		logger.info("[验签输入参数:{}]", request != null ? JSONUtils.toJson(request)
//				: "");
//		VerityResult result = new VerityResult();
//		result = getMethod(request.getInstCode()).verity(request);
//		try {
//			// 判断是否调用成功
//			if (result.isSuccess()) {
//				if (result.getExtendsion().get(ExtendsionKey.NOTIFY_TYPE)
//						.equals(NotifyType.FUND_IN_FRONT)) {
//					PaymentFundInInfo fundInfo = new PaymentFundInInfo();
//					fundInfo.setInstOrderNo(result.getInstOrderNo().replace(
//							"\"", ""));
//					fundInfo.setDeleted("F");
//					fundInfo.setInstCode(request.getInstCode());
//					List<PaymentFundInInfo> list = paymentFundInInfoService
//							.find(fundInfo);
//					if (list != null && list.size() > 0) {
//						result.setResultMessage(list.get(0).getFrontNotifyUrl());
//					}
//					return result;
//				}
//				// 入款通知处理
//				if (request.getExtendsion().get(ExtendsionKey.NOTIFY_TYPE)
//						.equals(NotifyType.FUND_IN_NOTIFY)
//						&& (result.getResultCode().equals(
//								ResultCodeEnum.SUCCESS) || result
//								.getResultCode().equals(ResultCodeEnum.F9999))) {
//					// 判断订单是否已经支付，如果已经支付就不做处理
//					PaymentFundInInfo fundInfo = new PaymentFundInInfo();
//					fundInfo.setInstOrderNo(result.getInstOrderNo());
//					fundInfo.setDeleted("F");
//					fundInfo.setStatus(FundInStatus.SUCCESS);
//					List<PaymentFundInInfo> list = paymentFundInInfoService
//							.find(fundInfo);
//					if (list != null && list.size() > 0) {
//						logger.info("订单号{},验签结果{}", result.getInstOrderNo(),
//								JSONUtils.toJson(result));
//						return result;
//					}
//					// 获取原订单
//					fundInfo = new PaymentFundInInfo();
//					fundInfo.setInstOrderNo(result.getInstOrderNo());
//					fundInfo.setDeleted("F");
//					fundInfo.setInstCode(request.getInstCode());
//					fundInfo.setAmount(Long.valueOf(result.getExtendsion().get(
//							ExtendsionKey.AMOUNT)));
//					list = paymentFundInInfoService.find(fundInfo);
//					if (list != null && list.size() > 0) {
//						fundInfo = list.get(0);
//					} else {
//						throw new Exception("原始订单不存在");
//					}
//
//					// 判断订单的状态,如果是成功或者关闭了就不做处理
//					if (fundInfo.getStatus().equals(FundInStatus.SUCCESS)
//							|| fundInfo.getStatus().equals(FundInStatus.CLOSE)) {
//						return result;
//					}
//					// 判断金额是否一致
//					if (!fundInfo
//							.getAmount()
//							.toString()
//							.equals(result.getExtendsion().get(
//									ExtendsionKey.AMOUNT))) {
//						throw new Exception("金额不一致");
//					}
//					// 对于成功的订单切包含余额的，需要对余额进行扣款处理
//					if (result.getResultCode().equals(ResultCodeEnum.SUCCESS)) {
//						if (fundInfo.getExtendsion() != null
//								&& fundInfo.getExtendsion().indexOf(
//										ExtendsionKey.BALANCE) >= 0) {
//							cashChange(JSONUtils.fromJson(
//									fundInfo.getExtendsion(), HashMap.class),
//									fundInfo.getAmount(), FundTypeEnum.FUND_IN);
//						}
//
//					}
//					// 订单金额一致修改数据库信息
//					fundInfo.setStatus(result.getResultCode().equals(
//							ResultCodeEnum.SUCCESS) ? FundInStatus.SUCCESS
//							: FundInStatus.CLOSE);
//					fundInfo.setReturnOrderNo(result.getInstReturnNo());
//					String payType = null;
//					if (StringUtils.isEmpty(fundInfo.getExtendsion())) {
//						fundInfo.setExtendsion(JSONUtils.toJson(result
//								.getExtendsion()));
//					} else {
//						Map<String, String> extendsion = JSONUtils.fromJson(
//								fundInfo.getExtendsion(), HashMap.class);
//						for (String key : result.getExtendsion().keySet()) {
//							if (extendsion.containsKey(key)) {
//								extendsion.put("resp" + key, result
//										.getExtendsion().get(key));
//							} else {
//								extendsion.put(key, String.valueOf(result
//										.getExtendsion().get(key)));
//							}
//						}
//						fundInfo.setExtendsion(JSONUtils.toJson(extendsion));
//						if (extendsion.containsKey(ExtendsionKey.PAY_TYPE)) {
//							payType = extendsion.get(ExtendsionKey.PAY_TYPE);
//						}
//					}
//
//					String content = NotifyUtils.notifyContent(
//							fundInfo.getOutOrderNo(),
//							"" + fundInfo.getAmount(),
//							fundInfo.getStatus(),
//							fundInfo.getInstCode(),
//							fundInfo.getChannelCode(),
//							payType,
//							result.getExtendsion().containsKey(
//									ExtendsionKey.BUYER_MAIL) ? result
//									.getExtendsion().get(
//											ExtendsionKey.BUYER_MAIL) : "");
//					logger.info("通知内容{}", content);
//					// 发送通知
//					fundInfo.setNotifyStatus(NotifyUtils.notifyPE(
//							fundInfo.getBackednNotifyUrl(),
//							fundInfo.getInstOrderNo(), content,
//							NotifyType.FUND_IN_NOTIFY,
//							paymentTradeNotifyInfoService));
//					// 更新订单信息
//					this.paymentFundInInfoService.updateById(fundInfo);
//				}
//
//				// 退款通知
//				if (request.getExtendsion().get(ExtendsionKey.NOTIFY_TYPE)
//						.equals(NotifyType.REFUND_NOTIFY)
//						&& (result.getResultCode().equals(
//								ResultCodeEnum.SUCCESS) || result
//								.getResultCode().equals(ResultCodeEnum.F9999))) {
//					// 获取退款订单
//					PaymentRefundInfo refundInfo = new PaymentRefundInfo();
//					refundInfo.setRefundInstOrderNo(result.getInstOrderNo());
//					List<PaymentRefundInfo> list = paymentRefundInfoService
//							.find(refundInfo);
//					if (list != null && list.size() > 0) {
//						refundInfo = list.get(0);
//					} else {
//						throw new Exception("原始订单不存在");
//					}
//					// 幂等性 判断订单的状态,如果是成功或者关闭了就不做处理
//					if (refundInfo.getStatus() != null
//							&& (refundInfo.getStatus().equals(
//									RefundStatus.SUCCESS) || refundInfo
//									.getStatus().equals(RefundStatus.FAIL))) {
//						return result;
//					}
//					// 判断金额是否一致
//					if (!refundInfo
//							.getRefundAmount()
//							.toString()
//							.equals(result.getExtendsion().get(
//									ExtendsionKey.AMOUNT))) {
//						throw new Exception("金额不一致");
//					}
//
//					// 订单金额一致修改数据库信息
//					refundInfo.setStatus(result.getResultCode().equals(
//							ResultCodeEnum.SUCCESS) ? RefundStatus.SUCCESS
//							: RefundStatus.FAIL);
//					refundInfo.setReturnRefundInstOrderNo(result
//							.getInstReturnNo());
//					refundInfo.setExtendsion(JSONUtils.toJson(result
//							.getExtendsion()));
//					// 获取原始交易订单
//					PaymentFundInInfo fundInfo = new PaymentFundInInfo();
//					fundInfo.setDeleted("F");
//					fundInfo.setInstOrderNo(refundInfo.getSourceInstOrderNo());
//					List<PaymentFundInInfo> fundinList = this.paymentFundInInfoService
//							.find(fundInfo);
//					if (fundinList != null && fundinList.size() > 0) {
//						fundInfo = fundinList.get(0);
//					} else {
//						// 不进行任何的操作
//						return result;
//					}
//					// 订单成功的情况下，判断金额是否一致 判断是否修改订单状态
//					if (result.getResultCode().equals(ResultCodeEnum.SUCCESS)
//							&& fundInfo.getAmount().compareTo(
//									paymentRefundInfoService.findRefundAmount(
//											fundInfo.getOutOrderNo(),
//											fundInfo.getCreateTime())) == 0) {
//						fundInfo.setRefundStatus(FundInRefundStatus.REFUND_ALL);
//						this.paymentFundInInfoService.updateById(fundInfo);
//					}
//
//					String content = NotifyUtils.notifyContent(
//							fundInfo.getOutOrderNo(),
//							"" + refundInfo.getRefundAmount(),
//							refundInfo.getStatus(), refundInfo.getInstCode(),
//							refundInfo.getChannelCode());
//					// 发送通知
//					refundInfo.setNotifyStatus(NotifyUtils.notifyPE(
//							refundInfo.getNotifyUrl(),
//							refundInfo.getRefundInstOrderNo(), content,
//							NotifyType.REFUND_NOTIFY,
//							paymentTradeNotifyInfoService));
//					refundInfo.setStatus(result.getResultCode().equals(
//							ResultCodeEnum.SUCCESS) ? RefundStatus.SUCCESS
//							: RefundStatus.FAIL);
//					// 更新订单信息
//					this.paymentRefundInfoService.updateById(refundInfo);
//				}
//				// 调用失败不错处理交给补单模块处理
//			}
//		} catch (Exception e) {
//			logger.error("验签发生错误:{}", e);
//			result.setResultCode(ResultCodeEnum.E9999);
//			result.setResultMessage(e.getMessage());
//		}
//		logger.info("[验签结果:{}]", result != null ? JSONUtils.toJson(result) : "");
//		return result;
//	}
//	// 重新加载可用的渠道
//	public void reloadChannel(StringBuffer channels) {
//		String[] channel = channels.toString().split(",");
//		for (String s : channel) {
//			if (availableChannel.indexOf(s) >= 0) {
//				continue;
//			}
//			availableChannel.append(s).append("#");
//		}
//	}
//
//	// 判断渠道是否存在
//	public boolean isChannelAvaliable(String instCode, final BaseResult result) {
//		if (map.isEmpty()) {
//			getMethod("");
//		}
//		if (availableChannel.toString().contains("#" + instCode + "#")) {
//			return true;
//		}
//		result.setResultCode(CommonResultCode.E40001);
//		result.setResultMessage(MessageConstant.NO_AVAILABLE_CHANNEL_FUND);
//		return false;
//	}
//
//	// 格式化生成订单号
//	private String generatorOrderNo(String instCode, String busType) {
//		StringBuilder instOrderNo = new StringBuilder();
//		String rule = CommonConfig.getOrderRules(instCode + "_" + busType);
//		if (StringUtils.isEmpty(rule)) {
//			return null;
//		}
//		String[] rules = rule.split("\\|");
//		if (rules.length < 2) {
//			return null;
//		}
//		for (int i = 0; i < rules.length; i++) {
//			if (rules[i].startsWith("f:")) {
//				instOrderNo.append(rules[i].substring(2));
//			} else if (rules[i].startsWith("t:")) {
//				instOrderNo.append(DateUtils.formatDate(new Date(),
//						rules[i].substring(2)));
//			} else if (rules[i].startsWith("s:")) {
//				int sequence = counter.addAndGet(1);
//				if (sequence > 9998) {
//					synchronized (counter) {
//						// 防止重复设置
//						if (counter.incrementAndGet() > 9998) {
//							counter.set(1000);
//						}
//					}
//				}
//				instOrderNo.append(RandomStringUtils.random(
//						Integer.valueOf(rules[i].substring(2)), false, true));
//			} else {
//				break;
//			}
//		}
//		return instOrderNo.toString();
//	}
//
//	private BaseFundFacade getMethod(String instCode) {
//		if (map.isEmpty()) {
//			synchronized (map) {
//				if (map.isEmpty()) {
//					map.put(InstCodeEnum.支付宝, alipayChannelFacade);
//					map.put(InstCodeEnum.微信, wechatChunnelFacade);
//					map.put(InstCodeEnum.银联, upmpChannelFacade);
//					map.put(InstCodeEnum.连连科技, lianlianChannelFacade);
//					map.put(InstCodeEnum.阿里百川, openTradeChannelFacade);
//					map.put(InstCodeEnum.汽车钱包, carWalletChannelFacade);
//					availableChannel.append("#").append(InstCodeEnum.银联)
//							.append("#").append(InstCodeEnum.微信).append("#")
//							.append(InstCodeEnum.支付宝).append("#")
//							.append(InstCodeEnum.连连科技).append("#")
//							.append(InstCodeEnum.阿里百川).append("#")
//							.append(InstCodeEnum.汽车钱包).append("#");
//				}
//			}
//		}
//		if (map.containsKey(instCode)) {
//			return map.get(instCode);
//		}
//		return null;
//	}
//
//	// 入款请求对象转换
//	private PaymentFundInInfo converFundInRequest(FundInRequest request) {
//		PaymentFundInInfo fundInfo = new PaymentFundInInfo();
//		fundInfo.setFrontNotifyUrl(request.getFrontNotifyUrl());
//		fundInfo.setBackednNotifyUrl(request.getNotifyUrl());
//		fundInfo.setAmount(request.getAmount());
//		fundInfo.setChannelCode(StringUtils.isEmpty(request.getChannelCode()) ? "00001"
//				: request.getChannelCode());
//		fundInfo.setCloseTime(getCloseTime(request.getCloseTime()));
//		fundInfo.setDeleted("F");
//		fundInfo.setInstCode(request.getInstCode());
//		fundInfo.setOutOrderNo(request.getOrderNo());
//		fundInfo.setInstOrderNo(request.getInstOrderNo());
//		fundInfo.setIpAddress(request.getIp());
//		fundInfo.setStatus(CommonConstants.FUND_IN_STATUS_WAITE_PAY);
//		fundInfo.setRefundStatus("0");
//		if (request.getExtendsion() != null)
//			fundInfo.setExtendsion(JSONUtils.toJson(request.getExtendsion()));
//		request.setCloseTime(fundInfo.getCloseTime());
//		return fundInfo;
//	}
//
//	// 对象转换
//	private PaymentRefundInfo converRefundRequest(RefundRequest request) {
//		PaymentRefundInfo refundInfo = new PaymentRefundInfo();
//		refundInfo.setRefundAmount(request.getRefundAmount());
//		refundInfo
//				.setRefundInstOrderNo(request.getInstOrderNo() == null ? request
//						.getOrderNo() : request.getInstOrderNo());
//		refundInfo.setSourceInstOrderNo(request.getOrderNo());
//		refundInfo.setInstCode(request.getInstCode());
//		refundInfo.setNotifyUrl(request.getNotifyUrl());
//		refundInfo.setCreateTime(new Date());
//		refundInfo.setDeleted("F");
//		refundInfo.setStatus("00");
//		refundInfo.setIpAddress(request.getIp());
//		refundInfo.setRefundType("00");
//		refundInfo.setChannelCode("00001");
//		return refundInfo;
//	}
//
//	// 如果没有设置关闭时间，默认往后推2天
//	private Date getCloseTime(Date date) {
//		if (date == null) {
//			Calendar calendar = Calendar.getInstance();
//			calendar.add(Calendar.DAY_OF_MONTH, CLOSE_DAYS);
//			return calendar.getTime();
//		}
//		return date;
//	}
//
//	// 判断订单是否存在,且支付，删除所有未支付的订单
//	private PaymentFundInInfo getOrderInfo(String orderNo, String instCode)
//			throws Exception {
//		PaymentFundInInfo fundInfo = new PaymentFundInInfo();
//		fundInfo.setOutOrderNo(orderNo);
//		fundInfo.setInstCode(instCode);
//		fundInfo.setDeleted("F");
//		List<PaymentFundInInfo> list = paymentFundInInfoService.find(fundInfo);
//		PaymentFundInInfo result = null;
//		if (list != null && list.size() > 0) {
//			// 删除所有的未支付订单
//			for (PaymentFundInInfo info : list) {
//				if (info.getStatus().equals(FundInStatus.WAIT_PAY)) {
//					paymentFundInInfoService.delete(info.getId());
//				} else {
//					result = info;
//				}
//			}
//		}
//		return result;
//	}
//
//	/**
//	 *
//	 * @Title: balancePay
//	 * @Description: 判断余额是否组工支付，如果足够支付返回TRUE，否则返回FALSE
//	 * @param @param fundInRequest
//	 * @param @return
//	 * @param @throws Exception 设定文件
//	 * @return boolean 返回类型
//	 * @throws
//	 */
//	private boolean balancePay(FundInRequest fundInRequest) throws Exception {
//		// 1、判断是否有使用余额支付
//		if (fundInRequest.getExtendsion() == null
//				|| !fundInRequest.getExtendsion().containsKey(
//						ExtendsionKey.BALANCE)) {
//			return false;
//		}
//		// 2。获取用于余额信息
//		if (!fundInRequest.getExtendsion().containsKey(ExtendsionKey.USER_ID)) {
//			throw new Exception("必要的参数：用户ID缺少");
//		}
//
//		Integer userId = Integer.valueOf(fundInRequest.getExtendsion().get(
//				ExtendsionKey.USER_ID));
//		// 3、判断余额是否足够
//		User user = userService.findById(userId);
//		if (user == null) {
//			throw new Exception("用户不存在");
//		}
//
//		if (Long.valueOf(MoneyUtils.yuan2Fen(user.getCashBalance())) >= fundInRequest
//				.getAmount()) {
//			// 4、足够进行扣款操作
//			// 4.1 用户余额清零操作
//			user.setCashBalance(user.getCashBalance()
//					- Float.valueOf(MoneyUtils.fen2yuan(fundInRequest
//							.getAmount())));
//			if (user.getCashBalance() < 0) {
//				throw new Exception("用户余额不足");
//			}
//			if (userService.updateById(user) <= 0) {
//				throw new Exception("修改用户余额失败");
//			}
//			// 4.2 插入余额使用流水
//			UserCashRecord cashRecord = new UserCashRecord();
//			cashRecord.setCashBalance(user.getCashBalance());
//			cashRecord.setChangeCash(-1
//					* Float.valueOf(MoneyUtils.fen2yuan(fundInRequest
//							.getAmount())));
//			cashRecord.setContent("订单支付抵扣");
//			cashRecord.setUid(userId);
//			cashRecord.setTime(DateUtils.now());
//			cashRecord.setType(5);
//			if (userCashRecordService.insert(cashRecord) <= 0) {
//				throw new Exception("插入余额变动错误");
//			}
//
//			// 4.3 插入入款记录
//			fundInRequest.setInstCode(InstCodeEnum.余额支付);
//			fundInRequest.setInstOrderNo(fundInRequest.getOrderNo());
//			PaymentFundInInfo fundInfo = converFundInRequest(fundInRequest);
//			fundInfo.setStatus(FundInStatus.SUCCESS);
//			if (paymentFundInInfoService.insert(fundInfo) <= 0) {
//				throw new Exception("插入入款记录失败");
//			}
//			// 4.4 往入款通知表当中插入入款记录
//			String content = NotifyUtils.notifyContent(
//					fundInfo.getOutOrderNo(), "0", fundInfo.getStatus(),
//					fundInfo.getInstCode(), fundInfo.getChannelCode(), "5");
//
//			fundInfo.setNotifyStatus(NotifyUtils.notifyPE(
//					fundInfo.getBackednNotifyUrl(), fundInfo.getInstOrderNo(),
//					content, NotifyType.FUND_IN_NOTIFY,
//					paymentTradeNotifyInfoService));
//			paymentFundInInfoService.updateById(fundInfo);
//			return true;
//		} else {
//			// 5、余额不足的情况下，计算出用户还需要支付多晒现金
//			// 5.1计算新的订单金额
//			fundInRequest.getExtendsion().put(ExtendsionKey.TOTAL_AMOUNT,
//					"" + fundInRequest.getAmount());
//			fundInRequest.setAmount(fundInRequest.getAmount()
//					- Long.valueOf(MoneyUtils.yuan2Fen(user.getCashBalance())));
//		}
//		return false;
//	}
//
//	@SuppressWarnings("unchecked")
//	private boolean balanceRefund(RefundRequest refundRequest) throws Exception {
//		// 修改原支付订单的退款信息
//		PaymentFundInInfo fundInfo = new PaymentFundInInfo();
//		fundInfo.setInstOrderNo(refundRequest.getOrderNo());
//		fundInfo.setStatus(FundInStatus.SUCCESS);
//		List<PaymentFundInInfo> list = this.paymentFundInInfoService
//				.find(fundInfo);
//		if (list != null && list.size() == 1) {
//			fundInfo = list.get(0);
//		} else {
//			throw new Exception("原支付订单信息不存在");
//		}
//		// 判断是否全额使用余额付款
//		if (refundRequest.getInstCode().equals(InstCodeEnum.余额支付)) {
//			// 查询到用户账户
//			User user = this.userService.findById(Integer.valueOf(refundRequest
//					.getExtendsion().get(ExtendsionKey.USER_ID)));
//			if (user == null) {
//				throw new Exception("用户不存在");
//			}
//			// 修改用户账户信息
//			user.setCashBalance(user.getCashBalance()
//					+ Float.valueOf(MoneyUtils.fen2yuan(refundRequest
//							.getRefundAmount())));
//			if (userService.updateById(user) <= 0) {
//				throw new Exception("修改余额信息失败");
//			}
//			// 插入余额变动记录
//			UserCashRecord cashRecord = new UserCashRecord();
//			cashRecord.setCashBalance(user.getCashBalance());
//			cashRecord.setChangeCash(Float.valueOf(MoneyUtils
//					.fen2yuan(refundRequest.getRefundAmount())));
//			cashRecord.setContent("订单退款");
//			cashRecord.setUid(user.getId());
//			cashRecord.setType(5);
//			cashRecord.setTime(new Date());
//			userCashRecordService.insert(cashRecord);
//			return true;
//		} else {
//			// 判断原支付金额跟退款金额
//			Map<String, String> extendsion = JSONUtils.fromJson(
//					fundInfo.getExtendsion(), HashMap.class);
//			if (extendsion.containsKey(ExtendsionKey.BALANCE)) {
//				PaymentRefundInfo refundInfo = new PaymentRefundInfo();
//				refundInfo.setDeleted("F");
//				refundInfo.setStatus(RefundStatus.PROCCESSING);
//				refundInfo.setSourceInstOrderNo(fundInfo.getInstOrderNo());
//				List<PaymentRefundInfo> processingRefund = this.paymentRefundInfoService
//						.find(refundInfo);
//				// 含有处理中的订单直接返回，不继续往下做处理，因为存在风险性
//				if (processingRefund != null && processingRefund.size() > 1) {
//					return false;
//				}
//				long totalAmount = Long.valueOf(extendsion
//						.get(ExtendsionKey.TOTAL_AMOUNT));
//				// 余额支付的金额
//				long balanceAmount = totalAmount - fundInfo.getAmount();
//				// 获取可以已经退款
//				long refunded = paymentRefundInfoService.findRefundAmount(
//						fundInfo.getOutOrderNo(), fundInfo.getCreateTime());
//				// 判断是否可退
//				if (0 > totalAmount - refunded) {
//					throw new Exception("退款余额不足");
//				}
//				// 判断已退金额是否大于余额支付金额
//				if (refunded > balanceAmount) {
//					// 余额不足以支付，先扣除余额部分
//					if (refundRequest.getRefundAmount() == refunded) {
//						refundRequest.setRefundAmount(refunded - balanceAmount);
//					}
//					return false;
//				} else {
//					// 余额足以支付，扣除余额部分
//					cashChange(extendsion, refundRequest.getRefundAmount(),
//							FundTypeEnum.REFUND);
//					// 把退款中的订单设置为退款成功
//					processingRefund.get(0).setStatus(RefundStatus.SUCCESS);
//					paymentRefundInfoService
//							.updateById(processingRefund.get(0));
//				}
//				return true;
//			}
//		}
//
//		return false;
//	}
//
//	/**
//	 * @throws Exception
//	 * @Title: cashChange
//	 * @Description: 资金变动,一旦失败，发送监控系统
//	 * @param @param extendsion
//	 * @param @param amount
//	 * @param @param fundType 设定文件
//	 * @return void 返回类型
//	 * @throws
//	 */
//	public void cashChange(Map<String, String> extendsion, long amount,
//			String fundType) throws Exception {
//		// 判断业务类型
//		long change = 0;
//		long totalAmount = Long.valueOf(extendsion
//				.get(ExtendsionKey.TOTAL_AMOUNT));
//		UserCashRecord userCashRecord = new UserCashRecord();
//		userCashRecord.setDeleted("N");
//		User user = this.userService.findById(Integer.valueOf(extendsion
//				.get(ExtendsionKey.USER_ID)));
//		if (fundType.equals(FundTypeEnum.FUND_IN)) {
//			change = amount - totalAmount;
//			userCashRecord.setContent("订单支付抵扣");
//			// 做余额判断
//		} else {
//			change = amount;
//			userCashRecord.setContent("订单退款");
//		}
//		user.setCashBalance(user.getCashBalance()
//				+ new BigDecimal(MoneyUtils.fen2yuan(change)).floatValue());
//		if (user.getCashBalance() < 0) {
//			logger.info("余额不足,扣款失败");
//			throw new Exception("余额不足,扣款失败");
//		}
//		// 增加流水
//		userCashRecord.setCashBalance(user.getCashBalance());
//		userCashRecord.setUid(user.getId());
//		userCashRecord.setChangeCash((new BigDecimal(MoneyUtils
//				.fen2yuan(change)).floatValue()));
//		userCashRecord.setTime(DateUtils.now());
//		userCashRecord.setType(Integer.valueOf(extendsion.get("payType")));
//		if (userCashRecordService.insert(userCashRecord) <= 0
//				|| userService.updateById(user) <= 0) {
//			throw new Exception("更新用户流水失败");
//		}
//	}
//}
