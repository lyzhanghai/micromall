package com.micromall.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

/**
 * Created by zhangzx on 16/3/23.
 * 用户消息
 */
public class Message extends IdEntity {

	// 所属用户id
	@JsonIgnore
	private Integer uid;
	// 标题
	private String  title;
	// 内容
	private String  content;
	// 发送时间
	private Date    createTime;

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
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
