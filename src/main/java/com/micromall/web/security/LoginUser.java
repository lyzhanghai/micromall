package com.micromall.web.security;

import com.micromall.repository.entity.Member;
import com.micromall.utils.IPUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhangzx on 16/3/21.
 */
public class LoginUser implements Serializable {

	// 用户id
	private int       uid;
	// 授权登录类型<微信授权登录、手机号登录>
	private LoginType loginType;
	// 登录时间
	private Date      loginTime;
	// 登录ip
	private String    loginIp;

	private LoginUser() {
	}

	public static LoginUser create(LoginType loginType, Member member, HttpServletRequest request) {
		LoginUser loginUser = new LoginUser();
		loginUser.setUid(member.getId());
		loginUser.setLoginType(loginType);
		loginUser.setLoginTime(new Date());
		loginUser.setLoginIp(IPUtils.getIp(request));
		return loginUser;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public LoginType getLoginType() {
		return loginType;
	}

	public void setLoginType(LoginType loginType) {
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

	@Override
	public String toString() {
		return "LoginUser{" +
				"uid=" + uid +
				", loginType=" + loginType +
				", loginTime=" + loginTime +
				", loginIp='" + loginIp + '\'' +
				'}';
	}

	public enum LoginType {
		Phone, WeChat, Mock
	}
}
