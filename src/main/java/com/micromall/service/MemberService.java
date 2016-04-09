package com.micromall.service;

import com.micromall.entity.Member;
import com.micromall.repository.MemberMapper;
import com.micromall.service.vo.MemberBasisinfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zhangzx on 16/3/26.
 */
@Service
public class MemberService {

	@Resource
	private MemberMapper mapper;

	public boolean updateLoginInfo(Member member) {
		return false;
	}

	public Member registerForPhone(String phone) {
		return null;
	}

	public Member findByPhone(String phone) {
		return null;
	}

	public boolean updateBasisInfo(Member member) {
		return false;
	}

	public Member findByWechatId(String wechatId) {
		return null;
	}

	public Member registerForWechatId(String wechatId) {
		return null;
	}

	public Member get(int id) {
		return null;
	}

	public boolean updatePhone(int id, String phone) {
		return false;
	}

	public boolean updateBasisinfo(int uid, MemberBasisinfo basisinfo) {
		return false;
	}

	public boolean updateAvatar(int uid, String avatar) {
		return false;
	}
}
