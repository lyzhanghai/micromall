package com.micromall.web.security;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractExcludeInterceptor extends HandlerInterceptorAdapter {

	private Set<String> excludes = new HashSet<String>();

	public Set<String> getExcludes() {
		return excludes;
	}

	public void setExcludes(Set<String> excludes) {
		this.excludes = excludes;
	}

	public boolean isExclude(HttpServletRequest request) {
		String url = request.getRequestURI();
		for (String exclude : excludes) {
			if (_isExclude(url, exclude)) {
				return true;
			}
		}
		return false;
	}

	private boolean _isExclude(String url, String exclude) {
		if (url.contains("@")) {
			url = url.substring(url.indexOf("@"));
		}

		if (exclude.endsWith("*")) {
			if (url.startsWith(exclude.substring(0, exclude.length() - 1))) {
				if (!url.contains("..") && !url.toLowerCase().contains("%2e")) {
					return true;
				}
			}
		} else if (exclude.startsWith("*")) {
			if (url.endsWith(exclude.substring(1))) {
				if (!url.contains("..") && !url.toLowerCase().contains("%2e")) {
					return true;
				}
			}
		} else if (exclude.contains("?")) {
			if (url.equals(exclude)) {
				return true;
			}
		} else {
			int paramIndex = url.indexOf("?");
			if (paramIndex != -1) {
				url = url.substring(0, paramIndex);
			}
			if (url.equals(exclude)) {
				return true;
			}
		}
		return false;
	}
}
