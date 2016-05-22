/*
 * Copyright (C), 2014-2015, 杭州小卡科技有限公司
 * Created by ciwei@xiaokakeji.com on 2016/05/15.
 */
package com.micromall.service.vo;

import com.micromall.repository.entity.OrderGoods;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author ciwei@xiaokakeji.com
 * @date 2016/05/15.
 */
public class CreateOrder {

	// 所属用户id
	private int                       uid;
	// 订单总金额
	private BigDecimal                totalAmount;
	// 优惠抵扣金额（优惠劵+实付金额=订单总金额）
	private BigDecimal                deductionAmount;
	// 运费
	private int                       freight;
	// 订单优惠信息(JSON)
	private List<Map<String, Object>> discounts;
	// 使用的优惠劵(JSON)
	private List<Map<String, Object>> coupons;
	// 买家留言
	private String                    leaveMessage;
	// 收货地址信息（省、市、区/县、详细地址）
	private String                    shippingAddress;
	// 收货人姓名
	private String                    consigneeName;
	// 收货人电话
	private String                    consigneePhone;
	// 邮政编码
	private String                    postcode;
	// 订单商品信息
	private List<OrderGoods>          goodsList;

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
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

	public List<Map<String, Object>> getCoupons() {
		return coupons;
	}

	public void setCoupons(List<Map<String, Object>> coupons) {
		this.coupons = coupons;
	}

	public String getLeaveMessage() {
		return leaveMessage;
	}

	public void setLeaveMessage(String leaveMessage) {
		this.leaveMessage = leaveMessage;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getConsigneePhone() {
		return consigneePhone;
	}

	public void setConsigneePhone(String consigneePhone) {
		this.consigneePhone = consigneePhone;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public List<OrderGoods> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<OrderGoods> goodsList) {
		this.goodsList = goodsList;
	}
}
