package com.micromall.web.controller;

import com.micromall.service.FavoriteService;
import com.micromall.web.security.Authentication;
import com.micromall.web.extend.UncaughtException;
import com.micromall.web.resp.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

	/**
	 * 收藏夹商品列表
	 *
	 * @return
	 */
	@UncaughtException(msg = "加载商品列表失败")
	@RequestMapping(value = "/goodses")
	@ResponseBody
	public ResponseEntity<?> goodses() {
		return ResponseEntity.ok(favoriteService.goodses(getLoginUser().getUid()));
	}

	/**
	 * 加入收藏夹
	 *
	 * @param goodsId 商品id
	 * @return
	 */
	@UncaughtException(msg = "加入收藏夹失败")
	@RequestMapping(value = "/join_favorite")
	@ResponseBody
	public ResponseEntity<?> join_favorite(int goodsId) {
		return ResponseEntity.ok(favoriteService.joinFavorite(getLoginUser().getUid(), goodsId));
	}

	/**
	 * 从收藏夹删除
	 *
	 * @param goodsId 商品id
	 * @return
	 */
	@UncaughtException(msg = "删除商品失败")
	@RequestMapping(value = "/delete_goods")
	@ResponseBody
	public ResponseEntity<?> delete_goods(int goodsId) {
		return ResponseEntity.ok(favoriteService.deleteGoods(getLoginUser().getUid(), goodsId));
	}

}
