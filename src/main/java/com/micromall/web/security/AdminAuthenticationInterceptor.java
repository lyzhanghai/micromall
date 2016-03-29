package com.micromall.web.security;

import com.alibaba.fastjson.JSON;
import com.micromall.utils.CommonEnvConstants;
import com.micromall.utils.CookieUtils;
import com.micromall.utils.URLBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AdminAuthenticationInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger             = LoggerFactory.getLogger(AdminAuthenticationInterceptor.class);
	private static final String _AUTHORIZED_HEADER = "X-Authorized";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String sid = CookieUtils.getCookieValue(request, CommonEnvConstants.LOGIN_SESSION_COOKIE_SID);
		LoginUser loginUser = (sid != null) ? (LoginUser) request.getSession().getAttribute(CommonEnvConstants.LOGIN_SESSION_KEY) : null;

		// 用户未登录
		if (loginUser == null) {
			logger.info("用户未登录访问：请求 [{}], 参数 [{}]", new Object[]{request.getRequestURI(), JSON.toJSONString(request.getParameterMap())});
			response.addHeader(_AUTHORIZED_HEADER, Boolean.FALSE.toString());
			String _agent = request.getHeader("User-Agent");
			// 请求是否来自于微信
			boolean weixin = (_agent != null && _agent.contains("MicroMessenger"));
			// 是否为ajax请求？
			boolean ajax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
			if (ajax) {
				response.getWriter().write("\"redirectUrl\":\"" + (weixin ? buildWeixinAuthorizeUrl(request) : CommonEnvConstants
						.MOBILE_AUTHORIZE_LOGIN_URL) + "\"");
				response.getWriter().flush();
			} else {
				response.sendRedirect((weixin ? buildWeixinAuthorizeUrl(request) : CommonEnvConstants.MOBILE_AUTHORIZE_LOGIN_URL));
			}
			return false;
		}
		response.addHeader(_AUTHORIZED_HEADER, Boolean.TRUE.toString());
		return true;
	}

	private String buildWeixinAuthorizeUrl(HttpServletRequest request) {
		// 用户授权登陆成功后返回的原页面地址
		URLBuilder urlBuilder = URLBuilder.createBuilder(CommonEnvConstants.WEIXIN_AUTHORIZE_URL);
		urlBuilder.param("appid", CommonEnvConstants.WEIXIN_APPID);
		urlBuilder.param("response_type", "code");
		urlBuilder.param("scope", "snsapi_base");
		String return_url = "";
		if (request.getMethod().equals("GET")) {
			return_url = request.getRequestURI();
			if (!StringUtils.isEmpty(request.getQueryString())) {
				return_url += "?" + request.getQueryString();
			}
		}
		urlBuilder.param("redirect_uri", URLBuilder.createBuilder(CommonEnvConstants.WEIXIN_AUTH_CALLBACK_URL, false).param("return_url",
				return_url).build());
		return urlBuilder.build();
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		logger.info("请求 [{}], 参数 [{}], 结果 [{}]", new Object[]{request.getRequestURI(), JSON.toJSONString(request.getParameterMap()), JSON
				.toJSONString(modelAndView.getModel())});
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		logger.info("请求 [{}], 参数 [{}], 异常 [{}]", new Object[]{request.getRequestURI(), JSON.toJSONString(request.getParameterMap()), ex.getMessage
				()});
	}

}
