package com.micromall.web.controller;

import com.micromall.web.extend.Authentication;
import com.micromall.web.resp.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zhangzx on 16/3/21.
 * 我的订单
 */
@Controller
@RequestMapping(value = "/my_orders")
@Authentication
public class MyOrdersController extends BasisController {

	/**
	 * 我的订单列表（包含各种状态的订单）[待付款、已付款/待发货、已收货、已完成]
	 *
	 * @param status 订单状态
	 * @param p      分页页码
	 * @return
	 */
	@RequestMapping(value = "/orders")
	public ResponseEntity<?> orders(int status, int p) {
		return ResponseEntity.ok(true);
	}

	/**
	 * 订单详情
	 *
	 * @param orderNo 订单号
	 * @return
	 */
	@RequestMapping(value = "/details")
	public ResponseEntity<?> details(int orderNo) {
		return ResponseEntity.ok(true);
	}

	/**
	 * 确认收货
	 *
	 * @param orderNo 订单号
	 * @return
	 */
	@RequestMapping(value = "/confirm_delivery")
	public ResponseEntity<?> confirm_delivery(int orderNo) {
		return ResponseEntity.ok(true);
	}

	@RequestMapping(value = "/submit")
	public ResponseEntity<?> submit() {
		return ResponseEntity.ok(true);
	}

}
