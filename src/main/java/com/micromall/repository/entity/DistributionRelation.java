package com.micromall.repository.entity;

import java.util.Date;

/**
 * 分销关系
 *
 * @author zhangzxiang91@gmail.com
 * @date 2016/04/17.
 */
public class DistributionRelation {

	// 用户id
	private Integer uid;
	// 下级分销用户id
	private Integer lowerUid;
	// 分销级别
	private Integer level;
	// 创建时间
	private Date    createTime;

	public DistributionRelation() {
	}

	public DistributionRelation(Integer uid, Integer lowerUid, Integer level, Date createTime) {
		this.uid = uid;
		this.lowerUid = lowerUid;
		this.level = level;
		this.createTime = createTime;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getLowerUid() {
		return lowerUid;
	}

	public void setLowerUid(Integer lowerUid) {
		this.lowerUid = lowerUid;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
