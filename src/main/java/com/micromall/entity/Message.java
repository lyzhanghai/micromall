package com.micromall.entity;

import java.util.Date;

/**
 * Created by zhangzx on 16/3/23.
 */
public class Message extends IdEntity {
	// 所属用户
	private int     uid;
	// 标题
	private String  title;
	// 内容
	private String  content;
	// 是已读
	private boolean read;
	// 发送时间
	private Date    createTime;

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
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

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
