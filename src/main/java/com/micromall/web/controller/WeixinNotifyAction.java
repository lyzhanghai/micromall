package com.micromall.web.controller;

import com.micromall.service.WeixinPaymentService;
import com.micromall.web.security.annotation.Authentication;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by zhangzx on 16/3/25.
 * 支付回调通知
 */
@Controller
@Authentication(force = false)
public class WeixinNotifyAction {

	private static Logger logger = LoggerFactory.getLogger(WeixinNotifyAction.class);
	@Resource
	private WeixinPaymentService weixinPaymentService;

	@RequestMapping("/weixin/payment/notify")
	public void notify(HttpServletRequest request, HttpServletResponse response) {
		String requestData = null;
		try {
			weixinPaymentService.payNotify((requestData = getRequestData(request)));
			this.write(response, "<xml><return_code>SUCCESS</return_code><return_msg>OK</return_msg></xml>");
		} catch (Exception e) {
			logger.error("微信支付回调通知处理出错, 通知内容：{}", requestData, e);
		}
	}

	private String getRequestData(HttpServletRequest request) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
			StringBuilder builder = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			return builder.toString();
		} catch (Exception e) {
			return null;
		} finally {
			IOUtils.closeQuietly(reader);
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
}
