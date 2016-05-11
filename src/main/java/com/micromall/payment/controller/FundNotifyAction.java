package com.micromall.payment.controller;

import com.micromall.payment.dto.VerityRequest;
import com.micromall.payment.dto.VerityResult;
import com.micromall.payment.dto.common.PayChannel;
import com.micromall.payment.facade.impl.FundServiceFacadeImpl;
import com.micromall.repository.PaymentRecordMapper;
import com.micromall.repository.entity.PaymentRecord;
import com.micromall.utils.Condition.Criteria;
import com.micromall.utils.IPUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by zhangzx on 16/3/25.
 * 支付回调通知
 */
//@Controller
public class FundNotifyAction {

	@Resource
	private FundServiceFacadeImpl fundServiceFacade;
	@Resource
	private PaymentRecordMapper   paymentRecordMapper;

	/**
	 * 第三方支付平台异步回调通知（共用）
	 *
	 * @param request
	 * @param response
	 * @param channel
	 * @param notifyType
	 */
	@RequestMapping("{channel}/{notifyType}/asynNotify")
	public void notify(HttpServletRequest request, HttpServletResponse response, @PathVariable("channel") String channel,
			@PathVariable("notifyType") String notifyType) {
		VerityRequest verityRequest = new VerityRequest();
		verityRequest.setPayChannel(PayChannel.valueOfCode(channel));
		verityRequest.setRequestData(_read_RequestData(request, channel));
		verityRequest.setRequestIp(IPUtils.getIp(request));
		verityRequest.setNotifyType(notifyType);

		VerityResult result = fundServiceFacade.verity(verityRequest);
		if (StringUtils.isNotEmpty(result.getReturnData())) {
			this.write(response, result.getReturnData());
		}
		return;
	}

	private String _read_RequestData(HttpServletRequest request, String channel) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
			StringBuilder builder = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			return builder.toString();
		} catch (Exception e) {
			return null;
		}
	}

	public void write(HttpServletResponse response, String content) {
		try {
			response.getWriter().write(content);
			response.getWriter().close();
		} catch (Exception e) {
			// ignore
		}
	}

	/**
	 * 第三方支付平台前端同步回调通知
	 *
	 * @param request
	 * @param response
	 * @param channel
	 */
	@RequestMapping("{channel}/synNotify")
	public void synNotify(HttpServletRequest request, HttpServletResponse response, @PathVariable("channel") String channel) {
		/*
		 * VerityRequest req = new VerityRequest(); req.setInstCode(instCode);
		 * req.setResponseMsg(JSONUtils.toJson(request.getParameterMap()));
		 * req.setCreateTime(DateUtils.now());
		 * req.setExtendsion(formatParamter(request.getParameterMap()));
		 * req.getExtendsion().put(ExtendsionKey.NOTIFY_TYPE,
		 * NotifyType.FUND_IN_FRONT); VerityResult result =
		 * fundRPCFacade.verity(req); if (result != null) { try {
		 * response.sendRedirect(result.getResultMessage()); } catch
		 * (IOException e) { logger.info("发送前端错误{}", e); this.write(response,
		 * "验签异常"); } }
		 */
		PayChannel payChannel = PayChannel.valueOfCode(channel);
		String orderNo = null;
		PaymentRecord paymentRecord = paymentRecordMapper
				.selectOneByWhereClause(Criteria.create().andEqualTo("payChannel", payChannel.getCode()).andEqualTo("order_no", orderNo).build());

		if (paymentRecord != null) {
			try {
				response.sendRedirect(paymentRecord.getFrontNotifyUrl());
			} catch (IOException e) {
				// ignore
			}
		}
	}

}
