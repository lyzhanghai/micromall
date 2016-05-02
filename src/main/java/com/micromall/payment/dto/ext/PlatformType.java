package com.micromall.payment.dto.ext;

/**
 * @author ciwei@xiaokakeji.com
 * @date 2016/05/02.
 */
public enum PlatformType {
	IOS("ios"),
	ANDROID("android"),
	WEB("web"),
	WAP("wap");
	private String code;

	PlatformType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
