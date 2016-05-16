package com.micromall.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

/**
 * 用户认证信息
 *
 * @author zhangzxiang91@gmail.com
 * @date 2016/04/09.
 */
public class CertifiedInfo {

	// 所属用户id
	@JsonIgnore
	private Integer uid;
	// 姓名
	private String  name;
	// 手机号
	private String  phone;
	// 身份证号码
	private String  idCarNo;
	// 身份证正面照片
	private String  idCarImage1;
	// 身份证背面照片
	private String  idCarImage0;
	// 审核状态
	private Integer status;
	// 审核失败原因
	private String  auditlog;
	// 审核时间
	@JsonIgnore
	private Date    auditTime;
	// 提交时间
	@JsonIgnore
	private Date    createTime;
	// 修改时间
	@JsonIgnore
	private Date    updateTime;

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIdCarNo() {
		return idCarNo;
	}

	public void setIdCarNo(String idCarNo) {
		this.idCarNo = idCarNo;
	}

	public String getIdCarImage1() {
		return idCarImage1;
	}

	public void setIdCarImage1(String idCarImage1) {
		this.idCarImage1 = idCarImage1;
	}

	public String getIdCarImage0() {
		return idCarImage0;
	}

	public void setIdCarImage0(String idCarImage0) {
		this.idCarImage0 = idCarImage0;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getAuditlog() {
		return auditlog;
	}

	public void setAuditlog(String auditlog) {
		this.auditlog = auditlog;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
