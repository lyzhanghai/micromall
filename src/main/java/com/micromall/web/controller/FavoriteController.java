package com.micromall.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zhangzx on 16/3/23.
 * 商品收藏夹
 */
@Controller
@RequestMapping(value = "/favorite")
public class FavoriteController {

	// 收藏夹商品列表
	@RequestMapping(value = "/goodses")
	public ResponseEntity<?> goodses() {
		return ResponseEntity.ok(true);
	}

	// 加入收藏夹
	@RequestMapping(value = "/join_favorite")
	public ResponseEntity<?> join_favorite() {
		return ResponseEntity.ok(true);
	}

	// 从收藏夹删除
	@RequestMapping(value = "/delete")
	public ResponseEntity<?> delete() {
		return ResponseEntity.ok(true);
	}

}
