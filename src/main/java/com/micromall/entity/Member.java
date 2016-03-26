package com.micromall.entity;

import java.util.Date;

/**
 * Created by zhangzx on 16/3/21.
 */
public class Member extends IdEntity {

	// 登录手机号（需要绑定手机号）
	private String mobile;
	// 登录密码（备用）
	private String password;
	// 微信id
	private String wechatId;
	// 用户昵称
	private String nickname;
	// 用户头像
	private String avatar;
	// 用户等级
	private int    level;
	// 性别
	private String gender;
	// 生日
	private String birthday;
	// 推广码
	private String myPromoteCode;
	// 注册时填写的推广码
	private String usePromoteCode;
	// 最后登录时间
	private Date   lastLoginTime;
	// 最后一次登录IP
	private String lastLoginIp;
	// 用户注册时间
	private Date   registerTime;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
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
}
