package com.micromall.service.vo;

import com.micromall.entity.ext.GoodsTypes;
import com.micromall.entity.ext.PromotionConfigKeys;

import java.math.BigDecimal;

/**
 * Created by zhangzx on 16/3/28.
 */
public class GoodsDetails {

	// 商品id
	private int        id;
	// 商品标题
	private String     title;
	// 商品图片数组(JSON)
	private String     images;
	// 商品价格
	private BigDecimal price;
	// 商品库存
	private int        inventory;
	/**
	 * 商品类型（普通商品、会员充值卡）{@link GoodsTypes}
	 */
	private int        type;
	// 是否促销商品
	private boolean    promotion;
	/**
	 * 促销配置(JSON) {@link PromotionConfigKeys}
	 */
	private String     promotionParams;
	// 商品描述
	private String     descr;
	// 产品参数(JSON)
	private String     productParams;
	// 商品销量
	private int        salesVolume;
	// 运费
	private int        freight;
	// 是否已经收藏
	private boolean    favorite;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getInventory() {
		return inventory;
	}

	public void setInventory(int inventory) {
		this.inventory = inventory;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getProductParams() {
		return productParams;
	}

	public void setProductParams(String productParams) {
		this.productParams = productParams;
	}

	public int getSalesVolume() {
		return salesVolume;
	}

	public void setSalesVolume(int salesVolume) {
		this.salesVolume = salesVolume;
	}

	public int getFreight() {
		return freight;
	}

	public void setFreight(int freight) {
		this.freight = freight;
	}

	public boolean isFavorite() {
		return favorite;
	}

	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}
}
