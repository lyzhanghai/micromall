package com.micromall.web.security;

import com.alibaba.fastjson.JSON;
import com.micromall.repository.entity.Member;
import com.micromall.utils.CommonEnvConstants;
import com.micromall.utils.HttpServletUtils;
import com.micromall.utils.URLBuilder;
import com.micromall.web.RequestContext;
import com.micromall.web.resp.ResponseEntity;
import com.micromall.web.security.entity.LoginUser;
import com.micromall.web.security.entity.LoginUser.LoginType;
import com.micromall.web.security.annotation.Authentication;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FrontAuthenticationInterceptor extends AbstractBaseInterceptor {

	private static final Logger logger             = LoggerFactory.getLogger(FrontAuthenticationInterceptor.class);
	private static final String _AUTHORIZED_HEADER = "X-Authorized";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (isForbidden(request)) {
			_forbidden(request, response);
			return false;
		}

		LoginUser loginUser = _getLoginUser(request);
		if (loginUser != null) {
			response.addHeader(_AUTHORIZED_HEADER, Boolean.TRUE.toString());
			RequestContext.setLoginUser(loginUser);
			return true;
		}

		response.addHeader(_AUTHORIZED_HEADER, Boolean.FALSE.toString());
		Authentication authentication = _getHandlerAuthentication(handler);
		if (isExclude(request) || (authentication == null || !authentication.force())) {
			return true;
		}

		_processNotLogin(request, response);
		return false;
	}

	private void _processNotLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.info("用户未登录访问：请求 [{}], 参数 [{}]", new Object[]{request.getRequestURI(), JSON.toJSONString(request.getParameterMap())});
		String _agent = request.getHeader("User-Agent");
		// 请求是否来自于微信
		boolean weixin = (_agent != null && _agent.contains("MicroMessenger"));

		String redirectUrl;
		if (!weixin && CommonEnvConstants.MOBILE_LOGIN_USABLE) {
			redirectUrl = CommonEnvConstants.MOBILE_AUTHORIZE_LOGIN_URL;
		} else {
			redirectUrl = _buildWeixinAuthorizeUrl(request);
		}

		if (HttpServletUtils.isAjaxRequest(request)) {
			HttpServletUtils.responseWriter(request, response, ResponseEntity.NotLogin(redirectUrl));
		} else {
			response.sendRedirect(redirectUrl);
		}
	}

	private void _forbidden(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		logger.info("拦截访问受保护资源：请求 [{}], 参数 [{}]", new Object[]{request.getRequestURI(), JSON.toJSONString(request.getParameterMap())});
		if (HttpServletUtils.isAjaxRequest(request)) {
			HttpServletUtils.responseWriter(request, response, ResponseEntity.Failure("请求地址[{" + (request.getRequestURI()) + "}]不存在"));
		} else {
			request.getRequestDispatcher(CommonEnvConstants.SERVER_NOTFOUND_REDIRECT_URL).forward(request, response);
		}
	}

	private LoginUser _getLoginUser(HttpServletRequest request) {
		LoginUser loginUser = (LoginUser)request.getSession().getAttribute(CommonEnvConstants.LOGIN_SESSION_KEY);

		boolean debugAuth = CommonEnvConstants.ENV.isDevEnv() && request.getParameterMap().containsKey("debugAuth");
		if (debugAuth) {
			loginUser = _MockLogin(request);
		}
		return loginUser;
	}

	private Authentication _getHandlerAuthentication(Object handler) {
		Authentication authentication = null;
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod)handler;
			authentication = handlerMethod.getMethodAnnotation(Authentication.class);
			if (authentication == null) {
				authentication = handlerMethod.getBeanType().getAnnotation(Authentication.class);
			}
		}
		return authentication;
	}

	private LoginUser _MockLogin(HttpServletRequest request) {
		Member member = new Member();
		member.setId(CommonEnvConstants.DEBUG_AUTH_USERID);
		LoginUser loginUser = LoginUser.create(LoginType.Mock, member, request);
		return loginUser;
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
		urlBuilder.param("redirect_uri",
				URLBuilder.createBuilder(CommonEnvConstants.WEIXIN_AUTH_CALLBACK_URL, false).param("return_url", return_url).build());
		return urlBuilder.build();
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		logger.info("请求 [{}], 参数 [{}] 状态 [200]", new Object[]{request.getRequestURI(), JSON.toJSONString(request.getParameterMap())});
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		RequestContext.clean();
		if (ex != null) {
			logger.error("请求 [{}], 参数 [{}], 异常 [{}]", request.getRequestURI(), JSON.toJSONString(request.getParameterMap()), ex.getMessage());
		}
	}
}
