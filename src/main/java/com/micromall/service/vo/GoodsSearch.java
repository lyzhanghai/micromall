package com.micromall.service.vo;

/**
 * Created by zhangzx on 16/3/28.
 */
public class GoodsSearch {

	private String  query;//搜索关键字
	private Boolean promotion;// 是否促销
	private Integer categoryId;//类目id
	private String  sort;//排序方式
	private int     page;//分页页码
	private int     limit;//每页记录数

	private GoodsSearch(String sort, int page, int limit) {
		this.sort = sort;
		this.page = page;
		this.limit = limit;
	}

	public static GoodsSearch created(String sort, int page, int limit) {
		GoodsSearch search = new GoodsSearch(sort, page, limit);
		return search;
	}

	public Boolean getPromotion() {
		return promotion;
	}

	public GoodsSearch setPromotion(Boolean promotion) {
		this.promotion = promotion;
		return this;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public GoodsSearch setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
		return this;
	}

	public String getQuery() {
		return query;
	}

	public GoodsSearch setQuery(String query) {
		this.query = query;
		return this;
	}

	public String getSort() {
		return sort;
	}

	public int getPage() {
		return page;
	}

	public int getLimit() {
		return limit;
	}
}
