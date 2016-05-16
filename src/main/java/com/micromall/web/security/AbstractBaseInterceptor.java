package com.micromall.web.security;

import com.google.common.collect.Sets;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

public abstract class AbstractBaseInterceptor extends HandlerInterceptorAdapter {

	private Set<String> excludes = Sets.newHashSet();

	private Set<String> forbiddens = Sets.newHashSet();

	public Set<String> getExcludes() {
		return excludes;
	}

	public void setExcludes(Set<String> excludes) {
		this.excludes = excludes;
	}

	public Set<String> getForbiddens() {
		return forbiddens;
	}

	public void setForbiddens(Set<String> forbiddens) {
		this.forbiddens = forbiddens;
	}

	public boolean isForbidden(HttpServletRequest request) {
		return isMatch(request, forbiddens);
	}

	public boolean isExclude(HttpServletRequest request) {
		return isMatch(request, excludes);
	}

	protected boolean isMatch(HttpServletRequest request, Set<String> urls) {
		String url = request.getRequestURI();
		for (String _url : urls) {
			if (_isMatch(url, _url)) {
				return true;
			}
		}
		return false;
	}

	private boolean _isMatch(String url, String exclude) {
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
