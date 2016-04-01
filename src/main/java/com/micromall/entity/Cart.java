package com.micromall.entity;

import java.util.Date;

/**
 * Created by zhangzx on 16/3/23.
 * 购物车
 */
public class Cart extends IdEntity {

	/*uid与goodsId组成唯一索引*/

	// 归属用户id
	private int  uid;
	// 商品id
	private int  goodsId;
	// 购买数量
	private int  buyNumber;
	// 创建时间
	private Date createTime;

	public int getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	public int getBuyNumber() {
		return buyNumber;
	}

	public void setBuyNumber(int buyNumber) {
		this.buyNumber = buyNumber;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
