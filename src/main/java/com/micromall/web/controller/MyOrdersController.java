package com.micromall.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.micromall.repository.entity.Order;
import com.micromall.repository.entity.common.OrderStatus;
import com.micromall.service.OrderService;
import com.micromall.utils.ChainMap;
import com.micromall.utils.HttpUtils;
import com.micromall.utils.HttpUtils.Method;
import com.micromall.web.resp.ResponseEntity;
import com.micromall.web.security.annotation.Authentication;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangzx on 16/3/21.
 * 我的订单
 */
@RestController
@RequestMapping(value = "/api/my_orders")
@Authentication
public class MyOrdersController extends BasisController {

	private static Logger logger = LoggerFactory.getLogger(MyOrdersController.class);

	@Resource
	private OrderService orderService;

	/**
	 * 统计
	 *
	 * @return
	 */
	@RequestMapping(value = "/statistics")
	public ResponseEntity<?> statistics() {
		return ResponseEntity.Success(orderService.statistics(getLoginUser().getUid()));
	}

	/**
	 * 我的订单列表（包含各种状态的订单）[0:待付款、1:待发货、2:待收货、3:已完成、4:退货/取消]
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
		Order order = orderService.getOrder(getLoginUser().getUid(), orderNo);
		if (order == null) {
			return ResponseEntity.Failure("订单不存");
		}

		if (order.getStatus() < OrderStatus.待收货) {
			return ResponseEntity.Failure("订单还未发货");
		}
		Map<String, Object> data = Maps.newHashMap();
		List<Map<String, String>> records = Lists.newArrayList();
		data.put("deliveryCompany", order.getDeliveryCompany());
		data.put("deliveryTime", order.getDeliveryTime());
		data.put("records", records);

		if ("-1".equals(order.getDeliveryCode())) {
			data.put("deliveryNumber", "无");
			// data.add(new ChainMap<>("time", jo.getString("time")).append("text", jo.getString("context")));
		} else {
			data.put("deliveryNumber", order.getDeliveryNumber());
			Map<String, String> headers = Maps.newHashMap();
			headers.put("Host", "m.kuaidi100.com");
			headers.put("Pragma", "no-cache");
			headers.put("Cache-Control", "no-cache");
			headers.put("Accept", "*/*");
			headers.put("X-Requested-With", "XMLHttpRequest");
			headers.put("User-Agent",
					"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
			headers.put("Referer", "http://m.kuaidi100.com/index_all.html");
			// headers.put("Accept-Encoding", "gzip, deflate, sdch");
			headers.put("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");

			Map<String, String> params = Maps.newHashMap();
			params.put("type", order.getDeliveryCode());
			params.put("postid", order.getDeliveryNumber());
			params.put("id", "1");
			params.put("valicode", "");
			params.put("temp", String.valueOf(RandomUtils.nextDouble()));
			org.springframework.http.ResponseEntity<String> responseEntity = HttpUtils
					.execute(Method.GET, headers, "http://m.kuaidi100.com/query", params, null, String.class);
			if (responseEntity == null || responseEntity.getStatusCode() != HttpStatus.OK) {
				logger.error("查询订单物流信息失败, 订单号:{}, 查询参数:{}, 物流查询结果:{}", orderNo, JSON.toJSONString(params),
						responseEntity != null ? responseEntity.toString() : "null");
				data.put("errorMsg", "获取物流信息失败");
			} else {
				try {
					JSONObject jsonObject = JSON.parseObject(responseEntity.getBody());
					if ("200".equals(jsonObject.getString("status"))) {
						JSONArray jsonArray = jsonObject.getJSONArray("data");
						for (int i = 0; i < jsonArray.size(); i++) {
							JSONObject jo = jsonArray.getJSONObject(i);
							records.add(new ChainMap<>("time", jo.getString("time")).append("text", jo.getString("context")));
						}
					} else {
						logger.error("查询订单物流信息失败, 订单号:{}, 查询参数:{}, 物流查询结果:{}", orderNo, JSON.toJSONString(params), responseEntity.getBody());
						data.put("errorMsg", "获取物流信息失败");
					}
				} catch (Exception e) {
					logger.error("查询订单物流信息失败, 订单号:{}, 查询参数:{}, 物流查询结果:{}", orderNo, JSON.toJSONString(params), responseEntity.getBody(), e);
					data.put("errorMsg", "获取物流信息失败");
				}
			}
		}

		return ResponseEntity.Success(records);
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
