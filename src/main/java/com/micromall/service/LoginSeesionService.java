package com.micromall.service;

import com.alibaba.fastjson.JSON;
import com.micromall.repository.entity.Member;
import com.micromall.utils.CommonEnvConstants;
import com.micromall.utils.CookieUtils;
import com.micromall.web.security.entity.LoginUser;
import com.micromall.web.security.entity.LoginUser.LoginType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static Logger logger = LoggerFactory.getLogger(LoginSeesionService.class);

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
		logger.info("sessionId:{}   设置登录用户：{}", request.getSession().getId(), JSON.toJSONString(loginUser));
	}

	public void loginout(HttpServletRequest request, int uid) {
		request.getSession().invalidate();
	}
}
