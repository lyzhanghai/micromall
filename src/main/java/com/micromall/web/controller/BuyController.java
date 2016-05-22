package com.micromall.web.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.micromall.repository.entity.*;
import com.micromall.repository.entity.common.OrderStatus;
import com.micromall.service.*;
import com.micromall.service.vo.CreateOrder;
import com.micromall.service.vo.OrderSettle;
import com.micromall.utils.HttpServletUtils;
import com.micromall.web.controller.BasisController;
import com.micromall.web.resp.ResponseEntity;
import com.micromall.web.security.annotation.Authentication;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

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
// @RestController
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

		OrderSettle orderSettle = new OrderSettle();
		BigDecimal totalAmount = new BigDecimal(0);
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
					orderGoods.setOriginPrice(carGoods.getPrice());
					orderGoods.setBuyNumber(carGoods.getBuyNumber());
					orderSettle.getGoodsList().add(orderGoods);
					totalAmount = totalAmount.add(orderGoods.getPrice().multiply(new BigDecimal(orderGoods.getBuyNumber())));
				}
			}
			if (orderSettle.getGoodsList().size() != _goodsIds.size()) {
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
			orderSettle.getGoodsList().add(orderGoods);
			totalAmount = orderGoods.getOriginPrice().multiply(new BigDecimal(orderGoods.getBuyNumber()));
		}

		// 计算运费
		int freight = calculateFreight(orderSettle);
		totalAmount = totalAmount.add(new BigDecimal(freight));
		orderSettle.setTotalAmount(totalAmount.setScale(2, BigDecimal.ROUND_DOWN));
		orderSettle.setFreight(freight);

		String settleId = UUID.randomUUID().toString().replaceAll("-", "");
		Map<String, Object> data = Maps.newHashMap();
		data.put("shippingAddress", shippingAddressService.getDefaultAddress(getLoginUser().getUid()));// 默认收货地址
		data.put("orderSettle", orderSettle);
		// data.put("coupons", new ArrayList<>());// 可选的优惠劵
		data.put("settleId", settleId);

		request.getSession().setAttribute("_SETTLE_ID:" + settleId, data);
		return ResponseEntity.Success(data);
	}

	// 邮费计算
	private int calculateFreight(OrderSettle orderSettle) {
		return 0;
	}

	/**
	 * 下单购买
	 *
	 * @param settleId 商品id
	 * @return
	 */
	@RequestMapping(value = "/buy")
	public ResponseEntity<?> buy(HttpServletRequest request, String settleId, String leaveMessage, int addressId) {
		OrderSettle orderSettle = (OrderSettle)request.getSession().getAttribute("_SETTLE_ID:" + settleId);
		if (orderSettle == null) {
			return ResponseEntity.Failure("当前访问页面已失效，请重新提交申请");
		}
		CreateOrder createOrder = new CreateOrder();
		createOrder.setUid(getLoginUser().getUid());
		createOrder.setTotalAmount(orderSettle.getTotalAmount());
		createOrder.setDeductionAmount(BigDecimal.ZERO);
		createOrder.setFreight(orderSettle.getFreight());
		createOrder.setDiscounts(Lists.newArrayList());
		createOrder.setCoupons(Lists.newArrayList());
		createOrder.setLeaveMessage(leaveMessage);
		// 收货地址
		ShippingAddress address = shippingAddressService.getAddress(getLoginUser().getUid(), addressId);
		createOrder.setShippingAddress(
				address.getProvince() + address.getCity() + StringUtils.trimToEmpty(address.getCounty()) + address.getDetailedAddress());
		createOrder.setConsigneeName(address.getConsigneeName());
		createOrder.setConsigneePhone(address.getConsigneePhone());
		createOrder.setPostcode(address.getPostcode());
		// 购买商品
		createOrder.setGoodsList(orderSettle.getGoodsList());

		// 创建订单
		Order order = orderService.createOrder(createOrder);
		return ResponseEntity.Success(orderService.getOrderDetails(order.getUid(), order.getOrderNo()));
	}

	/**
	 * 订单支付
	 *
	 * @param orderNo
	 * @return
	 */
	@RequestMapping(value = "/pay")
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
		return ResponseEntity.Success(wechatPaymentService.pay(order, member.getWechatId(), HttpServletUtils.getRequestIP(request)));
	}

}
