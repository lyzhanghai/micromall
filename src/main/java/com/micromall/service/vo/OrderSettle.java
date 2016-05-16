/*
 * Copyright (C), 2014-2015, 杭州小卡科技有限公司
 * Created by ciwei@xiaokakeji.com on 2016/05/16.
 */
package com.micromall.service.vo;

import com.micromall.repository.entity.OrderGoods;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ciwei@xiaokakeji.com
 * @date 2016/05/16.
 */
public class OrderSettle {

	// 订单总金额
	private BigDecimal                totalAmount;
	// 实付金额
	private BigDecimal                realpayAmount;
	// 余额支付金额
	// private BigDecimal                balancepayAmount;
	// 优惠抵扣金额（优惠劵/商品优惠抵扣金额+实付金额=订单总金额）
	private BigDecimal                deductionAmount;
	// 运费
	private int                       freight;
	// 订单优惠信息(JSON)
	private List<Map<String, Object>> discounts;
	// 使用的优惠劵(JSON)
	// private List<Map<String, Object>> coupons;
	// 订单商品信息
	private List<OrderGoods> goodsList = new ArrayList<>();

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getRealpayAmount() {
		return realpayAmount;
	}

	public void setRealpayAmount(BigDecimal realpayAmount) {
		this.realpayAmount = realpayAmount;
	}

	public BigDecimal getDeductionAmount() {
		return deductionAmount;
	}

	public void setDeductionAmount(BigDecimal deductionAmount) {
		this.deductionAmount = deductionAmount;
	}

	public int getFreight() {
		return freight;
	}

	public void setFreight(int freight) {
		this.freight = freight;
	}

	public List<Map<String, Object>> getDiscounts() {
		return discounts;
	}

	public void setDiscounts(List<Map<String, Object>> discounts) {
		this.discounts = discounts;
	}

	public List<OrderGoods> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<OrderGoods> goodsList) {
		this.goodsList = goodsList;
	}
}

