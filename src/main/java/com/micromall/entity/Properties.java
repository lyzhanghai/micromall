package com.micromall.entity;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangzx on 16/3/30.
 * 配置信息
 */
public class Properties {
	private String name;
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

	public <T> T toObject(Class<T> clazz) {
		if (StringUtils.isNotBlank(content)) {
			return JSON.parseObject(content, clazz);
		}
		return null;
	}

	public <T> List<T> toArray(Class<T> clazz) {
		if (StringUtils.isNotBlank(content)) {
			return JSON.parseArray(content, clazz);
		}
		return new ArrayList<T>();
	}
}
