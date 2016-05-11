package com.micromall.web.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.micromall.repository.entity.CartGoods;
import com.micromall.repository.entity.Goods;
import com.micromall.service.CartService;
import com.micromall.service.GoodsService;
import com.micromall.service.ShippingAddressService;
import com.micromall.web.resp.ResponseEntity;
import com.micromall.web.security.Authentication;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author zhangzxiang91@gmail.com
 * @date 2016/04/11.
 */
@RestController
@RequestMapping(value = "/api")
@Authentication
public class BuyController extends BasisController {

	@Resource
	private CartService            cartService;
	@Resource
	private GoodsService           goodsService;
	@Resource
	private ShippingAddressService shippingAddressService;

	/**
	 * 结算
	 *
	 * @param goodsIds 商品id
	 * @param cart 结算来源（1：购物车，2：直接购买）
	 * @param buyNumber 购买数量，直接购买模式下生效
	 * @return
	 */
	@RequestMapping(value = "/cart/settle")
	public ResponseEntity<?> settle(String goodsIds, Integer buyNumber, boolean cart) {
		if (StringUtils.isEmpty(goodsIds)) {
			return ResponseEntity.fail(cart ? "请选择要结算的商品" : "请选择要购买的商品");
		}
		if (!cart && buyNumber == null || buyNumber.intValue() <= 0) {
			return ResponseEntity.fail("购买数量不能小于1件");
		}

		Set<String> _goodsIds = Sets.newHashSet(StringUtils.split(goodsIds, ","));

		List<Map<String, Object>> settleGoodses = Lists.newArrayList();
		BigDecimal totalPrice = new BigDecimal(0);

		if (cart) {
			// 购物车结算
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
		} else {
			// 直接购买
			Goods goods = goodsService.getGoodsInfo(Integer.parseInt(goodsIds));
			if (goods == null) {
				return ResponseEntity.fail("商品不存在或已下架");
			}
			if (buyNumber > goods.getInventory()) {
				return ResponseEntity.fail("购买数量不能大于商品库存量");
			}

			Map<String, Object> _goods = Maps.newHashMap();
			_goods.put("id", goods.getId());
			_goods.put("title", goods.getTitle());
			_goods.put("image", goods.getMainImage());
			_goods.put("price", goods.getPrice());
			_goods.put("buyNumber", buyNumber);
			BigDecimal _totalPrice = goods.getPrice().multiply(new BigDecimal(buyNumber)).setScale(2);
			_goods.put("totalPrice", _totalPrice);
			settleGoodses.add(_goods);
			totalPrice = _totalPrice;
		}

		Map<String, Object> data = Maps.newHashMap();
		data.put("shippingAddress", shippingAddressService.getDefaultAddress(getLoginUser().getUid()));
		data.put("goodsList", settleGoodses);
		data.put("totalGoods", settleGoodses.size());
		data.put("totalPrice", totalPrice);
		data.put("token", UUID.randomUUID().toString());
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
	public ResponseEntity<?> pay(String orderNo) {

		return ResponseEntity.ok();
	}

}
