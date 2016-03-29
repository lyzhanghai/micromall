package com.micromall.web.security;

import com.alibaba.fastjson.JSON;
import com.micromall.utils.CommonEnvConstants;
import com.micromall.utils.URLBuilder;
import com.micromall.web.RequestContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class FrontAuthenticationInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger             = LoggerFactory.getLogger(FrontAuthenticationInterceptor.class);
	private static final String _AUTHORIZED_HEADER = "X-Authorized";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// controller单独采用@PermissionsVerify进行控制
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Authentication permissionsVerify = handlerMethod.getMethodAnnotation(Authentication.class);
			if (permissionsVerify == null) {
				permissionsVerify = handlerMethod.getBeanType().getAnnotation(Authentication.class);
			}
			if (permissionsVerify == null || permissionsVerify.ignore()) {
				return true;
			}
		}

		/*String sid = CookieUtils.getCookieValue(request, CommonEnvConstants.LOGIN_SESSION_COOKIE_SID);
		LoginUser loginUser = (sid != null) ? (LoginUser) request.getSession().getAttribute(CommonEnvConstants.LOGIN_SESSION_KEY) : null;*/
		LoginUser loginUser = (LoginUser) request.getSession().getAttribute(CommonEnvConstants.LOGIN_SESSION_KEY);

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
				response.getWriter().write("\"redirectUrl\":\"" + (weixin ? _buildWeixinAuthorizeUrl(request) : CommonEnvConstants
						.MOBILE_AUTHORIZE_LOGIN_URL) + "\"");
			} else {
				response.sendRedirect((weixin ? _buildWeixinAuthorizeUrl(request) : CommonEnvConstants.MOBILE_AUTHORIZE_LOGIN_URL));
			}
			return false;
		}
		response.addHeader(_AUTHORIZED_HEADER, Boolean.TRUE.toString());
		RequestContext.setLoginUser(loginUser);
		return true;
	}

	private String _buildWeixinAuthorizeUrl(HttpServletRequest request) {
		URLBuilder urlBuilder = URLBuilder.createBuilder(CommonEnvConstants.WEIXIN_AUTHORIZE_URL);
		urlBuilder.param("appid", CommonEnvConstants.WEIXIN_APPID);
		urlBuilder.param("response_type", "code");
		urlBuilder.param("scope", CommonEnvConstants.WEIXIN_AUTH_SCOPE);
		// 用户授权登陆成功后返回的原页面地址
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
		RequestContext.clean();
		logger.info("请求 [{}], 参数 [{}], 异常 [{}]", new Object[]{request.getRequestURI(), JSON.toJSONString(request.getParameterMap()), ex.getMessage
				()});
	}

}
