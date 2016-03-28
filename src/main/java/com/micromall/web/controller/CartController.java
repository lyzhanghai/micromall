package com.micromall.web.controller;

import com.micromall.service.CartService;
import com.micromall.web.extend.Authentication;
import com.micromall.web.extend.UncaughtException;
import com.micromall.web.resp.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by zhangzx on 16/3/23.
 * 购物车
 * <p/>
 * 用户支付完后, 从购物车删除商品记录
 * 查询时如果遇到下架，或删除的商品，自动从购物车中删除
 */
@Controller
@RequestMapping(value = "/cart")
@Authentication
public class CartController extends BasisController {

	@Resource
	private CartService cartService;

	/**
	 * 购物车商品列表
	 *
	 * @return
	 */
	@UncaughtException(msg = "加载商品列表失败")
	@RequestMapping(value = "/goodses")
	public ResponseEntity<?> goodses() {
		return ResponseEntity.ok(cartService.goodses(getLoginUser().getUid()));
	}

	/**
	 * 加入购物车
	 *
	 * @param goodsId   商品id
	 * @param buyNumber 购买数量
	 * @return
	 */
	@UncaughtException(msg = "加入购物车失败")
	@RequestMapping(value = "/join_cart")
	public ResponseEntity<?> join_cart(int goodsId, int buyNumber) {
		// TODO 参数验证
		return ResponseEntity.ok(cartService.joinCart(getLoginUser().getUid(), goodsId, buyNumber));
	}

	/**
	 * 修改购买数量
	 *
	 * @param goodsId   商品id
	 * @param buyNumber 购买数量
	 * @return
	 */
	@UncaughtException(msg = "修改购买数量失败")
	@RequestMapping(value = "/update_buyNumber")
	public ResponseEntity<?> update_buyNumber(int goodsId, int buyNumber) {
		// TODO 参数验证
		return ResponseEntity.ok(cartService.updateBuyNumber(getLoginUser().getUid(), goodsId, buyNumber));
	}

	/**
	 * 删除商品
	 *
	 * @param goodsId 商品id
	 * @return
	 */
	@UncaughtException(msg = "删除商品失败")
	@RequestMapping(value = "/delete_goods")
	public ResponseEntity<?> delete_goods(int goodsId) {
		return ResponseEntity.ok(cartService.deleteGoods(getLoginUser().getUid(), goodsId));
	}

}
