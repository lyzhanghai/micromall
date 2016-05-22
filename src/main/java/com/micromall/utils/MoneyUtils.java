package com.micromall.utils;


public class MoneyUtils {

	private final static String ZERO = "000";
	/**
	 * 分转成元为单位
	 *
	 * @param amount
	 * @return
	 */
	public static String fen2yuan(Long amount) {
		String _amount = String.valueOf(amount);
		if (_amount.length() < 3) {
			_amount = ZERO.substring(0, 3 - _amount.length()) + amount;
		}
		return _amount.substring(0, _amount.length() - 2) + "." + _amount.substring(_amount.length() - 2);
	}

	/**
	 * 元转成分为单位
	 *
	 * @param amount
	 * @return
	 */
	public static String yuan2Fen(double amount) {
		return String.valueOf(Math.round(amount * 100));
	}

	/**
	 * 元转成分为单位
	 *
	 * @param amount
	 * @return
	 */
	public static String yuan2Fen(String amount) {
		return String.valueOf(Math.round(Double.valueOf(amount.trim()) * 100));
	}
}
