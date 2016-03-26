package com.micromall.entity;

/**
 * Created by zhangzx on 16/3/23.
 */
public class Favorite {
	private Integer id;
	// 收藏的商品ID
	private int     goodsId;
	private Goods   goods;
	// 所属用户
	private int     uid;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}
}
