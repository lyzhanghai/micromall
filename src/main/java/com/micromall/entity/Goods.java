package com.micromall.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.micromall.entity.ext.GoodsTypes;
import com.micromall.entity.ext.ProductParamsKeys;
import com.micromall.entity.ext.PromotionConfigKeys;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangzx on 16/3/21.
 * 商品信息
 */
public class Goods extends IdEntity {

	// 商品标题
	private String              title;
	// 商品主图片（JSON数据，取图片数组第一张图片作为主图）
	@JsonIgnore
	private String              mainImage;
	// 商品图片数组(JSON)
	private List<String>        images;
	// 所属类目
	@JsonIgnore
	private Integer             categoryId;
	// 商品价格
	private BigDecimal          price;
	// 商品库存
	private Integer             inventory;
	// 是否上架（商品卖完后自动下架）
	private Boolean             shelves;
	/**
	 * 商品类型（普通商品、会员充值卡）{@link GoodsTypes}
	 */
	private Integer             type;
	// 是否促销商品
	private Boolean             promotion;
	/**
	 * 促销配置(JSON数据) {@link PromotionConfigKeys}
	 */
	private Map<String, Object> promotionParams;
	// TODO 运费模型
	//private boolean    freeFreight;// 是否免运费
	//private boolean    fixedFreight;// 是否固定运费
	// 运费
	private Integer             freight;
	// 商品描述
	private String              descr;
	/**
	 * 产品参数(JSON数据) {@link ProductParamsKeys}
	 */
	private Map<String, Object> productParams;
	// 商品排序
	@JsonIgnore
	private Integer             sort;
	// 商品销量
	private Integer             salesVolume;
	// 是否逻辑删除
	@JsonIgnore
	private Boolean             deleted;
	// 创建时间
	@JsonIgnore
	private Date                createTime;
	// 修改时间
	@JsonIgnore
	private Date                updateTime;

	// -------------前端展示字段------------
	// 是否已经收藏
	private boolean favorite;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMainImage() {
		return mainImage;
	}

	public void setMainImage(String mainImage) {
		this.mainImage = mainImage;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getInventory() {
		return inventory;
	}

	public void setInventory(Integer inventory) {
		this.inventory = inventory;
	}

	public Boolean isShelves() {
		return shelves;
	}

	public void setShelves(Boolean shelves) {
		this.shelves = shelves;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Boolean isPromotion() {
		return promotion;
	}

	public void setPromotion(Boolean promotion) {
		this.promotion = promotion;
	}

	public Map<String, Object> getPromotionParams() {
		return promotionParams;
	}

	public void setPromotionParams(Map<String, Object> promotionParams) {
		this.promotionParams = promotionParams;
	}

	public Integer getFreight() {
		return freight;
	}

	public void setFreight(Integer freight) {
		this.freight = freight;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public Map<String, Object> getProductParams() {
		return productParams;
	}

	public void setProductParams(Map<String, Object> productParams) {
		this.productParams = productParams;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getSalesVolume() {
		return salesVolume;
	}

	public void setSalesVolume(Integer salesVolume) {
		this.salesVolume = salesVolume;
	}

	public Boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean delete) {
		this.deleted = delete;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public boolean isFavorite() {
		return favorite;
	}

	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}
}
