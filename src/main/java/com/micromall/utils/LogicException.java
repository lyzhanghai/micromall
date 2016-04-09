package com.micromall.utils;

/**
 * Created by zhangzx on 16/3/28.
 */
public class LogicException extends RuntimeException {

	public LogicException() {
		super();
	}

	public LogicException(String s) {
		super(s);
	}

	public LogicException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public LogicException(Throwable throwable) {
		super(throwable);
	}
}
