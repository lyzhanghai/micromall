package com.micromall.service.vo;

/**
 * Created by zhangzx on 16/3/28.
 */
public class FavoriteGoods {

	// 商品ID
	private int     goodsId;
	// 商品标题
	private String  title;
	// 商品图片
	private String  picture;
	// 商品价格
	private float   price;
	// 是否已下架
	private boolean soldout;

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

	public boolean isSoldout() {
		return soldout;
	}

	public void setSoldout(boolean soldout) {
		this.soldout = soldout;
	}
}
