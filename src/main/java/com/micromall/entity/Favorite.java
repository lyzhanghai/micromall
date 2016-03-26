package com.micromall.entity;

import java.util.Date;

/**
 * Created by zhangzx on 16/3/23.
 */
public class Favorite extends IdEntity {

	// 所属用户
	private int   uid;
	// 收藏的商品ID
	private int   goodsId;
	private Goods goods;
	// 创建时间
	private Date  createTime;

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

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
