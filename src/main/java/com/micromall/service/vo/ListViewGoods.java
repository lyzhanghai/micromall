package com.micromall.service.vo;

/**
 * Created by zhangzx on 16/3/28.
 */
public class ListViewGoods {

	// 商品ID
	private int     goodsId;
	// 商品标题
	private String  title;
	// 商品图片
	private String  picture;
	// 商品价格
	private float   price;
	// 商品销量
	private int     salesVolume;
	// 是否促销商品
	private boolean promotion;

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

	public int getSalesVolume() {
		return salesVolume;
	}

	public void setSalesVolume(int salesVolume) {
		this.salesVolume = salesVolume;
	}

	public boolean isPromotion() {
		return promotion;
	}

	public void setPromotion(boolean promotion) {
		this.promotion = promotion;
	}
}
