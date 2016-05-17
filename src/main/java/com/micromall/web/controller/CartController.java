package com.micromall.web.controller;

import com.micromall.service.CartService;
import com.micromall.web.resp.ResponseEntity;
import com.micromall.web.security.annotation.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * Created by zhangzx on 16/3/23.
 * 购物车
 */
@RestController
@RequestMapping(value = "/api")
@Authentication
public class CartController extends BasisController {

	@Resource
	private CartService cartService;

	/**
	 * 购物车商品列表
	 *
	 * @return
	 */
	@RequestMapping(value = "/cart/list")
	public ResponseEntity<?> list() {
		return ResponseEntity.Success(cartService.listGoods(getLoginUser().getUid()));
	}

	/**
	 * 加入购物车
	 *
	 * @param goodsId 商品id
	 * @param buyNumber 购买数量
	 * @return
	 */
	@RequestMapping(value = "/cart/join")
	public ResponseEntity<?> join(int goodsId, int buyNumber) {
		if (buyNumber < 1) {
			return ResponseEntity.Failure("购买数量不能小于1");
		}
		if (buyNumber > 1000) {
			return ResponseEntity.Failure("购买数量不能大于1000");
		}
		cartService.updateCartGoods(getLoginUser().getUid(), goodsId, buyNumber);
		return ResponseEntity.Success();
	}

	/**
	 * 修改购买数量
	 *
	 * @param goodsId 商品id
	 * @param buyNumber 购买数量
	 * @return
	 */
	@RequestMapping(value = "/cart/update_buyNumber")
	public ResponseEntity<?> update_buyNumber(int goodsId, int buyNumber) {
		if (buyNumber < 1) {
			return ResponseEntity.Failure("购买数量不能小于1件");
		}
		cartService.updateCartGoods(getLoginUser().getUid(), goodsId, buyNumber);
		return ResponseEntity.Success();
	}

	/**
	 * 删除商品
	 *
	 * @param goodsId 商品id
	 * @return
	 */
	@RequestMapping(value = "/cart/delete")
	public ResponseEntity<?> delete(int goodsId) {
		cartService.deleteGoods(getLoginUser().getUid(), Arrays.asList(goodsId));
		return ResponseEntity.Success();
	}

	@RequestMapping(value = "/cart/delete_all")
	public ResponseEntity<?> delete_all() {
		cartService.deleteAllGoods(getLoginUser().getUid());
		return ResponseEntity.Success();
	}
}
