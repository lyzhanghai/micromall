package com.micromall.service.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.micromall.repository.entity.OrderGoods;
import com.micromall.repository.entity.common.OrderStatus;
import com.micromall.repository.entity.common.OrderStatus.RefundStatus;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author zhangzxiang91@gmail.com
 * @date 2016/04/18.
 */
public class ListViewOrder {

	// 订单编号
	private String           orderNo;
	// 订单总金额
	private BigDecimal       totalAmount;
	/**
	 * 订单当前状态 {@link OrderStatus}
	 */
	private Integer          status;
	/**
	 * 退款状态 {@link RefundStatus}
	 */
	private Integer          refundStatus;
	// 能否申请退款
	private boolean          canApplyRefund;
	// 订单商品信息
	private List<OrderGoods> goodsList;
	// 订单确认收货时间（验证能否申请退款使用）
	@JsonIgnore
	private Date             confirmDeliveryTime;
	// 超时未支付自动关闭时间
	private Date             timeoutCloseTime;

	// ----------------------------- 物流信息

	// 发货物流公司
	private String deliveryCompany;
	// 发货物流公司代码
	private String deliveryCode;
	// 发货物流单号
	private String deliveryNumber;
	// 发货时间
	private Date   deliveryTime;

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

	public boolean isCanApplyRefund() {
		return canApplyRefund;
	}

	public void setCanApplyRefund(boolean canApplyRefund) {
		this.canApplyRefund = canApplyRefund;
	}

	public List<OrderGoods> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<OrderGoods> goodsList) {
		this.goodsList = goodsList;
	}

	public Date getConfirmDeliveryTime() {
		return confirmDeliveryTime;
	}

	public void setConfirmDeliveryTime(Date confirmDeliveryTime) {
		this.confirmDeliveryTime = confirmDeliveryTime;
	}

	public Date getTimeoutCloseTime() {
		return timeoutCloseTime;
	}

	public void setTimeoutCloseTime(Date timeoutCloseTime) {
		this.timeoutCloseTime = timeoutCloseTime;
	}

	public String getDeliveryCompany() {
		return deliveryCompany;
	}

	public void setDeliveryCompany(String deliveryCompany) {
		this.deliveryCompany = deliveryCompany;
	}

	public String getDeliveryCode() {
		return deliveryCode;
	}

	public void setDeliveryCode(String deliveryCode) {
		this.deliveryCode = deliveryCode;
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
}
