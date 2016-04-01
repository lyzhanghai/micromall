package com.micromall.entity;

import java.util.Date;

/**
 * Created by zhangzx on 16/3/26.
 * 订单商品
 */
public class OrderGoods extends IdEntity {
	// 归属订单编号
	private String orderNo;
	// 所属用户
	private int    uid;
	// 购买数量
	private int    buyNumber;
	// 总价格
	private float  totalPrice;
	// 商品ID
	private int    goodsId;
	// 商品标题
	private String title;
	// 商品图片
	private String picture;
	// 商品价格
	private float  price;
	// 创建时间
	private Date   createTime;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getBuyNumber() {
		return buyNumber;
	}

	public void setBuyNumber(int buyNumber) {
		this.buyNumber = buyNumber;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
