package com.micromall.web.extend;

import com.alibaba.fastjson.JSON;
import com.micromall.utils.ArgumentValidException;
import com.micromall.utils.LogicException;
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
			HandlerMethod handlerMethod = (HandlerMethod)handler;
			UncaughtException uncaughtException = handlerMethod.getMethodAnnotation(UncaughtException.class);
			if (uncaughtException != null) {
				message = uncaughtException.msg();
			}
			logger.error("请求 [{}], 参数 [{}], 异常：", request.getRequestURI(), JSON.toJSONString(request.getParameterMap()), ex);
		} else {
			logger.error("请求 [{}], 参数 [{}], 异常：", request.getRequestURI(), JSON.toJSONString(request.getParameterMap()), ex);
		}

		try {
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(JSON.toJSONString(ResponseEntity.fail(message)));

			/*
			// 是否为ajax请求？
			boolean ajax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
			if (ajax) {
				response.setContentType("application/json;charset=UTF-8");
				response.getWriter().write(JSON.toJSONString(ResponseEntity.fail(message)));
			} else {
				// response.sendRedirect(CommonEnvConstants.SERVER_ERROR_REDIRECT_URL);
				request.getRequestDispatcher(CommonEnvConstants.SERVER_ERROR_REDIRECT_URL).forward(request, response);
			}*/
		} catch (Exception e) {
			logger.warn("请求[{}], 写入返回结果出错: {}", request.getRequestURI(), e.getMessage());
		}
		return null;
	}
}
