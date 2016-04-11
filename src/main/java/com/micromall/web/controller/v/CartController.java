package com.micromall.web.controller.v;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.micromall.service.CartService;
import com.micromall.service.ShippingAddressService;
import com.micromall.service.vo.CartGoodsDTO;
import com.micromall.web.controller.BasisController;
import com.micromall.web.extend.UncaughtException;
import com.micromall.web.resp.ResponseEntity;
import com.micromall.web.security.Authentication;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	private CartService            cartService;
	@Resource
	private ShippingAddressService shippingAddressService;

	/**
	 * 购物车商品列表
	 *
	 * @return
	 */
	@UncaughtException(msg = "加载商品列表失败")
	@RequestMapping(value = "/goodses")
	@ResponseBody
	public ResponseEntity<?> goodses() {
		return ResponseEntity.ok(cartService.goodses(getLoginUser().getUid()));
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
			return ResponseEntity.fail("购买数量不能小于0");
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
			return ResponseEntity.fail("购买数量不能小于0");
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
		return ResponseEntity.ok(cartService.deleteGoods(getLoginUser().getUid(), Arrays.asList(goodsId)));
	}

	/**
	 * 结算
	 *
	 * @param goodsIds 商品id
	 * @return
	 */
	@RequestMapping(value = "/settle")
	@ResponseBody
	public ResponseEntity<?> settle(String goodsIds) {
		if (StringUtils.isBlank(goodsIds)) {
			return ResponseEntity.fail("请选择要结算的商品");
		}

		List<Map<String, Object>> settleGoodses = Lists.newArrayList();
		BigDecimal totalPrice = new BigDecimal(0);

		Set<String> _goodsIds = Sets.newHashSet(StringUtils.split(goodsIds, ","));
		List<CartGoodsDTO> goodsList = cartService.goodses(getLoginUser().getUid());

		for (CartGoodsDTO goods : goodsList) {
			if (_goodsIds.contains(String.valueOf(goods.getGoodsId()))) {
				Map<String, Object> _goods = Maps.newHashMap();
				_goods.put("id", goods.getGoodsId());
				_goods.put("title", goods.getTitle());
				_goods.put("image", goods.getImage());
				_goods.put("price", goods.getPrice());
				_goods.put("buyNumber", goods.getBuyNumber());
				BigDecimal _totalPrice = goods.getPrice().multiply(new BigDecimal(goods.getBuyNumber())).setScale(2);
				_goods.put("totalPrice", _totalPrice);
				settleGoodses.add(_goods);
				totalPrice = totalPrice.add(_totalPrice);
			}
		}
		if (settleGoodses.size() != _goodsIds.size()) {
			return ResponseEntity.fail("商品信息改变，请刷新页面重新结算");
		}
		Map<String, Object> data = Maps.newHashMap();
		data.put("shippingAddress", shippingAddressService.getDefaultAddress(getLoginUser().getUid()));
		data.put("goodsList", settleGoodses);
		data.put("totalGoods", settleGoodses.size());
		data.put("totalPrice", totalPrice);
		// TODO 优惠计算、邮费

		return ResponseEntity.ok(data);
	}

}
