package com.micromall.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.xml.transform.Source;
import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HttpUtils {

	private final static RestTemplate rest   = new RestTemplate();
	private final static Logger       logger = LoggerFactory.getLogger(HttpUtils.class);

	static {
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		messageConverters.add(new ByteArrayHttpMessageConverter());
		messageConverters.add(new StringHttpMessageConverter(Charset.forName("utf-8")));
		messageConverters.add(new ResourceHttpMessageConverter());
		messageConverters.add(new SourceHttpMessageConverter<Source>());
		messageConverters.add(new AllEncompassingFormHttpMessageConverter());
		rest.setMessageConverters(messageConverters);
	}

	public static <T> ResponseEntity<T> executeRequest(String url, String content, Class<T> clazz) {
		// 执行请求
		ResponseEntity<T> responseEntity = null;
		try {
			// 执行请求
			responseEntity = rest.postForEntity(url, content, clazz);
		} catch (HttpClientErrorException e) {
			responseEntity = new ResponseEntity<T>(null, e.getResponseHeaders(), e.getStatusCode());
		} catch (Exception e) {
			logger.warn("请求出错: URL [{}]", url, e);
		}
		return responseEntity;
	}

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
