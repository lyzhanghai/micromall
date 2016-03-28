package com.micromall.web.controller;

import com.micromall.service.FavoriteService;
import com.micromall.web.extend.Authentication;
import com.micromall.web.resp.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by zhangzx on 16/3/23.
 * 商品收藏夹
 */
@Controller
@RequestMapping(value = "/favorite")
@Authentication
public class FavoriteController extends BasisController {

	@Resource
	private FavoriteService favoriteService;

	// 收藏夹商品列表
	@RequestMapping(value = "/goodses")
	public ResponseEntity<?> goodses() {
		return ResponseEntity.ok(favoriteService.goodses(getLoginUser().getUid()));
	}

	// 加入收藏夹
	@RequestMapping(value = "/join_favorite")
	public ResponseEntity<?> join_favorite(int goodsId) {
		return ResponseEntity.ok(favoriteService.joinFavorite(getLoginUser().getUid(), goodsId));
	}

	// 从收藏夹删除
	@RequestMapping(value = "/delete_goods")
	public ResponseEntity<?> delete_goods(int goodsId) {
		return ResponseEntity.ok(favoriteService.deleteGoods(getLoginUser().getUid(), goodsId));
	}

}
