package com.micromall.service.vo;

import com.micromall.entity.OrderGoods;
import com.micromall.entity.ext.OrderStatus;

import java.math.BigDecimal;
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
	// 订单商品信息
	private List<OrderGoods> goodsList;

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

	public List<OrderGoods> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<OrderGoods> goodsList) {
		this.goodsList = goodsList;
	}
}
