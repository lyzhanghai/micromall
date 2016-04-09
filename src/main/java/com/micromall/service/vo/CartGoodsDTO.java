package com.micromall.service.vo;

/**
 * Created by zhangzx on 16/3/23.
 */
public class CartGoodsDTO {

	// 商品ID
	private int    goodsId;
	// 商品标题
	private String title;
	// 商品图片
	private String picture;
	// 商品价格
	private float  price;
	// 购买数量
	private int    buyNumber;
	// 库存量
	private int    inventory;

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

	public int getBuyNumber() {
		return buyNumber;
	}

	public void setBuyNumber(int buyNumber) {
		this.buyNumber = buyNumber;
	}

	public int getInventory() {
		return inventory;
	}

	public void setInventory(int inventory) {
		this.inventory = inventory;
	}
}
