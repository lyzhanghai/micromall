package com.micromall.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zhangzx on 16/3/21.
 * 我的订单
 */
@Controller
@RequestMapping(value = "/my_orders")
public class MyOrdersController {

	/**
	 * 我的订单列表（包含各种状态的订单）[待付款、已付款/待发货、已收货、已完成]
	 *
	 * @return
	 */
	@RequestMapping(value = "/orders")
	public ResponseEntity<?> orders() {
		return ResponseEntity.ok(true);
	}

	/**
	 * 订单详情
	 *
	 * @return
	 */
	@RequestMapping(value = "/details")
	public ResponseEntity<?> details() {
		return ResponseEntity.ok(true);
	}

	/**
	 * 确认收货
	 *
	 * @return
	 */
	@RequestMapping(value = "/confirm_delivery")
	public ResponseEntity<?> confirm_delivery() {
		return ResponseEntity.ok(true);
	}

}
