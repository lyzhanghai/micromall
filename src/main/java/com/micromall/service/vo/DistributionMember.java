package com.micromall.service.vo;

import com.micromall.repository.entity.common.MemberLevels;

/**
 * @author zhangzxiang91@gmail.com
 * @date 2016/04/24.
 */
public class DistributionMember {

	// 用户id
	private Integer uid;

	// 用户昵称
	private String nickname;
	// 用户头像
	private String avatar;
	/**
	 * 会员级别 {@link MemberLevels}
	 */
	private String level;

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
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
}
