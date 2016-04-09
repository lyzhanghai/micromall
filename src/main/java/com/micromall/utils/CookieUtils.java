package com.micromall.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

public class CookieUtils {

	private static Logger logger = LoggerFactory.getLogger(CookieUtils.class);

	public static String getCookieValue(HttpServletRequest request, String key) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		}
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (key.equals(cookie.getName())) {
				return cookie.getValue();
			}
		}
		return null;
	}

	public static void addCookie(HttpServletResponse response, String key, String value) {
		Cookie cookie = new Cookie(key, value);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	public static void addObjectToCookies(HttpServletResponse response, String key, Object amchartData) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(amchartData);

			String cookieValue = new String(baos.toByteArray(), "UTF-8");
			Cookie cookie = new Cookie(key, cookieValue);
			cookie.setSecure(false);
			cookie.setPath("/");
			cookie.setMaxAge(6000);
			response.addCookie(cookie);
		} catch (Exception e) {
			logger.error("addObjectToCookies ERRO：", e);
		}
	}

	public static void clearCookie(HttpServletRequest request, HttpServletResponse response, String key) {
		String val = getCookieValue(request, key);
		if (val != null && !val.trim().equals("")) {
			Cookie cookie = new Cookie(key, "");
			cookie.setMaxAge(0);
			cookie.setPath("/");
			response.addCookie(cookie);
		}
	}
}
