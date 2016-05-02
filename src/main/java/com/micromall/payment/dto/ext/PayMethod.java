package com.micromall.payment.dto.ext;

/**
 * @author zhangzxiang91@gmail.com
 * @date 2016/04/27.
 */
public interface PayMethod {

	String getCode();

	public enum Wechat implements PayMethod {

		公众号支付("OfficialAccounts"),
		扫码支付("SCANCODE"),
		APP支付("APP");

		private String code;

		Wechat(String code) {
			this.code = code;
		}

		public String getCode() {
			return code;
		}

	}

	public enum Alipay implements PayMethod {

		PC支付("PC"),
		WAP支付("WAP"),
		APP支付("APP"),
		扫码支付("SCANCODE");

		private String code;

		Alipay(String code) {
			this.code = code;
		}

		public String getCode() {
			return code;
		}

	}
}
