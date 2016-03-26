package com.micromall.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zhangzx on 16/3/21.
 * 商品（收藏：favorite）
 */
@Controller
@RequestMapping(value = "/goods")
public class GoodsController {

	/**
	 * 分类下的商品列表
	 *
	 * @return
	 */
	@RequestMapping(value = "/category_get_goods")
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

}
