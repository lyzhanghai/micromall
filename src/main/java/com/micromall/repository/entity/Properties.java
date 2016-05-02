package com.micromall.repository.entity;

import com.micromall.repository.entity.common.PropKeys;

/**
 * Created by zhangzx on 16/3/30.
 * 配置信息
 */
public class Properties {

	/**
	 * 配置Key {@link PropKeys}
	 */
	private String name;
	// 配置Value
	private String content;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
