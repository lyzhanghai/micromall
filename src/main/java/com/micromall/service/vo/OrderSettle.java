/*
 * Copyright (C), 2014-2015, 杭州小卡科技有限公司
 * Created by ciwei@xiaokakeji.com on 2016/05/16.
 */
package com.micromall.service.vo;

import com.micromall.repository.entity.OrderGoods;
import com.micromall.repository.entity.ShippingAddress;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ciwei@xiaokakeji.com
 * @date 2016/05/16.
 */
public class OrderSettle {

	private String          settleId;
	// 订单总金额
	private BigDecimal      totalAmount;
	// 运费
	private int             freight;
	// 收货地址
	private ShippingAddress address;
	// 总重量
	private BigDecimal      totalWeight;
	// 订单商品信息
	private List<OrderGoods> goodsList = new ArrayList<>();
	// 优惠抵扣金额（优惠劵/商品优惠抵扣金额+实付金额=订单总金额）
	// private BigDecimal                deductionAmount;
	// 订单优惠信息(JSON)
	//	private List<Map<String, Object>> discounts;
	// 使用的优惠劵(JSON)
	// private List<Map<String, Object>> coupons;

	public String getSettleId() {
		return settleId;
	}

	public void setSettleId(String settleId) {
		this.settleId = settleId;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getFreight() {
		return freight;
	}

	public void setFreight(int freight) {
		this.freight = freight;
	}

	public ShippingAddress getAddress() {
		return address;
	}

	public void setAddress(ShippingAddress address) {
		this.address = address;
	}

	public BigDecimal getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(BigDecimal totalWeight) {
		this.totalWeight = totalWeight;
	}

	public List<OrderGoods> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<OrderGoods> goodsList) {
		this.goodsList = goodsList;
	}
}


