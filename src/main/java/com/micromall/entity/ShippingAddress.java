package com.micromall.entity;

import java.util.Date;

/**
 * Created by zhangzx on 16/3/26.
 * 用户收货地址
 */
public class ShippingAddress extends IdEntity {

	// 所属用户id
	private Integer uid;
	// 省
	private String  province;
	// 市
	private String  city;
	// 区/县
	private String  county;
	// 详细地址
	private String  detailedAddress;
	// 收货人姓名
	private String  consigneeName;
	// 收货人电话
	private String  consigneePhone;
	// 邮政编码
	private String  postcode;
	// 是否默认地址
	private Boolean defaul;
	// 创建时间
	private Date    createTime;
	// 修改时间
	private Date    updateTime;

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getDetailedAddress() {
		return detailedAddress;
	}

	public void setDetailedAddress(String detailedAddress) {
		this.detailedAddress = detailedAddress;
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getConsigneePhone() {
		return consigneePhone;
	}

	public void setConsigneePhone(String consigneePhone) {
		this.consigneePhone = consigneePhone;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public Boolean isDefaul() {
		return defaul;
	}

	public void setDefaul(Boolean defaul) {
		this.defaul = defaul;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
