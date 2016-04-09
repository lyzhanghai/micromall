package com.micromall.service.vo;

/**
 * Created by zhangzx on 16/3/28.
 */
public class GoodsSearch {

	private Boolean promotion;// 是否促销
	private Integer categoryId;//类目id
	private String  sort;//排序方式
	private int     p;//分页页码
	private int     limit;//每页记录数

	private GoodsSearch(String sort, int p, int limit) {
		this.sort = sort;
		this.p = p;
		this.limit = limit;
	}

	public static GoodsSearch created(String sort, int p, int limit) {
		GoodsSearch search = new GoodsSearch(sort, p, limit);
		return search;
	}

	public Boolean getPromotion() {
		return promotion;
	}

	public void setPromotion(Boolean promotion) {
		this.promotion = promotion;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getSort() {
		return sort;
	}

	public int getP() {
		return p;
	}

	public int getLimit() {
		return limit;
	}
}
