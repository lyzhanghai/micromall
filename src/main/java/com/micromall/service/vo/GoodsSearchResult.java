package com.micromall.service.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.micromall.repository.entity.common.GoodsTypes;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by zhangzx on 16/3/28.
 */
public class GoodsSearchResult {

	// 商品ID
	private int        goodsId;
	// 商品标题
	private String     title;
	// 商品图片
	private String     image;
	// 商品价格
	private BigDecimal price;
	// 商品原价
	private BigDecimal originPrice;
	// 商品销量
	private int        salesVolume;

	// ------------------------------------------------------------------------------------------------------------------------------

	/**
	 * 商品类型（普通商品、会员充值卡）{@link GoodsTypes}
	 */
	@JsonIgnore
	@Deprecated
	private int                 type;
	// 是否促销商品
	@JsonIgnore
	@Deprecated
	private boolean             promotion;
	// 促销配置
	@JsonIgnore
	@Deprecated
	private Map<String, Object> promotionParams;

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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getOriginPrice() {
		return originPrice;
	}

	public void setOriginPrice(BigDecimal originPrice) {
		this.originPrice = originPrice;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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

	public Map<String, Object> getPromotionParams() {
		return promotionParams;
	}

	public void setPromotionParams(Map<String, Object> promotionParams) {
		this.promotionParams = promotionParams;
	}

}
