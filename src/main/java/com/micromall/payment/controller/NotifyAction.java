package com.micromall.payment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhangzx on 16/3/25.
 * 支付回调通知
 */
@Controller
public class NotifyAction {

	@RequestMapping("{channel}/{notifyType}/notify")
	public void notify(HttpServletRequest request, HttpServletResponse response, @PathVariable("channel") String channel,
			@PathVariable("notifyType") String notifyType) {

	}

	@RequestMapping("{channel}/synNotify")
	public void synNotify(HttpServletRequest request, HttpServletResponse response, @PathVariable("channel") String channel) {

	}

}
