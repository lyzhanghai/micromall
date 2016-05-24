package com.micromall.web.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.micromall.repository.entity.*;
import com.micromall.repository.entity.common.OrderStatus;
import com.micromall.repository.entity.common.ProductParamsKeys;
import com.micromall.service.*;
import com.micromall.service.vo.CreateOrder;
import com.micromall.service.vo.OrderSettle;
import com.micromall.utils.HttpServletUtils;
import com.micromall.web.resp.ResponseEntity;
import com.micromall.web.security.annotation.Authentication;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
	@Resource
	private OrderService           orderService;
	@Resource
	private WechatPaymentService   wechatPaymentService;
	@Resource
	private MemberService          memberService;

	/**
	 * 结算
	 *
	 * @param goodsIds 商品id
	 * @param cart 结算来源（1：购物车，2：直接购买）
	 * @param buyNumber 购买数量，直接购买模式下生效
	 * @return
	 */
	@RequestMapping(value = "/cart/settle")
	public ResponseEntity<?> settle(HttpServletRequest request, String goodsIds, Integer buyNumber, boolean cart) {
		if (StringUtils.isEmpty(goodsIds)) {
			return ResponseEntity.Failure(cart ? "请选择要购买的商品" : "请选择要购买的商品");
		}
		if (!cart && buyNumber == null || buyNumber.intValue() <= 0) {
			return ResponseEntity.Failure("购买数量不能小于1件");
		}

		OrderSettle settle = new OrderSettle();
		BigDecimal totalAmount = new BigDecimal(0);
		BigDecimal totalWeight = new BigDecimal(0);
		if (cart) {
			// 购物车结算
			Set<String> _goodsIds = Sets.newHashSet(StringUtils.split(goodsIds, ","));
			List<CartGoods> goodsList = cartService.listGoods(getLoginUser().getUid());
			for (CartGoods carGoods : goodsList) {
				if (_goodsIds.contains(String.valueOf(carGoods.getGoodsId()))) {
					if (carGoods.isInvalid()) {
						return ResponseEntity.Failure("商品[" + carGoods.getTitle() + "]已下架");
					}
					if (carGoods.getBuyNumber() > carGoods.getInventory()) {
						return ResponseEntity.Failure("商品[" + carGoods.getTitle() + "]库存不足");
					}
					OrderGoods orderGoods = new OrderGoods();
					orderGoods.setGoodsId(carGoods.getGoodsId());
					orderGoods.setTitle(carGoods.getTitle());
					orderGoods.setImage(carGoods.getImage());
					orderGoods.setPrice(carGoods.getPrice());
					orderGoods.setOriginPrice(carGoods.getOriginPrice());
					orderGoods.setBuyNumber(carGoods.getBuyNumber());
					settle.getGoodsList().add(orderGoods);
					totalAmount = totalAmount.add(orderGoods.getPrice().multiply(new BigDecimal(orderGoods.getBuyNumber())));
					if (MapUtils.isNotEmpty(carGoods.getProductParams()) && carGoods.getProductParams().containsKey(ProductParamsKeys.WEIGHT)) {
						totalWeight = totalWeight.add(new BigDecimal(carGoods.getProductParams().get(ProductParamsKeys.WEIGHT).toString()));
					}

				}
			}
			if (settle.getGoodsList().size() != _goodsIds.size()) {
				return ResponseEntity.Failure("商品信息改变，请刷新页面重新结算");
			}
		} else {
			// 直接购买
			Goods goods = goodsService.getGoodsInfo(Integer.parseInt(goodsIds));
			if (goods == null) {
				return ResponseEntity.Failure("商品[" + goods.getTitle() + "]不存在");
			}
			if (goods.isDeleted() || !goods.isShelves() || goods.getInventory() <= 0) {
				return ResponseEntity.Failure("商品[" + goods.getTitle() + "]已下架");
			}
			if (buyNumber > goods.getInventory()) {
				return ResponseEntity.Failure("商品[" + goods.getTitle() + "]库存不足");
			}
			OrderGoods orderGoods = new OrderGoods();
			orderGoods.setGoodsId(goods.getId());
			orderGoods.setTitle(goods.getTitle());
			orderGoods.setImage(goods.getMainImage());
			orderGoods.setPrice(goods.getPrice());
			orderGoods.setOriginPrice(goods.getOriginPrice());
			orderGoods.setBuyNumber(buyNumber);
			settle.getGoodsList().add(orderGoods);
			totalAmount = orderGoods.getOriginPrice().multiply(new BigDecimal(orderGoods.getBuyNumber()));
			if (MapUtils.isNotEmpty(goods.getProductParams()) && goods.getProductParams().containsKey(ProductParamsKeys.WEIGHT)) {
				totalWeight = new BigDecimal(goods.getProductParams().get(ProductParamsKeys.WEIGHT).toString());
			}
		}

		settle.setSettleId(UUID.randomUUID().toString().replaceAll("-", ""));
		settle.setTotalAmount(totalAmount.setScale(2, BigDecimal.ROUND_DOWN));
		settle.setTotalWeight(totalWeight.setScale(2, BigDecimal.ROUND_DOWN));
		settle.setFreight(0);
		Map<String, Object> data = Maps.newHashMap();
		data.put("settle", settle);
		data.put("address", shippingAddressService.getDefaultAddress(getLoginUser().getUid()));
		request.getSession().setAttribute("_SETTLE_ID:" + settle.getSettleId(), settle);
		return ResponseEntity.Success(data);
	}

	// 邮费计算
	@RequestMapping(value = "/cart/settle/calculateFreight")
	public ResponseEntity<?> calculateFreight(HttpServletRequest request, String settleId, int addressId) {
		OrderSettle settle = (OrderSettle)request.getSession().getAttribute("_SETTLE_ID:" + settleId);
		if (settle == null) {
			return ResponseEntity.Failure("当前访问页面已失效，请重新提交申请");
		}
		ShippingAddress address = shippingAddressService.getAddress(getLoginUser().getUid(), addressId);
		if (address == null) {
			return ResponseEntity.Failure("收货地址不存在");
		}
		settle.setAddress(address);
		settle.setFreight(999);// TODO 运费计算
		settle.setTotalAmount(settle.getTotalAmount().add(new BigDecimal(settle.getFreight())));
		return ResponseEntity.Success(settle);
	}

	/**
	 * 下单购买
	 *
	 * @param settleId 商品id
	 * @return
	 */
	@RequestMapping(value = "/cart/settle/buy")
	public ResponseEntity<?> buy(HttpServletRequest request, String settleId, String leaveMessage) {
		OrderSettle settle = (OrderSettle)request.getSession().getAttribute("_SETTLE_ID:" + settleId);
		if (settle == null) {
			return ResponseEntity.Failure("当前访问页面已失效，请重新提交申请");
		}
		if (settle.getAddress() == null) {
			return ResponseEntity.Failure("请选择收货地址");
		}

		// TODO 商品校验

		CreateOrder createOrder = new CreateOrder();
		createOrder.setUid(getLoginUser().getUid());
		createOrder.setTotalAmount(settle.getTotalAmount());
		createOrder.setDeductionAmount(BigDecimal.ZERO);
		createOrder.setFreight(settle.getFreight());
		createOrder.setDiscounts(Lists.newArrayList());
		createOrder.setCoupons(Lists.newArrayList());
		createOrder.setLeaveMessage(leaveMessage);
		// 收货地址
		ShippingAddress address = settle.getAddress();
		createOrder.setShippingAddress(
				address.getProvince() + address.getCity() + StringUtils.trimToEmpty(address.getCounty()) + address.getDetailedAddress());
		createOrder.setConsigneeName(address.getConsigneeName());
		createOrder.setConsigneePhone(address.getConsigneePhone());
		createOrder.setPostcode(address.getPostcode());
		// 购买商品
		createOrder.setGoodsList(settle.getGoodsList());

		// 创建订单
		Order order = orderService.createOrder(createOrder);
		request.getSession().removeAttribute("_SETTLE_ID:" + settleId);
		Map<String, Object> data = Maps.newHashMap();
		data.put("orderNo", order.getOrderNo());
		data.put("amount", order.getTotalAmount());
		return ResponseEntity.Success(data);
	}

	/**
	 * 订单支付
	 *
	 * @param orderNo
	 * @return
	 */
	@RequestMapping(value = "/order/pay")
	public ResponseEntity<?> pay(HttpServletRequest request, String orderNo) {
		Order order = orderService.getOrder(getLoginUser().getUid(), orderNo);
		if (order == null) {
			return ResponseEntity.Failure("订单不存在");
		}
		if (order.getStatus() == OrderStatus.已关闭) {
			return ResponseEntity.Failure("订单已关闭");
		} else if (order.getStatus() != OrderStatus.待支付) {
			return ResponseEntity.Failure("订单已支付");
		}
		Member member = memberService.get(getLoginUser().getUid());
		if (StringUtils.isEmpty(member.getWechatId())) {
			return ResponseEntity.Failure("无法使用微信支付");
		}

		// TODO 商品校验

		return ResponseEntity.Success(wechatPaymentService.pay(order, member.getWechatId(), HttpServletUtils.getRequestIP(request)));
	}

}
