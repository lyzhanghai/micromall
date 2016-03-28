package com.micromall.web.controller;

import com.micromall.service.OrderService;
import com.micromall.web.extend.Authentication;
import com.micromall.web.extend.UncaughtException;
import com.micromall.web.resp.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by zhangzx on 16/3/21.
 * 我的订单
 */
@Controller
@RequestMapping(value = "/my_orders")
@Authentication
public class MyOrdersController extends BasisController {

	@Resource
	private OrderService orderService;

	/**
	 * 我的订单列表（包含各种状态的订单）[待付款、已付款/待发货、已收货、已完成]
	 *
	 * @param status 订单状态
	 * @param p      分页页码
	 * @return
	 */
	@UncaughtException(msg = "加载订单列表失败")
	@RequestMapping(value = "/orders")
	public ResponseEntity<?> orders(int status, int p) {
		return ResponseEntity.ok(orderService.findOrders(getLoginUser().getUid(), status, p));
	}

	/**
	 * 订单详情
	 *
	 * @param orderNo 订单号
	 * @return
	 */
	@UncaughtException(msg = "加载订单详情失败")
	@RequestMapping(value = "/details")
	public ResponseEntity<?> details(String orderNo) {
		return ResponseEntity.ok(orderService.getOrderDetails(getLoginUser().getUid(), orderNo));
	}

	/**
	 * 确认收货
	 *
	 * @param orderNo 订单号
	 * @return
	 */
	@UncaughtException(msg = "确认收货失败")
	@RequestMapping(value = "/confirm_delivery")
	public ResponseEntity<?> confirm_delivery(String orderNo) {
		return ResponseEntity.ok(orderService.confirmDelivery(getLoginUser().getUid(), orderNo));
	}

	/**
	 * 关闭订单
	 *
	 * @param orderNo 订单号
	 * @return
	 */
	@UncaughtException(msg = "关闭订单失败")
	@RequestMapping(value = "/close")
	public ResponseEntity<?> close(String orderNo) {
		return ResponseEntity.ok(orderService.closeOrder(getLoginUser().getUid(), orderNo));
	}

	/**
	 * @param goodsIds     商品id
	 * @param couponId     使用的优惠券id
	 * @param leaveMessage 留言
	 * @param addressId    收货地址id
	 * @return
	 */
	@UncaughtException(msg = "提交订单失败")
	@RequestMapping(value = "/submit")
	public ResponseEntity<?> submit(String goodsIds, String couponId, String leaveMessage, int addressId) {
		return ResponseEntity.ok(orderService.createOrder());
	}

}
