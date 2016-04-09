package com.micromall.entity;

import com.micromall.entity.ext.GoodsTypes;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhangzx on 16/3/21.
 * 商品信息
 */
public class Goods extends IdEntity {

	// 商品标题
	private String     title;
	// 商品主图片（取图片数组第一张图片作为主图）
	private String     mainImage;
	// 商品图片数组(JSON)
	private String     images;
	// 所属类目
	private Integer    categoryId;
	// 商品价格
	private BigDecimal price;
	// 商品库存
	private Integer    inventory;
	// 是否上架
	private Boolean    shelves;
	/**
	 * 商品类型（普通商品、会员充值卡）{@link GoodsTypes}
	 */
	private Integer    type;
	// 是否促销商品
	private Boolean    promotion;
	/**
	 * 促销配置(JSON) {@link com.micromall.entity.ext.PromotionConfigKeys}
	 */
	private String     promotionParams;
	// 商品描述
	private String     descr;
	// 产品参数(JSON)
	private String     productParams;
	// 商品排序
	private Integer    sort;
	// 商品销量
	private Integer    salesVolume;
	// 是否逻辑删除
	private Boolean    delete;
	// 创建时间
	private Date       createTime;
	// 修改时间
	private Date       updateTime;

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

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
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

	public Boolean isDelete() {
		return delete;
	}

	public void setDelete(Boolean delete) {
		this.delete = delete;
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
}
