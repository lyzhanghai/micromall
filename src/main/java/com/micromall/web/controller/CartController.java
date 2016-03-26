package com.micromall.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zhangzx on 16/3/23.
 * 购物车
 */
@Controller
@RequestMapping(value = "/cart")
public class CartController {

	// 购物车商品列表
	@RequestMapping(value = "/goodses")
	public ResponseEntity<?> goodses() {
		return ResponseEntity.ok(true);
	}

	// 加入购物车
	@RequestMapping(value = "/join_cart")
	public ResponseEntity<?> join_cart() {
		return ResponseEntity.ok(true);
	}

	// 修改购买数量
	@RequestMapping(value = "/update_buyNumber")
	public ResponseEntity<?> update_buyNumber() {
		return ResponseEntity.ok(true);
	}

	// 删除商品
	@RequestMapping(value = "/delete")
	public ResponseEntity<?> delete() {
		return ResponseEntity.ok(true);
	}

}
