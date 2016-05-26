package com.micromall.utils;

import com.micromall.web.resp.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpServletUtils {

	private static Logger logger = LoggerFactory.getLogger(HttpServletUtils.class);

	public static boolean isAjaxRequest(HttpServletRequest request) {
		return "XMLHttpRequest".equals(request.getHeader("X-Requested-With")) || "true".equals(request.getParameter("PAJAX"));
	}

	public static String getRequestIP(HttpServletRequest request) {
		if (request == null) {
			return "unknown";
		}
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static void responseWriter(HttpServletRequest request, HttpServletResponse response, ResponseEntity<?> responseEntity) {
		try {
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(responseEntity.toJSONString());
		} catch (Exception e) {
			logger.warn("请求[{}], 写入返回结果出错: {}", request.getRequestURI(), e.getMessage());
		}
	}
}
