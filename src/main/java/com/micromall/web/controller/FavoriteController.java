package com.micromall.web.controller;

import com.micromall.service.FavoriteService;
import com.micromall.web.resp.ResponseEntity;
import com.micromall.web.security.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by zhangzx on 16/3/23.
 * 商品收藏夹
 */
@RestController
@RequestMapping(value = "/api")
@Authentication
public class FavoriteController extends BasisController {

	@Resource
	private FavoriteService favoriteService;

	/**
	 * 收藏夹商品列表
	 *
	 * @return
	 */
	@RequestMapping(value = "/favorite/list")
	public ResponseEntity<?> list() {
		return ResponseEntity.ok(favoriteService.listGoods(getLoginUser().getUid()));
	}

	/**
	 * 加入收藏夹
	 *
	 * @param goodsId 商品id
	 * @return
	 */
	@RequestMapping(value = "/favorite/join")
	public ResponseEntity<?> join(int goodsId) {
		favoriteService.favoriteGoods(getLoginUser().getUid(), goodsId);
		return ResponseEntity.ok();
	}

	/**
	 * 从收藏夹删除
	 *
	 * @param goodsId 商品id
	 * @return
	 */
	@RequestMapping(value = "/favorite/delete")
	public ResponseEntity<?> delete(int goodsId) {
		favoriteService.deleteGoods(getLoginUser().getUid(), goodsId);
		return ResponseEntity.ok();
	}

}
