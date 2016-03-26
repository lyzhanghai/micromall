package com.micromall.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * URL拼接工具类
 *
 * @author 张忠祥(zhangzhongxiang@hztianque.com)
 * @date 2013-7-16 下午3:56:23
 */
public class URLBuilder {

	private static final String URL_ENCODING = "UTF-8";
	private String url;
	private Map<String, String> parameters = new LinkedHashMap<String, String>();
	private boolean             urlEncoder = true;

	public URLBuilder(String url) {
		this.url = url;
	}

	public URLBuilder(String url, boolean urlEncoder) {
		this.url = url;
		this.urlEncoder = urlEncoder;
	}

	public URLBuilder(String url, Map<String, String> parameters, boolean urlEncoder) {
		this.url = url;
		if (null != parameters) {
			this.parameters = parameters;
		}
		this.urlEncoder = urlEncoder;
	}

	public static URLBuilder createBuilder(String url) {
		URLBuilder builder = new URLBuilder(url);
		return builder;
	}

	public static URLBuilder createBuilder(String url, boolean urlEncoding) {
		URLBuilder builder = new URLBuilder(url, urlEncoding);
		return builder;
	}

	public static URLBuilder createBuilder(String url, Map<String, String> parameters) {
		URLBuilder builder = new URLBuilder(url, parameters, true);
		return builder;
	}

	public static void main(String[] args) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("p1", "v1");
		parameters.put("p2", "v2");
		parameters.put("p3", "v3");
		new URLBuilder("http://localhost:8080/module.jsp?name=xx&age=12#index", parameters, true).build();
	}

	public URLBuilder param(String name, String value) {
		this.parameters.put(name, value);
		return this;
	}

	@Override
	public String toString() {
		return build();
	}

	public String build() {
		StringBuffer targetUrl = new StringBuffer();
		targetUrl.append(this.url);
		String fragment = null;
		int anchorIndex = targetUrl.indexOf("#");
		if (anchorIndex != -1) {
			fragment = targetUrl.substring(anchorIndex);
			targetUrl.delete(anchorIndex, targetUrl.length());
		}

		boolean first = (this.url.indexOf('?') == -1);
		try {
			Iterator<String> it = this.parameters.keySet().iterator();
			while (it.hasNext()) {
				String name = it.next();
				String value = this.parameters.get(name);
				if (first) {
					targetUrl.append('?');
					first = false;
				} else {
					targetUrl.append('&');
				}
				if (urlEncoder) {
					String encodedName = URLEncoder.encode(name, URL_ENCODING);
					String encodedValue = (value != null ? URLEncoder.encode(value, URL_ENCODING) : "");
					targetUrl.append(encodedName).append('=').append(encodedValue);
				} else {
					targetUrl.append(name).append('=').append((value != null ? value : ""));
				}
			}
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		if (fragment != null) {
			targetUrl.append(fragment);
		}

		return targetUrl.toString();
	}
}
