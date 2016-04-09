package com.micromall.entity;

import java.util.Date;

/**
 * Created by zhangzx on 16/3/23.
 * 购物车商品
 */
public class CartGoods extends IdEntity {

	// 所属用户id
	private Integer uid;
	// 商品id
	private Integer goodsId;
	// 购买数量
	private Integer buyNumber;
	// 创建时间
	private Date    createTime;

	public CartGoods() {
	}

	public CartGoods(Integer uid, Integer goodsId, Integer buyNumber, Date createTime) {
		this.uid = uid;
		this.goodsId = goodsId;
		this.buyNumber = buyNumber;
		this.createTime = createTime;
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

}
