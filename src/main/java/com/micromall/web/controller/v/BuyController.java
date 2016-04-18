package com.micromall.web.controller.v;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.micromall.entity.CartGoods;
import com.micromall.service.CartService;
import com.micromall.service.ShippingAddressService;
import com.micromall.web.controller.BasisController;
import com.micromall.web.resp.ResponseEntity;
import com.micromall.web.security.Authentication;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zhangzxiang91@gmail.com
 * @date 2016/04/11.
 */
@Controller
@Authentication
public class BuyController extends BasisController {

	@Resource
	private CartService            cartService;
	@Resource
	private ShippingAddressService shippingAddressService;

	/**
	 * 结算
	 *
	 * @param goodsIds 商品id
	 * @param cart 结算来源（1：购物车，2：直接购买）
	 * @return
	 */
	@RequestMapping(value = "/cart/settle")
	@ResponseBody
	public ResponseEntity<?> settle(String goodsIds, Integer cart) {
		if (StringUtils.isBlank(goodsIds)) {
			return ResponseEntity.fail("请选择要结算的商品");
		}

		List<Map<String, Object>> settleGoodses = Lists.newArrayList();
		BigDecimal totalPrice = new BigDecimal(0);

		Set<String> _goodsIds = Sets.newHashSet(StringUtils.split(goodsIds, ","));
		List<CartGoods> goodsList = cartService.listGoods(getLoginUser().getUid());

		for (CartGoods goods : goodsList) {
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

		return ResponseEntity.ok();
	}

	/**
	 * 下单购买
	 *
	 * @param settleId 商品id
	 * @return
	 */
	@RequestMapping(value = "/buy")
	@ResponseBody
	public ResponseEntity<?> buy(String settleId) {

		return ResponseEntity.ok();
	}

	/**
	 * 订单支付
	 *
	 * @param orderNo
	 * @return
	 */
	@RequestMapping(value = "/pay")
	@ResponseBody
	public ResponseEntity<?> pay(String orderNo) {

		return ResponseEntity.ok();
	}

}
