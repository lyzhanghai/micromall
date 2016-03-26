package com.micromall.web;

import com.micromall.entity.LoginUser;

import java.util.HashMap;
import java.util.Map;


public class RequestContext {

	private final static String LOGIN_USER_KEY = "LoginUser";

	private static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>() {

		@Override
		protected Map<String, Object> initialValue() {
			return new HashMap<String, Object>();
		}
	};

	public static LoginUser getLoginUser() {
		return (LoginUser) threadLocal.get().get(LOGIN_USER_KEY);
	}

	public static void setLoginUser(LoginUser loginUser) {
		threadLocal.get().put(LOGIN_USER_KEY, loginUser);
	}

	public static void clean() {
		threadLocal.remove();
	}
}
