package com.micromall.web.controller.admin;

import com.micromall.service.OrderService;
import com.micromall.web.controller.BasisController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by zhangzx on 16/3/21.
 * 订单管理
 */
@RestController
@RequestMapping(value = "/admin/orders")
public class AdminOrdersController extends BasisController {

	@Resource
	private OrderService orderService;

	// 订单列表

	// 订单详情

	// 关闭订单

	// 申请退款订单列表

	// 申请退款审核

	// 发货
}
