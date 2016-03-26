package com.micromall.entity;

import java.util.Date;

/**
 * Created by zhangzx on 16/3/23.
 */
public class Cart extends IdEntity {

	// 用户支付完后, 从购物车删除商品记录
	// 接口返回数据包含（商品ID, 商品标题, 商品图片, 商品价格, 商品数量）
	// 查询时如果遇到下架，或删除的商品，自动从购物车中删除

	// 商品ID
	private int   goodsId;
	private Goods goods;
	// 购买数量
	private int   buyNumber;
	// 所属用户
	private int   uid;
	// 创建时间
	private Date  createTime;
	// 修改时间
	private Date  updateTime;

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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
