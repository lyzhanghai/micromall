package com.micromall.web.controller;

import com.micromall.service.LogisticsService;
import com.micromall.web.extend.UncaughtException;
import com.micromall.web.resp.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by zhangzx on 16/3/28.
 * 物流查询
 */
@Controller
@RequestMapping(value = "/logistics")
public class LogisticsController {

	@Resource
	private LogisticsService logisticsService;

	@RequestMapping(value = "/records")
	@UncaughtException(msg = "获取订单物流信息失败")
	public ResponseEntity<?> get(String orderNo) {
		return ResponseEntity.ok(logisticsService.getLogisticsRecords(orderNo));
	}
}
