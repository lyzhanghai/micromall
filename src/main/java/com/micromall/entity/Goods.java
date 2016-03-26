package com.micromall.entity;

import java.util.Date;
import java.util.Map;

/**
 * Created by zhangzx on 16/3/21.
 */
public class Goods {
	// 商品标题
	private String              title;
	// 商品图片数组
	private String[]            pictures;
	// 所属类目
	private int                 categoryId;
	// 商品价格
	private float               price;
	// 商品库存
	private int                 inventory;
	// 商品状态（上架/不上架）
	private int                 status;
	// 是否促销商品
	private int                 promotion;
	// 促销配置
	private Map<String, String> promotionParams;
	// 商品描述
	private String              descr;
	// 产品参数
	private Map<String, String> productParams;
	// 商品排序
	private int                 sort;
	// 商品销量
	private int                 salesVolume;
	// 创建时间
	private Date                createTime;
	// 修改时间
	private Date                updateTime;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String[] getPictures() {
		return pictures;
	}

	public void setPictures(String[] pictures) {
		this.pictures = pictures;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getInventory() {
		return inventory;
	}

	public void setInventory(int inventory) {
		this.inventory = inventory;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getPromotion() {
		return promotion;
	}

	public void setPromotion(int promotion) {
		this.promotion = promotion;
	}

	public Map<String, String> getPromotionParams() {
		return promotionParams;
	}

	public void setPromotionParams(Map<String, String> promotionParams) {
		this.promotionParams = promotionParams;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public Map<String, String> getProductParams() {
		return productParams;
	}

	public void setProductParams(Map<String, String> productParams) {
		this.productParams = productParams;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public int getSalesVolume() {
		return salesVolume;
	}

	public void setSalesVolume(int salesVolume) {
		this.salesVolume = salesVolume;
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
