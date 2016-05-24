/*
 * Copyright (C), 2014-2015, 杭州小卡科技有限公司
 * Created by ciwei@xiaokakeji.com on 2016/05/21.
 */
package com.micromall.service.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.micromall.repository.entity.OrderGoods;
import com.micromall.repository.entity.common.OrderStatus;
import com.micromall.repository.entity.common.OrderStatus.RefundStatus;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ciwei@xiaokakeji.com
 * @date 2016/05/21.
 */
public class OrderDetails {

	// 订单编号
	private String                    orderNo;
	// 订单总金额
	private BigDecimal                totalAmount;
	// 优惠抵扣金额（优惠劵/商品优惠抵扣金额+实付金额=订单总金额）
	private BigDecimal                deductionAmount;
	// 运费
	private Integer                   freight;
	// 订单优惠信息(JSON)
	@JsonIgnore
	@Deprecated
	private List<Map<String, Object>> discounts;
	// 使用的优惠劵(JSON)
	@JsonIgnore
	@Deprecated
	private List<Map<String, Object>> coupons;
	/**
	 * 订单当前状态 {@link OrderStatus}
	 */
	private Integer                   status;
	/**
	 * 退款状态 {@link RefundStatus}
	 */
	private Integer                   refundStatus;

	// ----------------------------- 收货信息

	// 收货地址信息（省、市、区/县、详细地址）
	private String shippingAddress;
	// 收货人姓名
	private String consigneeName;
	// 收货人电话
	private String consigneePhone;
	// 邮政编码
	private String postcode;

	// ----------------------------- 物流信息

	// 发货物流公司
	private String deliveryCompany;
	// 发货物流单号
	private String deliveryNumber;
	// 发货时间
	private Date   deliveryTime;

	// 订单支付时间
	private Date payTime;
	// 订单关闭时间
	@JsonIgnore
	private Date closeTime;
	// 订单创建时间
	private Date createTime;

	// -------------前端展示字段------------
	// 订单商品信息
	private List<OrderGoods> goodsList;
	// 能否申请退款
	private boolean          canApplyRefund;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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

	public Integer getFreight() {
		return freight;
	}

	public void setFreight(Integer freight) {
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(Integer refundStatus) {
		this.refundStatus = refundStatus;
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

	public String getDeliveryCompany() {
		return deliveryCompany;
	}

	public void setDeliveryCompany(String deliveryCompany) {
		this.deliveryCompany = deliveryCompany;
	}

	public String getDeliveryNumber() {
		return deliveryNumber;
	}

	public void setDeliveryNumber(String deliveryNumber) {
		this.deliveryNumber = deliveryNumber;
	}

	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Date getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public List<OrderGoods> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<OrderGoods> goodsList) {
		this.goodsList = goodsList;
	}

	public boolean isCanApplyRefund() {
		return canApplyRefund;
	}

	public void setCanApplyRefund(boolean canApplyRefund) {
		this.canApplyRefund = canApplyRefund;
	}
}
