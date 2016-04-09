package com.micromall.utils;

/**
 * Created by zhangzx on 16/3/28.
 */
public class ServiceException extends RuntimeException {

	public ServiceException() {
		super();
	}

	public ServiceException(String s) {
		super(s);
	}

	public ServiceException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public ServiceException(Throwable throwable) {
		super(throwable);
	}
}
