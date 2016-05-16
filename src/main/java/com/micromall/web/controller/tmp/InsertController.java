/*
 * Copyright (C), 2014-2015, 杭州小卡科技有限公司
 * Created by ciwei@xiaokakeji.com on 2016/05/12.
 */
package com.micromall.web.controller.tmp;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.micromall.repository.*;
import com.micromall.repository.entity.*;
import com.micromall.repository.entity.Properties;
import com.micromall.repository.entity.common.GoodsTypes;
import com.micromall.repository.entity.common.PropKeys;
import com.micromall.service.*;
import com.micromall.utils.ChainMap;
import com.micromall.utils.Condition;
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

		Map<String, Object> map = Maps.newHashMap();
		List<Map<String, Object>> banner = Lists.newArrayList();
		banner.add(new ChainMap<String, Object>().append("image", "http://211.149.241.76/images/tmp/banner.png").append("link", "http://www.micromall.com/xxx/xxx.html"));
		banner.add(new ChainMap<String, Object>().append("image", "http://211.149.241.76/images/tmp/banner.png").append("link", "http://www.micromall.com/xxx/xxx.html"));
		banner.add(new ChainMap<String, Object>().append("image", "http://211.149.241.76/images/tmp/banner.png").append("link", "http://www.micromall.com/xxx/xxx.html"));
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

		System.out.println(JSON.toJSONString(map));
	}

	@RequestMapping(value = "/insert")
	public ResponseEntity<?> insert() {
		//		createMember();
				createGoods();
				createProperties();
		//		createMessage();
		//		createAddress();
		//		createCartFavoriteGoods();

		return ResponseEntity.Success();
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
		banner.add(new ChainMap<String, Object>().append("image", "http://211.149.241.76/images/tmp/banner.png").append("link", "http://www.micromall.com/xxx/xxx.html"));
		banner.add(new ChainMap<String, Object>().append("image", "http://211.149.241.76/images/tmp/banner.png").append("link", "http://www.micromall.com/xxx/xxx.html"));
		banner.add(new ChainMap<String, Object>().append("image", "http://211.149.241.76/images/tmp/banner.png").append("link", "http://www.micromall.com/xxx/xxx.html"));
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
		Member member = memberService.registerForPhone("13023657670", "IzEwMDAw");
	}

	private void createGoods() {
		Random random = new Random();
		int[] categorys = {101, 102, 103, 104, 105};
		int[] freights = {0, 5, 5, 10, 20};
		for (int i = 0; i < 300; i++) {
			Goods goods = new Goods();
			goods.setTitle("测试商品-" + i);
			goods.setMainImage("http://211.149.241.76/images/tmp/goods.jpg");
			goods.setImages(Arrays.asList("http://211.149.241.76/images/tmp/goods.jpg", "http://211.149.241.76/images/tmp/goods.jpg", "http://211.149.241.76/images/tmp/goods.jpg"));

			goods.setCategoryId(categorys[random.nextInt(categorys.length)]);
			goods.setPrice(new BigDecimal(random.nextInt(500) + "." + random.nextInt(9)));
			goods.setInventory(random.nextInt(1000));
			goods.setShelves(true);
			goods.setType(GoodsTypes.普通商品);
			goods.setPromotion(false);
			if (random.nextInt(10) < 3) {
				goods.setPromotion(true);
				Map<String, Object> params = Maps.newHashMap();
				params.put("type", "single:discount");// 促销类型
				params.put("discount", "0.85");// 折扣
				params.put("discountName", "85折");// 折扣名称
				// params.put("reduceAmoun", "18.5");// 折扣减免金额
				// params.put("presentPrice", "18.5");// 折扣减免金额
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
