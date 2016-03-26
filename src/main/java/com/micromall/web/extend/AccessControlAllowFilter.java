package com.micromall.web.extend;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <pre>
 * 解决js访问跨域问题
 *
 * @author Zhang Zhongxiang<zhangzxiang91@gmail.com>
 * @ClassName: AccessControlAllowFilter
 * @date 2015年7月1日 下午6:19:24
 */
public class AccessControlAllowFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse resp = (HttpServletResponse) response;
		resp.addHeader("Access-Control-Allow-Origin", "*");
		resp.addHeader("Access-Control-Allow-Methods", "HEAD, POST, GET");
		resp.addHeader("Access-Control-Allow-Headers", "accept, origin, x-requested-with, content-type");
		resp.addHeader("Access-Control-Allow-Credentials", "true");
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}
}
