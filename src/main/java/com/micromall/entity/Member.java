package com.micromall.entity;

import com.micromall.entity.ext.MemberLevels;

import java.util.Date;

/**
 * Created by zhangzx on 16/3/21.
 * 会员信息
 */
public class Member extends IdEntity {

	// 登录手机号（需要绑定手机号）
	private String  phone;
	// 微信id
	private String  wechatId;
	// 用户昵称
	private String  nickname;
	// 用户头像
	private String  avatar;
	/**
	 * 会员级别 {@link MemberLevels}
	 */
	private String  level;
	// 性别
	private String  gender;
	// 生日
	private String  birthday;
	// 推广码
	private String  myPromoteCode;
	// 上级分销商推广码
	private String  usePromoteCode;
	// 上级分销商用户id
	private Integer parentUid;
	// 是否认证用户
	private Boolean verified;
	// 是否逻辑删除
	private Boolean delete;
	// 最后登录时间
	private Date    lastLoginTime;
	// 最后一次登录IP
	private String  lastLoginIp;
	// 用户注册时间
	private Date    registerTime;
	// 更新时间
	private Date    updateTime;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getWechatId() {
		return wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getMyPromoteCode() {
		return myPromoteCode;
	}

	public void setMyPromoteCode(String myPromoteCode) {
		this.myPromoteCode = myPromoteCode;
	}

	public String getUsePromoteCode() {
		return usePromoteCode;
	}

	public void setUsePromoteCode(String usePromoteCode) {
		this.usePromoteCode = usePromoteCode;
	}

	public Integer getParentUid() {
		return parentUid;
	}

	public void setParentUid(Integer parentUid) {
		this.parentUid = parentUid;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}

	public Boolean isVerified() {
		return verified;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
