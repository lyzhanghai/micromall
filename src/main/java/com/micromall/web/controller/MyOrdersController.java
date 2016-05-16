package com.micromall.web.controller;

import com.micromall.service.OrderService;
import com.micromall.web.resp.ResponseEntity;
import com.micromall.web.security.annotation.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by zhangzx on 16/3/21.
 * 我的订单
 */
@RestController
@RequestMapping(value = "/api/my_orders")
@Authentication
public class MyOrdersController extends BasisController {

	@Resource
	private OrderService orderService;

	/**
	 * 我的订单列表（包含各种状态的订单）[待付款、待发货、待收货、已完成、退货/取消]
	 *
	 * @param status 订单状态
	 * @return
	 */
	@RequestMapping(value = "/orders")
	public ResponseEntity<?> orders(int status, @RequestParam(defaultValue = "1") int page, Integer limit) {
		return ResponseEntity.Success(orderService.findOrders(getLoginUser().getUid(), status, page, resizeLimit(limit)));
	}

	/**
	 * 订单详情
	 *
	 * @param orderNo 订单号
	 * @return
	 */
	@RequestMapping(value = "/details")
	public ResponseEntity<?> details(String orderNo) {
		return ResponseEntity.Success(orderService.getOrderDetails(getLoginUser().getUid(), orderNo));
	}

	/**
	 * 订单物流信息
	 *
	 * @param orderNo 订单号
	 * @return
	 */
	@RequestMapping(value = "/logistics")
	public ResponseEntity<?> logistics(String orderNo) {
		return ResponseEntity.Success(orderService.getLogisticsInfo(orderNo));
	}

	/**
	 * 确认收货
	 *
	 * @param orderNo 订单号
	 * @return
	 */
	@RequestMapping(value = "/confirm_delivery")
	public ResponseEntity<?> confirm_delivery(String orderNo) {
		return ResponseEntity.Success(orderService.confirmDelivery(getLoginUser().getUid(), orderNo));
	}

	/**
	 * 申请退款
	 *
	 * @param orderNo 订单号
	 * @return
	 */
	@RequestMapping(value = "/apply_refund")
	public ResponseEntity<?> apply_refund(String orderNo, String refundReason) {
		return ResponseEntity.Success(orderService.applyRefund(getLoginUser().getUid(), orderNo, refundReason));
	}

	/**
	 * 关闭订单
	 *
	 * @param orderNo 订单号
	 * @return
	 */
	@RequestMapping(value = "/close")
	public ResponseEntity<?> close(String orderNo) {
		return ResponseEntity.Success(orderService.closeOrder(getLoginUser().getUid(), orderNo));
	}

}
