package com.micromall.web.security;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminAuthenticationInterceptor extends AbstractExcludeInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(AdminAuthenticationInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		logger.info("请求 [{}], 参数 [{}], 结果 [{}]", request.getRequestURI(), JSON.toJSONString(request.getParameterMap()),
				JSON.toJSONString(modelAndView.getModel()));
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		logger.info("请求 [{}], 参数 [{}], 异常 [{}]", request.getRequestURI(), JSON.toJSONString(request.getParameterMap()), ex.getMessage());
	}

}
