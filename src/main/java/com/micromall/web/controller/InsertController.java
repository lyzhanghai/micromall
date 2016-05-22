/*
 * Copyright (C), 2014-2015, 杭州小卡科技有限公司
 * Created by ciwei@xiaokakeji.com on 2016/05/12.
 */
package com.micromall.web.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.micromall.repository.*;
import com.micromall.repository.entity.*;
import com.micromall.repository.entity.Properties;
import com.micromall.repository.entity.common.GoodsTypes;
import com.micromall.repository.entity.common.OrderStatus;
import com.micromall.repository.entity.common.OrderStatus.RefundStatus;
import com.micromall.repository.entity.common.PropKeys;
import com.micromall.repository.entity.common.WithdrawStatus;
import com.micromall.service.*;
import com.micromall.utils.ChainMap;
import com.micromall.utils.CommonEnvConstants;
import com.micromall.utils.Condition;
import com.micromall.utils.OrderNumberUtils;
import com.micromall.web.resp.ResponseEntity;
import com.micromall.web.security.annotation.Authentication;
import org.apache.ibatis.session.RowBounds;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author ciwei@xiaokakeji.com
 * @date 2016/05/12.
 */
@RestController

@Authentication(force = true)
public class InsertController {

	@Resource
	private MemberService             memberService;
	@Resource
	private CartService               cartService;
	@Resource
	private FavoriteService           favoriteService;
	@Resource
	private OrderService              orderService;
	@Resource
	private ShippingAddressService    shippingAddressService;
	@Resource
	private WithdrawService           withdrawService;
	@Resource
	private CertifiedInfoMapper       certifiedInfoMapper;
	@Resource
	private CommissionRecordMapper    commissionRecordMapper;
	@Resource
	private GoodsMapper               goodsMapper;
	@Resource
	private MessageMapper             messageMapper;
	@Resource
	private OrderGoodsMapper          orderGoodsMapper;
	@Resource
	private OrderMapper               orderMapper;
	@Resource
	private PropertiesMapper          propertiesMapper;
	@Resource
	private WithdrawApplyRecordMapper withdrawApplyRecordMapper;

	public static void main(String[] args) {

		for (int i = 0; i < 100; i++) {
			System.out.println(new Random().nextInt(6));
		}
	}

	@RequestMapping(value = "/insert")
	public ResponseEntity<?> insert() {
		//		createMember();
		//		createGoods();
		//		createProperties();
		//		createMessage();
		//		createAddress();
		//		createCartFavoriteGoods();
		//		createOrders();
		createWithdraw();

		return ResponseEntity.Success();
	}

	private void createWithdraw() {

		Random random = new Random();
		for (int i = 0; i < 20; i++) {
			WithdrawApplyRecord record = new WithdrawApplyRecord();
			record.setUid(10000);
			record.setAmount(new BigDecimal(random.nextInt(5000)));
			record.setChannel("WECHAT");
			record.setApplyTime(new Date());
			switch (random.nextInt(3)) {
				case WithdrawStatus.待审核:
					record.setStatus(WithdrawStatus.待审核);
					break;
				case WithdrawStatus.审核通过:
					record.setStatus(WithdrawStatus.审核通过);
					record.setAuditlog("通过审核");
					record.setAuditTime(new Date());
					record.setCompleteTime(new Date());
					break;
				case WithdrawStatus.审核不通过:
					record.setStatus(WithdrawStatus.审核不通过);
					record.setAuditlog("未通过身份认证");
					record.setAuditTime(new Date());
					break;
			}
		}

	}

	private void createOrders() {
		Random random = new Random();
		for (int i = 0; i < 100; i++) {
			Order order = new Order();
			order.setUid(10000);
			order.setTotalAmount(new BigDecimal(random.nextInt(500) + "." + random.nextInt(9)));
			order.setDeductionAmount(BigDecimal.ZERO);
			order.setFreight(0);
			order.setDiscounts(null);
			order.setCoupons(null);
			order.setLeaveMessage("这是买家留言");
			order.setShippingAddress("浙江省杭州市西湖区学院路50号");
			order.setConsigneeName("张三");
			order.setConsigneePhone("13023657670");
			order.setPostcode("321000");
			// 系统
			order.setOrderNo(OrderNumberUtils.generateNumber());
			order.setStatus(OrderStatus.待支付);
			order.setRefundStatus(RefundStatus.初始状态);
			order.setCreateTime(new Date());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(order.getCreateTime());
			calendar.add(Calendar.MINUTE, CommonEnvConstants.ORDER_NOTPAY_TIMEOUT_CLOSE_TIME);
			order.setTimeoutCloseTime(calendar.getTime());

			switch (random.nextInt(6)) {
				case OrderStatus.待支付:
					break;
				case OrderStatus.待发货:
					order.setStatus(OrderStatus.待发货);
					order.setPayTime(new Date());

					if (random.nextBoolean()) {
						if (random.nextBoolean()) {
							// 申请退款
							order.setRefundStatus(RefundStatus.申请退款);
							order.setApplyRefundTime(new Date());
							order.setRefundReason("不想买了....");
						} else {
							// 拒绝退款
							order.setRefundStatus(RefundStatus.拒绝退款);
						}
					}
					break;
				case OrderStatus.待收货:
					order.setStatus(OrderStatus.待收货);
					order.setDeliveryCompany("顺丰快递");
					order.setDeliveryCode("SF");
					order.setDeliveryNumber("100093439400023232");
					order.setDeliveryTime(new Date());
					if (random.nextBoolean()) {
						if (random.nextBoolean()) {
							// 申请退款
							order.setRefundStatus(RefundStatus.申请退款);
							order.setApplyRefundTime(new Date());
							order.setRefundReason("不想买了....");
						} else {
							// 拒绝退款
							order.setRefundStatus(RefundStatus.拒绝退款);
						}
					}
					break;
				case OrderStatus.已收货:
					order.setDeliveryCompany("顺丰快递");
					order.setDeliveryCode("SF");
					order.setDeliveryNumber("100093439400023232");
					order.setDeliveryTime(new Date());
					order.setStatus(OrderStatus.已收货);
					order.setConfirmDeliveryTime(new Date());
					if (random.nextBoolean()) {
						if (random.nextBoolean()) {
							// 申请退款
							order.setRefundStatus(RefundStatus.申请退款);
							order.setApplyRefundTime(new Date());
							order.setRefundReason("不想买了....");
						} else {
							// 拒绝退款
							order.setRefundStatus(RefundStatus.拒绝退款);
						}
					}
					break;
				case OrderStatus.已退款:
					if (random.nextBoolean()) {
						order.setDeliveryCompany("顺丰快递");
						order.setDeliveryCode("SF");
						order.setDeliveryNumber("100093439400023232");
						order.setDeliveryTime(new Date());
					}
					order.setStatus(OrderStatus.已退款);
					order.setRefundStatus(RefundStatus.同意退款);
					order.setRefundCompleteTime(new Date());
					break;
				case OrderStatus.已关闭:
					order.setStatus(OrderStatus.已关闭);
					order.setCloseTime(new Date());
					order.setCloselog(random.nextBoolean() ? "超时系统自动关闭订单" : "用户主动关闭订单");
					break;
			}
			orderMapper.insert(order);
			List<Goods> goodses = goodsMapper.selectPageByWhereClause(new Condition(), new RowBounds(0, random.nextInt(11) + 4));
			for (Goods goods : goodses) {
				OrderGoods orderGoods = new OrderGoods();
				orderGoods.setOrderNo(order.getOrderNo());
				orderGoods.setGoodsId(goods.getId());
				orderGoods.setTitle(goods.getTitle());
				orderGoods.setImage(goods.getMainImage());
				orderGoods.setPrice(goods.getPrice());
				orderGoods.setOriginPrice(goods.getPrice());
				orderGoods.setBuyNumber(random.nextInt(10) + 1);
				orderGoods.setCreateTime(new Date());
				orderGoodsMapper.insert(orderGoods);
			}
		}
	}

	private void createCartFavoriteGoods() {
		List<Goods> goodses = goodsMapper.selectPageByWhereClause(new Condition(), new RowBounds(1, 30));
		for (Goods goods : goodses) {
			cartService.updateCartGoods(10000, goods.getId(), new Random().nextInt(10) + 1);
			favoriteService.favoriteGoods(10000, goods.getId());
		}
	}

	private void createAddress() {
		for (int i = 0; i < 5; i++) {
			ShippingAddress address = new ShippingAddress();
			address.setUid(10000);
			address.setProvince("浙江省");
			address.setCity("杭州市");
			address.setCounty("西湖区");
			address.setDetailedAddress("蒋村花园广安苑11幢3单元60" + i);
			address.setConsigneeName("张三");
			address.setConsigneePhone("13023657670");
			address.setDefaul(true);
			address.setCreateTime(new Date());
			shippingAddressService.addAddress(address);
		}
	}

	private void createMessage() {
		for (int i = 0; i < 100; i++) {
			Message message = new Message();
			message.setUid(10000);
			message.setTitle("消息通知-" + i);
			message.setContent("消息通知内容.......");
			message.setCreateTime(new Date());
			messageMapper.insert(message);
		}
	}

	private void createProperties() {
		Map<String, Object> map = Maps.newHashMap();
		List<Map<String, Object>> banner = Lists.newArrayList();
		banner.add(new ChainMap<String, Object>().append("image", "http://211.149.241.76/images/tmp/banner.png")
		                                         .append("link", "http://www.micromall.com/xxx/xxx.html"));
		banner.add(new ChainMap<String, Object>().append("image", "http://211.149.241.76/images/tmp/banner.png")
		                                         .append("link", "http://www.micromall.com/xxx/xxx.html"));
		banner.add(new ChainMap<String, Object>().append("image", "http://211.149.241.76/images/tmp/banner.png")
		                                         .append("link", "http://www.micromall.com/xxx/xxx.html"));
		map.put("banner", banner);
		List<Map<String, Object>> middle = Lists.newArrayList();
		middle.add(new ChainMap<String, Object>().append("index", 1).append("image", "http://211.149.241.76/images/tmp/middle0.png")
		                                         .append("link", "http://www.micromall.com/xxx/xxx.html"));
		middle.add(new ChainMap<String, Object>().append("index", 2).append("image", "http://211.149.241.76/images/tmp/middle1.png")
		                                         .append("link", "http://www.micromall.com/xxx/xxx.html"));
		middle.add(new ChainMap<String, Object>().append("index", 3).append("image", "http://211.149.241.76/images/tmp/middle2.png")
		                                         .append("link", "http://www.micromall.com/xxx/xxx.html"));
		middle.add(new ChainMap<String, Object>().append("index", 4).append("image", "http://211.149.241.76/images/tmp/middle3.png")
		                                         .append("link", "http://www.micromall.com/xxx/xxx.html"));
		map.put("middle", middle);

		Properties properties = new Properties();
		properties.setName(PropKeys.INDEX_AD_CONFIG);
		properties.setContent(JSON.toJSONString(map));
		propertiesMapper.insert(properties);
	}

	private void createMember() {
		// Member member = memberService.registerForWechatId("_$SYSTEM_AUTH_TEST_WECHAT_USER");
		//Member member = memberService.registerForPhone("13023657670", "IzEwMDAw");

		// 一级分销商
		for (int i = 0; i < 10; i++) {
			Member member = memberService.registerForPhone("1300000000" + i, "IzEwMDAw");
			for (int j = 0; j < 8; j++) {
				Member member1 = memberService.registerForPhone("13" + (i + 1) + "000000" + j, member.getMyPromoteCode());
			}
		}
	}

	private void createGoods() {
		Random random = new Random();
		int[] categorys = {101, 102, 103, 104, 105};
		int[] freights = {0, 5, 5, 10, 20};
		for (int i = 0; i < 300; i++) {
			Goods goods = new Goods();
			goods.setTitle("测试商品-" + i);
			goods.setMainImage("http://211.149.241.76/images/tmp/goods.jpg");
			goods.setImages(Arrays.asList("http://211.149.241.76/images/tmp/goods.jpg", "http://211.149.241.76/images/tmp/goods.jpg",
					"http://211.149.241.76/images/tmp/goods.jpg"));

			goods.setCategoryId(categorys[random.nextInt(categorys.length)]);
			goods.setPrice(new BigDecimal(random.nextInt(500) + "." + random.nextInt(9)));
			goods.setOriginPrice(goods.getPrice());
			goods.setInventory(random.nextInt(1000));
			goods.setShelves(true);
			goods.setType(GoodsTypes.普通商品);
			goods.setPromotion(false);
			if (random.nextInt(10) < 3) {
				goods.setPromotion(true);
				goods.setPrice(goods.getOriginPrice().multiply(new BigDecimal(0.85)).setScale(2, BigDecimal.ROUND_DOWN));
				Map<String, Object> params = Maps.newHashMap();
				params.put("type", "single:discount");// 促销类型
				params.put("discount", "0.85");// 折扣
				params.put("discountName", "85折");// 折扣名称
				params.put("imgurl", "http://211.149.241.76/images/tmp/goods_" + (random.nextInt(2) + 1) + "_promotion.png");
				goods.setPromotionParams(params);
			}
			goods.setFreight(freights[random.nextInt(freights.length)]);
			goods.setDescr("商品描述.");
			/*Map<String, Object> productParams = Maps.newHashMap();
			productParams.put(ProductParamsKeys.重量, "10KG");
			productParams.put(ProductParamsKeys.产地, "北京");
			productParams.put(ProductParamsKeys.生产日期, "2016-03-18");
			goods.setProductParams(productParams);*/
			goods.setSort(0);
			goods.setSalesVolume(random.nextInt(5000));
			goods.setDeleted(false);
			goods.setCreateTime(new Date());

			goodsMapper.insert(goods);
		}
	}

}
