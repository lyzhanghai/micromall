package com.micromall.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhangzx on 16/3/23.
 * 用户商品收藏
 */
public class FavoriteGoods extends IdEntity {

	// 所属用户id
	@JsonIgnore
	private Integer    uid;
	// 收藏的商品ID
	private Integer    goodsId;
	// 商品标题
	private String     title;
	// 商品图片
	private String     image;
	// 商品价格
	private BigDecimal price;
	// 创建时间
	@JsonIgnore
	private Date       createTime;

	// -------------前端展示字段------------
	// 库存量
	private int     inventory;
	// 是否已经失效
	private boolean invalid;

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
