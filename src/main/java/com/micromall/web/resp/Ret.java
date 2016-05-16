package com.micromall.web.resp;

public enum Ret {

	Success(0, ""),
	Error(1, "系统繁忙,请稍后再试"),
	NotLogin(-1, "用户未登录");

	private int    code;
	private String message;

	Ret(int code, String msg) {
		this.code = code;
		this.message = msg;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "Ret{" +
				"code=" + code +
				", message='" + message + '\'' +
				'}';
	}
}
