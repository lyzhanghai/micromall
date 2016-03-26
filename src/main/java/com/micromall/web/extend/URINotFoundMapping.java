package com.micromall.web.extend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class URINotFoundMapping extends AbstractController {
	private static final Logger logger = LoggerFactory.getLogger(URINotFoundMapping.class);

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("请求地址 {} 不存在", request.getRequestURI());
		return null;
	}

}
