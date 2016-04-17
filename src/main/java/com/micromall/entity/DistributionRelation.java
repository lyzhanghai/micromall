/*
 * Copyright (C), 2014-2015, 杭州小卡科技有限公司
 * Created by ciwei@xiaokakeji.com on 2016/04/17.
 */
package com.micromall.entity;

import java.util.Date;

/**
 * 分销关系
 *
 * @author zhangzxiang91@gmail.com
 * @date 2016/04/17.
 */
public class DistributionRelation {

	private int  uid;
	private int  lowerUid;
	private int  level;
	private Date createTime;

	public DistributionRelation() {
	}

	public DistributionRelation(int uid, int lowerUid, int level, Date createTime) {
		this.uid = uid;
		this.lowerUid = lowerUid;
		this.level = level;
		this.createTime = createTime;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getLowerUid() {
		return lowerUid;
	}

	public void setLowerUid(int lowerUid) {
		this.lowerUid = lowerUid;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
