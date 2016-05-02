package com.micromall.repository.entity;

/**
 * Created by zhangzx on 16/3/23.
 * 商品类目
 */
public class Category extends IdEntity {

	// 类目名称
	private String  name;
	// 显示顺序
	private Integer sort;
	// 上级类目id
	private Integer parentId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
}
