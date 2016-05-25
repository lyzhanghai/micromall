package com.micromall.web.controller;

import com.micromall.service.WeixinPaymentService;
import com.micromall.web.security.annotation.Authentication;
import org.apache.commons.io.IOUtils;
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

	@Resource
	private WeixinPaymentService weixinPaymentService;

	@RequestMapping("/payment/weixin/notify")
	public void notify(HttpServletRequest request, HttpServletResponse response) {
		try {
			weixinPaymentService.payNotify(getRequestData(request));
			this.write(response, "");
		} catch (Exception e) {
			// TODO: handle exception
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
