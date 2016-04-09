package com.micromall.utils;

/**
 * Created by zhangzx on 16/3/28.
 */
public class ArgumentValidException extends RuntimeException {

	public ArgumentValidException() {
		super();
	}

	public ArgumentValidException(String s) {
		super(s);
	}

	public ArgumentValidException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public ArgumentValidException(Throwable throwable) {
		super(throwable);
	}
}
