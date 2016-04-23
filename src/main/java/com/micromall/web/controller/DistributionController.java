package com.micromall.web.controller;

import com.google.common.collect.Maps;
import com.micromall.web.resp.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by zhangzx on 16/3/28.
 * 分销
 */
@Controller
@RequestMapping(value = "/distribution")
public class DistributionController {

	// 佣金数据

	/**
	 * 佣金总收入
	 * 已提现佣金
	 * 可提现佣金
	 * 审核中佣金
	 * 销售总额
	 * 一级分销商：
	 * 未付款订单
	 * 待发货订单
	 * 待收货订单
	 * 已完成订单
	 * 已取消订单
	 * 二级分销商：
	 * 未付款订单
	 * 待发货订单
	 * 待收货订单
	 * 已完成订单
	 * 已取消订单
	 */

	@RequestMapping(value = "/commission")
	@ResponseBody
	public ResponseEntity<?> commission() {
		Map<String, Object> data = Maps.newHashMap();
		Map<String, Object> lv1 = Maps.newHashMap();
		Map<String, Object> lv2 = Maps.newHashMap();
		data.put("canWithdraw", 0);//可提现佣金
		data.put("withdrawOf", 0);//提现中佣金(审核中佣金)
		data.put("hasWithdraw", 0);//已提现佣金
		data.put("commissionIncome", 0);//佣金总收入
		data.put("totalSales", 0);//销售总额
		data.put("lv1", lv1);//一级分销商
		data.put("lv2", lv2);//一级分销商

		return ResponseEntity.ok();
	}

	/**
	 * 申请提现
	 * @return
	 */
	@RequestMapping(value = "/withdraw/apply")
	@ResponseBody
	public ResponseEntity<?> withdraw_apply() {
		return ResponseEntity.ok();
	}

	/**
	 * 提现记录
	 * @return
	 */
	@RequestMapping(value = "/withdraw/list")
	@ResponseBody
	public ResponseEntity<?> withdraw_list() {
		return ResponseEntity.ok();
	}

}
