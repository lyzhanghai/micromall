package com.micromall.web.resp;

public enum Ret {

	ok(0, ""),
	error(1, "请求出错");

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
