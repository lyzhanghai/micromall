package com.micromall.web.extend;

import com.alibaba.fastjson.JSON;
import com.micromall.utils.ArgumentValidException;
import com.micromall.utils.HttpServletUtils;
import com.micromall.utils.LogicException;
import com.micromall.web.extend.annotation.Uncaught;
import com.micromall.web.resp.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HandlerExceptionResolver extends SimpleMappingExceptionResolver {

	private static Logger logger = LoggerFactory.getLogger(HandlerExceptionResolver.class);

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		String message = "系统异常";
		if (ex instanceof LogicException || ex instanceof ArgumentValidException) {
			message = ex.getMessage();
			logger.error("请求 [{}], 参数 [{}], 异常：{}", request.getRequestURI(), JSON.toJSONString(request.getParameterMap()), ex.getMessage());
		} else if (handler instanceof HandlerMethod) {
			Uncaught uncaught = ((HandlerMethod)handler).getMethodAnnotation(Uncaught.class);
			if (uncaught != null) {
				message = uncaught.msg();
			}
			logger.error("请求 [{}], 参数 [{}], 异常：", request.getRequestURI(), JSON.toJSONString(request.getParameterMap()), ex);
		} else {
			logger.error("请求 [{}], 参数 [{}], 异常：", request.getRequestURI(), JSON.toJSONString(request.getParameterMap()), ex);
		}
		HttpServletUtils.responseWriter(request, response, ResponseEntity.Failure(message));
		return null;
	}
}
