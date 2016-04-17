package com.micromall.web.controller.v;

import com.micromall.service.CartService;
import com.micromall.web.controller.BasisController;
import com.micromall.web.extend.UncaughtException;
import com.micromall.web.resp.ResponseEntity;
import com.micromall.web.security.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * Created by zhangzx on 16/3/23.
 * 购物车 TODO 【OK】
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
	@RequestMapping(value = "/list")
	@ResponseBody
	public ResponseEntity<?> list() {
		return ResponseEntity.ok(cartService.listGoods(getLoginUser().getUid()));
	}

	/**
	 * 加入购物车
	 *
	 * @param goodsId 商品id
	 * @param buyNumber 购买数量
	 * @return
	 */
	@UncaughtException(msg = "加入购物车失败")
	@RequestMapping(value = "/join")
	@ResponseBody
	public ResponseEntity<?> join(int goodsId, int buyNumber) {
		if (buyNumber < 1) {
			return ResponseEntity.fail("购买数量不能小于1");
		}
		if (buyNumber > 1000) {
			return ResponseEntity.fail("购买数量不能大于1000");
		}
		cartService.updateCartGoods(getLoginUser().getUid(), goodsId, buyNumber);
		return ResponseEntity.ok();
	}

	/**
	 * 修改购买数量
	 *
	 * @param goodsId 商品id
	 * @param buyNumber 购买数量
	 * @return
	 */
	@UncaughtException(msg = "修改购买数量失败")
	@RequestMapping(value = "/update_buyNumber")
	@ResponseBody
	public ResponseEntity<?> update_buyNumber(int goodsId, int buyNumber) {
		if (buyNumber < 1) {
			return ResponseEntity.fail("购买数量不能小于1");
		}
		cartService.updateCartGoods(getLoginUser().getUid(), goodsId, buyNumber);
		return ResponseEntity.ok();
	}

	/**
	 * 删除商品
	 *
	 * @param goodsId 商品id
	 * @return
	 */
	@UncaughtException(msg = "删除商品失败")
	@RequestMapping(value = "/delete_goods")
	@ResponseBody
	public ResponseEntity<?> delete_goods(int goodsId) {
		cartService.deleteGoods(getLoginUser().getUid(), Arrays.asList(goodsId));
		return ResponseEntity.ok();
	}
}
