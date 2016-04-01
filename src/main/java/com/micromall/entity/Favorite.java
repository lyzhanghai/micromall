package com.micromall.entity;

import java.util.Date;

/**
 * Created by zhangzx on 16/3/23.
 * 用户商品收藏
 */
public class Favorite extends IdEntity {

	/*uid与goodsId组成唯一索引*/

	// 所属用户
	private int    uid;
	// 收藏的商品ID
	private int    goodsId;
	// 商品标题
	private String title;
	// 商品图片
	private String picture;
	// 商品价格
	private float  price;
	// 创建时间
	private Date   createTime;

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
