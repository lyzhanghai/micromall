package com.micromall.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhangzx on 16/3/23.
 * 购物车商品
 */
public class CartGoods extends IdEntity {

	// 所属用户id
	@JsonIgnore
	private Integer uid;
	// 商品id
	private Integer goodsId;
	// 购买数量
	private Integer buyNumber;
	// 创建时间
	@JsonIgnore
	private Date    createTime;

	// -------------前端展示字段------------
	// 商品标题
	private String     title;
	// 商品图片
	private String     image;
	// 商品价格
	private BigDecimal price;
	// 库存量
	private int        inventory;
	// 是否已经失效
	private boolean    invalid;

	public CartGoods() {
	}

	public CartGoods(Integer uid, Integer goodsId, Integer buyNumber, Date createTime) {
		this.uid = uid;
		this.goodsId = goodsId;
		this.buyNumber = buyNumber;
		this.createTime = createTime;
	}

	@JsonIgnore
	@Override
	public Integer getId() {
		return super.getId();
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public Integer getBuyNumber() {
		return buyNumber;
	}

	public void setBuyNumber(Integer buyNumber) {
		this.buyNumber = buyNumber;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public int getInventory() {
		return inventory;
	}

	public void setInventory(int inventory) {
		this.inventory = inventory;
	}

	public boolean isInvalid() {
		return invalid;
	}

	public void setInvalid(boolean invalid) {
		this.invalid = invalid;
	}
}
