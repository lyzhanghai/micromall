package com.micromall.utils;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
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
		messageConverters.add(new SourceHttpMessageConverter<>());
		messageConverters.add(new AllEncompassingFormHttpMessageConverter());
		rest.setMessageConverters(messageConverters);
	}

	public static <T> ResponseEntity<T> executePost(String url, String content, Class<T> clazz) {
		ResponseEntity<T> responseEntity = null;
		try {
			responseEntity = rest.postForEntity(url, content, clazz);
		} catch (HttpClientErrorException e) {
			responseEntity = new ResponseEntity<>(null, e.getResponseHeaders(), e.getStatusCode());
		} catch (Exception e) {
			logger.warn("请求出错: URL [{}]", url, e);
		}
		return responseEntity;
	}

	public static <T> ResponseEntity<T> executePost(String url, Map<String, String> params, Class<T> clazz) {
		return execute(Method.POST, new ChainMap<>("Content-Type", "text/plain;charset=UTF-8"), url, params, null, clazz);
	}

	public static <T> ResponseEntity<T> execute(Method method, Map<String, String> headers, String url, Map<String, String> params,
			Map<String, File> fileParams, Class<T> clazz) {

		if (MapUtils.isNotEmpty(fileParams) && method != Method.POST) {
			throw new UnsupportedOperationException("GET 请求不支持文件上传.");
		}

		ResponseEntity<T> responseEntity = null;
		if (Method.GET == method) {
			URLBuilder builder = URLBuilder.createBuilder(url, params);
			url = builder.build();

			HttpHeaders _headers = new HttpHeaders();
			if (MapUtils.isNotEmpty(headers)) {
				for (String key : headers.keySet()) {
					_headers.add(key, headers.get(key));
				}
			}

			logger.debug("Execute get request: URL [{}]", url);
			try {
				responseEntity = rest.exchange(url, HttpMethod.GET, new HttpEntity<>(_headers), clazz);
			} catch (HttpClientErrorException e) {
				responseEntity = new ResponseEntity<T>(null, e.getResponseHeaders(), e.getStatusCode());
			} catch (Exception e) {
				logger.warn("请求出错: URL [{}]", url, e);
			}
		} else {
			MultiValueMap<String, String> _headers = new LinkedMultiValueMap<String, String>();
			if (MapUtils.isNotEmpty(headers)) {
				for (String key : headers.keySet()) {
					_headers.add(key, headers.get(key));
				}
			}

			// 设置请求参数
			MultiValueMap<String, Object> _params = new LinkedMultiValueMap<>();
			for (Entry<String, String> entry : params.entrySet()) {
				_params.add(entry.getKey(), new HttpEntity<>(entry.getValue() != null ? entry.getValue() : "", _headers));
			}

			// 上传文件
			if (fileParams != null) {
				for (String name : fileParams.keySet()) {
					_params.add(name, new FileSystemResource(fileParams.get(name)));
				}
			}
			logger.debug("Execute post request: URL [{}], 参数 [{}]", url, _params);
			try {
				responseEntity = rest.postForEntity(url, _params, clazz);
			} catch (HttpClientErrorException e) {
				responseEntity = new ResponseEntity<T>(null, e.getResponseHeaders(), e.getStatusCode());
			} catch (Exception e) {
				logger.warn("请求出错: URL [{}]", url, e);
			}
		}
		return responseEntity;
	}

	public enum Method {
		POST, GET
	}

}
