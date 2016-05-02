//package com.micromall.payment.utils;
//
//import org.apache.commons.httpclient.*;
//import org.apache.commons.httpclient.cookie.CookiePolicy;
//import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
//import org.apache.commons.httpclient.methods.PostMethod;
//import org.apache.commons.httpclient.methods.RequestEntity;
//import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
//import org.apache.commons.httpclient.params.HttpMethodParams;
//
//import java.io.UnsupportedEncodingException;
//import java.net.SocketTimeoutException;
//
///**
// * 用于http请求
// * 线程安全
// * */
//public class HttpUtils {
//
//	public static String encoding;
//
//	private static final HttpConnectionManager connectionManager;
//
//	private static final HttpClient client;
//
//	static {
//		HttpConnectionManagerParams params = loadHttpConfFromFile();
//		connectionManager = new MultiThreadedHttpConnectionManager();
//		connectionManager.setParams(params);
//		client = new HttpClient(connectionManager);
//	}
//
//	private static HttpConnectionManagerParams loadHttpConfFromFile() {
//		encoding = "UTF-8";
//		HttpConnectionManagerParams params = new HttpConnectionManagerParams();
//		params.setConnectionTimeout(20000);
//		params.setSoTimeout(30000);
//		params.setStaleCheckingEnabled(true);
//		params.setTcpNoDelay(true);
//		params.setDefaultMaxConnectionsPerHost(100);
//		params.setMaxTotalConnections(1000);
//		params.setParameter(HttpMethodParams.RETRY_HANDLER,
//				new DefaultHttpMethodRetryHandler(0, false));
//		return params;
//	}
//
//	public static String post(String url, String encoding, String content) {
//		try {
//			byte[] resp = post(url, content.getBytes(encoding));
//			if (null == resp)
//				return null;
//			return new String(resp, encoding);
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//
//	public static String post(String url, String content) {
//		return post(url, encoding, content);
//	}
//
//	public static byte[] post(String url, byte[] content) {
//		try {
//			byte[] ret = post(url, new ByteArrayRequestEntity(content));
//			return ret;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//
//	public static byte[] post(String url, RequestEntity requestEntity)
//			throws Exception {
//
//		PostMethod method = new PostMethod(url);
//		method.addRequestHeader("Connection", "Keep-Alive");
//		method.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
//		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
//				new DefaultHttpMethodRetryHandler(0, false));
//		method.setRequestEntity(requestEntity);
//		method.addRequestHeader("Content-Type",
//				"application/x-www-form-urlencoded");
//
//		try {
//			int statusCode = client.executeMethod(method);
//			if (statusCode != HttpStatus.SC_OK) {
//				return method.getResponseBody();
//			}
//			return method.getResponseBody();
//		} catch (SocketTimeoutException e) {
//			e.printStackTrace();
//			return null;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		} finally {
//			method.releaseConnection();
//		}
//	}
//}
