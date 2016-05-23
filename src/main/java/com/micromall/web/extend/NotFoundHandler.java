package com.micromall.web.extend;

import com.micromall.utils.CommonEnvConstants;
import com.micromall.utils.HttpServletUtils;
import com.micromall.web.resp.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NotFoundHandler extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(NotFoundHandler.class);

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("请求地址 {} 不存在", request.getRequestURI());
		if (HttpServletUtils.isAjaxRequest(request)) {
			HttpServletUtils.responseWriter(request, response, ResponseEntity.Failure("请求地址[{" + (request.getRequestURI()) + "}]不存在"));
		} else {
			request.getRequestDispatcher(CommonEnvConstants.SERVER_INDEX_REDIRECT_URL).forward(request, response);
		}
		return null;
	}

}
