package com.micromall.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class HttpUtils {

	private final static RestTemplate rest   = new RestTemplate();
	private final static Logger       logger = LoggerFactory.getLogger(HttpUtils.class);

	public static <T> ResponseEntity<T> executeRequest(String url, Map<String, String> params, Class<T> clazz) {
		return executeRequest(url, params, null, clazz);
	}

	public static <T> ResponseEntity<T> executeRequest(String url, Map<String, String> params, Map<String, File> fileParams, Class<T> clazz) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Content-Type", "text/plain;charset=UTF-8");

		if (params == null) {
			params = new HashMap<String, String>();
		}
		// 设置请求参数
		MultiValueMap<String, Object> _params = new LinkedMultiValueMap<String, Object>();
		for (Entry<String, String> entry : params.entrySet()) {
			_params.add(entry.getKey(), new HttpEntity<String>(entry.getValue() != null ? entry.getValue() : "", headers));
		}

		// 上传文件
		if (fileParams != null) {
			for (String name : fileParams.keySet()) {
				_params.add(name, new FileSystemResource(fileParams.get(name)));
			}
		}
		logger.debug("ExecuteRequest: URL [{}], 参数 [{}]", url, _params);

		// 执行请求
		ResponseEntity<T> responseEntity = null;
		try {
			// 执行请求
			responseEntity = rest.postForEntity(url, _params, clazz);
		} catch (HttpClientErrorException e) {
			responseEntity = new ResponseEntity<T>(null, e.getResponseHeaders(), e.getStatusCode());
		} catch (Exception e) {
			logger.warn("请求出错: URL [{}]", url, e);
		}
		return responseEntity;
	}

}
