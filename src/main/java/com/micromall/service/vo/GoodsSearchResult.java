package com.micromall.service.vo;

/**
 * Created by zhangzx on 16/3/28.
 */
public class GoodsSearchResult {

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
	// 促销配置
	private String  promotionParams;
	// 是否免运费
	private boolean freeFreight;
	// 是否固定运费
	private boolean fixedFreight;
	// 运费
	private int     freight;

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

	public String getPromotionParams() {
		return promotionParams;
	}

	public void setPromotionParams(String promotionParams) {
		this.promotionParams = promotionParams;
	}

	public boolean isFreeFreight() {
		return freeFreight;
	}

	public void setFreeFreight(boolean freeFreight) {
		this.freeFreight = freeFreight;
	}

	public boolean isFixedFreight() {
		return fixedFreight;
	}

	public void setFixedFreight(boolean fixedFreight) {
		this.fixedFreight = fixedFreight;
	}

	public int getFreight() {
		return freight;
	}

	public void setFreight(int freight) {
		this.freight = freight;
	}
}
