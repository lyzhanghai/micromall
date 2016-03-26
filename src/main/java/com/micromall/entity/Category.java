package com.micromall.entity;

/**
 * Created by zhangzx on 16/3/23.
 */
public class Category extends IdEntity {

	// 类目名称
	private String name;
	// 显示顺序
	private int    index;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
