package com.micromall.utils;

/**
 * Created by zhangzx on 16/3/28.
 */
public class ValidateException extends RuntimeException {
	public ValidateException() {
		super();
	}

	public ValidateException(String s) {
		super(s);
	}

	public ValidateException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public ValidateException(Throwable throwable) {
		super(throwable);
	}
}
