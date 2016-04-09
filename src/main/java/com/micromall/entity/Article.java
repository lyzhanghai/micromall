package com.micromall.entity;

import com.micromall.entity.ext.ArticleTypes;

import java.util.Date;

/**
 * Created by zhangzx on 16/3/23.
 * 文章
 */
public class Article extends IdEntity {

	/**
	 * 类型 {@link ArticleTypes}
	 */
	private Integer type;
	// 标题
	private String  title;
	// 内容
	private String  content;
	// 创建时间
	private Date    createTime;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
