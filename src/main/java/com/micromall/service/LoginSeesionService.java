/*
 * Copyright (C), 2014-2015, 杭州小卡科技有限公司
 * Created by ciwei@xiaokakeji.com on 2016/04/19.
 */
package com.micromall.service;

import com.micromall.entity.Member;
import com.micromall.utils.CommonEnvConstants;
import com.micromall.utils.CookieUtils;
import com.micromall.web.security.LoginUser;
import com.micromall.web.security.LoginUser.LoginType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author zhangzxiang91@gmail.com
 * @date 2016/04/19.
 */
@Service
public class LoginSeesionService {

	@Resource
	private MemberService memberService;

	public void processLogin(LoginType loginType, Member member, HttpServletRequest request, HttpServletResponse response) {
		LoginUser loginUser = LoginUser.create(loginType, member, request);

		// 更新登录数据
		Member _updateMember = new Member();
		_updateMember.setId(member.getId());
		_updateMember.setLastLoginIp(loginUser.getLoginIp());
		_updateMember.setLastLoginTime(loginUser.getLoginTime());
		memberService.update(_updateMember);

		// 保存会话session，重定向到用户最开始访问的页面
		String sid = UUID.randomUUID().toString().replace("-", "");
		CookieUtils.addCookie(response, CommonEnvConstants.LOGIN_SESSION_COOKIE_SID, sid);
		/*cacheService.set(CommonEnvConstants.LOGIN_SESSION_KEY, sid, loginUser, CommonEnvConstants.SESSION_CACHE_EXPRIED);*/
		request.getSession().setAttribute(CommonEnvConstants.LOGIN_SESSION_KEY, loginUser);
	}

	public void loginout(HttpServletRequest request) {
		request.getSession().invalidate();
	}
}
