package com.micromall.entity;

import java.util.Date;

/**
 * Created by zhangzx on 16/3/21.
 */
public class LoginUser {

	// 用户id
	private int    uid;
	// 用户手机号
	private String mobile;
	// 微信用户id<如果绑定了微信>
	private String wechatId;
	// 授权登录类型<微信授权登录、手机号登录>
	private String loginType;
	// 登录时间
	private Date   loginTime;
	// 登录ip
	private String loginIp;

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getWechatId() {
		return wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
}
