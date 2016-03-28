package com.micromall.service.vo;

/**
 * Created by zhangzx on 16/3/28.
 */
public class MemberBasisinfo {
	// 用户昵称
	private String nickname;
	// 性别
	private String gender;
	// 生日
	private String birthday;

	public MemberBasisinfo() {
	}

	public MemberBasisinfo(String nickname, String gender, String birthday) {
		this.nickname = nickname;
		this.gender = gender;
		this.birthday = birthday;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
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
}
