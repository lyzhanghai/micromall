package com.micromall.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class ValidateUtils {

	private static final Pattern PHONE_1         = Pattern.compile("^(\\+\\d+)?1[34578]\\d{9}$");
	private static final Pattern PHONE_2         = Pattern.compile("^((\\+86)?\\d{3,4}-?)?\\d{7,8}$");
	private static final Pattern DECIMALS        = Pattern.compile("(-?\\d+)(\\.\\d+)?");
	private static final Pattern POSITIVE_NUMBER = Pattern.compile("^(0|([1-9](\\d+)*))$");
	private static final Pattern SIMPLE_DATE     = Pattern.compile(
			"^([1-9][0-9]{0,3}-(((0?[13578]|1[02])-(0?[1-9]|([12][0-9]|3[01])))|(" + "(0?[469]|11)-"
					+ "(0?[1-9]|([12][0-9]|30)))|((0?2)-(0?[1-9]|([12][0-9])))))$");

	/**
	 * <pre>
	 * 字符串长度验证
	 *
	 * @param min
	 * @param max
	 * @param text
	 * @return boolean
	 * @date 2015年6月15日 下午4:07:16
	 */
	public static boolean illegalStringLength(int min, int max, String text) {
		if (StringUtils.isEmpty(text)) {
			if (min > 0) {
				return true;
			} else {
				return false;
			}
		}
		return text.length() < min || text.length() > max;
	}

	/**
	 * <pre>
	 * 电话号码验证
	 *
	 * 固定电话号码：
	 * +86:国家代码
	 * 0712：区号
	 * 支持格式：+8607122347420,
	 * 			+860712-2347420,
	 * 			07122347420,
	 * 			0712-2347420,
	 * 			2347420,
	 *
	 * 手机号码（支持国际格式，+8613588888888）
	 * 支持以：13、14、15、16、17、18 开头的手机号验证
	 *
	 * @param text
	 * @return boolean
	 * @date 2015年6月15日 下午4:12:52
	 */
	public static boolean illegalPhone(String text) {
		return !PHONE_1.matcher(text).matches() && !PHONE_2.matcher(text).matches();
	}

	/**
	 * <pre>
	 * 正负浮点数验证，支持：-1，1，1.0，-1.0
	 *
	 * @param text
	 * @return boolean
	 * @date 2015年6月15日 下午4:21:01
	 */
	public static boolean illegalDecimals(String text) {
		return !DECIMALS.matcher(text).matches();
	}

	/**
	 * <pre>
	 * 正整数
	 *
	 * @param text
	 * @return boolean
	 * @date 2015年6月15日 下午4:57:57
	 */
	public static boolean illegalPositiveNumber(String text) {
		return !POSITIVE_NUMBER.matcher(text).matches();
	}

	/**
	 * 日期（年-月-日）
	 *
	 * @param text
	 * @return
	 */
	public static boolean illegalSimpleDate(String text) {
		return !SIMPLE_DATE.matcher(text).matches();
	}

	//
	// public static boolean containValue(String value, String[] values) {
	// boolean flag = false;
	// for (String vl : values) {
	// if (vl.equals(value)) {
	// flag = true;
	// break;
	// }
	// }
	// return flag;
	// }
	//
	// public static String getShortClassName(Class<?> clazz) {
	// String[] names = clazz.getName().split("\\.");
	// String attr = names[names.length - 1];
	// attr = attr.substring(0, 1).toLowerCase() + attr.substring(1);
	// return attr;
	// }
	//
	// public static String[] cellValueTrim(String[] cellValues) {
	// for (int i = 0; i < cellValues.length; i++) {
	// cellValues[i] = cellValues[i].trim();
	// }
	// return cellValues;
	// }
	//
	// //
	// // public static boolean illegalIMSI(String str) {
	// // if (!emptyString(str)) {
	// // return true;
	// // }
	// // if (str.length() != 15) {
	// // return true;
	// // }
	// // Pattern pattern = Pattern.compile("[0-9]*");
	// // return !pattern.matcher(str).matches();
	// // }
	//
	// /**
	// * 功能：判断一个字符串是否包含特殊字符 汉字a-z A-Z 0-9
	// */
	// public static boolean isConSpeCharacters(String string) {
	// if
	// (string.replaceAll("[\u4e00-\u9fa5]*[a-z]*[A-Z]*\\d*-*_*\\s*）*（*\\(*\\)*",
	// "").length() == 0) {
	// return true;
	// }
	// return false;
	// }
	//
	// /**
	// * <pre>
	// * 只能是由字母、数字、中文组成的字符
	// *
	// * 如果被校验的值为空，默认不校验，直接返回true
	// *
	// * @author zhangzhongxiang@hztianque.com
	// *
	// * 2012-11-21
	// */
	// public static boolean exculdeParticalChar(String val) {
	// if (StringUtils.isEmpty(val)) {
	// return true;
	// }
	// String regex = "^[A-Za-z0-9\u4E00-\u9FA5]+$";
	// return Pattern.matches(regex, val);
	// }

	// public static boolean illegalStringLength(int min, int max, String text)
	// {
	// if (emptyString(text)) {
	// if (min > 0) {
	// return true;
	// } else {
	// return false;
	// }
	// }
	// // 先去掉输入的字符串中的换行和回车添加的符号
	// String[] remove = { "\n" };
	// for (int i = 0; i < remove.length; i++) {
	// text = text.replaceAll(remove[i], "");
	// }
	// return text.length() < min || text.length() > max;
	// }
	//
	// public static boolean illegalMobilePhone(String text) {
	// if (text.length() < 11 || text.length() > 11) {
	// return true;
	// }
	// return !text.matches("^1[0-9]\\d{9}$");
	// }
	//
	// public static boolean illegalEmail(String mail) {
	// String regex =
	// ("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
	// Pattern p = Pattern.compile(regex);
	// Matcher m = p.matcher(mail);
	// return !m.find();
	// }
	//
	// public static boolean illegalWebSite(String mail) {
	// String regex =
	// "^(http|https)://(([a-zA-z0-9]|-){1,}\\.){1,}[a-zA-z0-9]{1,}-*";
	// Pattern p = Pattern.compile(regex);
	// Matcher m = p.matcher(mail);
	// return !m.find();
	// }
	//
	// public static boolean illegalTelephone(String text) {
	// if (text.length() <= 20 && text.charAt(0) != '-' &&
	// text.charAt(text.length() - 1) != '-') {
	// for (int i = 0; i < text.length(); i++) {
	// int charToInt = text.charAt(i);
	// if ((charToInt < 48 && charToInt != 45) || charToInt > 57) {
	// return true;
	// }
	// }
	// return false;
	// }
	// return true;
	// }
	//
	// public static boolean illegalNum(String text) {
	// Pattern pattern = Pattern.compile("[0-9]*");
	// Matcher numbericMatcher = pattern.matcher(text);
	// return !numbericMatcher.matches();
	// }
	//
	// /**
	// * @param dateStr
	// * yyyy-MM-dd
	// * @return
	// */
	// @SuppressWarnings("unused")
	// private String getPatternStr(String dateStr) {
	// String ereg = "";
	// int year = Integer.parseInt(dateStr.substring(0, 4));
	// if (!(year % 4 == 0 && !(year % 100 == 0) || year % 400 == 0)) {
	// ereg =
	// "([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-
	// (0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8])))";
	// }
	// return ereg;
	// }
	//
	// public static boolean notLettersOrNum(String text) {
	// if (!text.matches("^[A-Za-z0-9]+$")) {
	// return true;
	// }
	// return false;
	// }
	//
	// public static boolean endDateIsSameorBigForStartDate(Date endDate, Date
	// startDate) {
	// if (endDate.equals(startDate) || endDate.after(startDate)) {
	// return true;
	// }
	// return false;
	// }
	//
	// public static boolean illegalNumeric(String str) {
	//
	// if (str.charAt(0) == '.' || str.charAt(str.length() - 1) == '.' ||
	// (str.charAt(0) == '-' || str.charAt(0) == '+') && str.charAt(1) == '.') {
	// return true;
	// } else {
	// try {
	// Double.parseDouble(str);
	// return false;
	// } catch (NumberFormatException ex) {
	// return true;
	// }
	// }
	// }
	//
	// public static boolean illegalNumericRange(String str, double min, double
	// max) {
	// try {
	// double value = Double.parseDouble(str);
	// return NumbericUtil.compare(value, min) < 0 ||
	// NumbericUtil.compare(value, max) > 0;
	// } catch (NumberFormatException ex) {
	// return true;
	// }
	//
	// }

	// public static boolean illegalDate(String strDate) {
	// if (!emptyString(strDate)) {
	// return false;
	// }
	// if (illegalLength(strDate)) {
	// return true;
	// }
	// Date date = null;
	// date = CalendarUtil.parseDate(strDate, "yyyy/MM/dd");
	// if (null != date && getPrevFourString(strDate, "/") < 1900) {
	// return true;
	// }
	// if (date == null) {
	// date = CalendarUtil.parseDate(strDate, "yyyy-MM-dd");
	// if (null != date && getPrevFourString(strDate, "-") < 1900) {
	// return true;
	// }
	// }
	// return date == null;
	// }

	// 验证日期的长度
	// public static boolean illegalLength(String strDate) {
	// if (strDate.indexOf("-") != -1
	// && (strDate.substring(strDate.lastIndexOf("-") + 1).length() > 2 ||
	// strDate.substring(strDate.indexOf("-") + 1,
	// strDate.lastIndexOf("-")).length() > 2)) {
	// return true;
	// } else if (strDate.indexOf("/") != -1
	// && (strDate.substring(strDate.lastIndexOf("/") + 1).length() > 2 ||
	// strDate.substring(strDate.indexOf("/") + 1,
	// strDate.lastIndexOf("/")).length() > 2)) {
	// return true;
	// } else {
	// return false;
	// }
	// }

	// 对年份的验证
	// public static boolean illegaYear(String strYear) {
	// if (!emptyString(strYear)) {
	// return false;
	// }
	// Date year = null;
	// year = DateUtils.parseDate(strYear, "yyyy");
	// if (null != year
	// && (Integer.parseInt(strYear) < 1000 || Integer
	// .parseInt(strYear) > 9999)) {
	// return true;
	// }
	// return false;
	// }

	// public static boolean illegalBoolean(String text) {
	// if ("是".equals(text) || "否".equals(text)) {
	// return false;
	// } else {
	// return true;
	// }
	// }
	//
	// public static int getPrevFourString(String strDate, String style) {
	// String str = strDate.substring(0, strDate.indexOf(style, 1));
	// int result = Integer.parseInt(str);
	// return result;
	// }
	//
	// public static boolean illegalUserName(String text) {
	// Pattern p = Pattern.compile("^[A-Za-z0-9_]+$");
	// Matcher m = p.matcher(text);
	// return !m.find();
	// }
	//
	// /**
	// * 验证是否输入特殊字符(数字,字母,中文字符)
	// */
	// public static boolean illegalExculdeParticalChar(String text) {
	// return !text.matches("^[A-Za-z0-9\u4E00-\u9FA5]+$");
	// }
	//
	// /**
	// * 验证是否输入特殊字符(数字,字母,中文字符,空格,点)
	// */
	// public static boolean illegalExculdeParticalChar2(String text) {
	// return !text.matches("^[A-Za-z0-9\u4E00-\u9FA5\\s\\.]+$");
	// }
	//
	// /**
	// * 验证英文名
	// */
	// public static boolean illegalEnglishName(String text) {
	// // fateson edit from formValidate.js
	// return !text.matches("^[A-Za-z]+$");
	// }
	//
	// /**
	// * 正整数
	// *
	// * @param text
	// * @return
	// */
	// public static boolean illegalNumberZZ(String text) {
	// Pattern pattern = Pattern.compile("[^(0|\\-|\\s)*][0-9]*$");
	// Matcher numbericMatcher = pattern.matcher(text);
	// return !numbericMatcher.matches();
	//
	// }
	//
	// /**
	// * 只能输入正数
	// *
	// * @param text
	// * @return
	// */
	// public static boolean illegalNumberZS(String text) {
	// if (text.startsWith("0"))
	// return false;
	// Pattern pattern = Pattern.compile("[0-9]+(.[0-9]+)?");
	// Matcher numbericMatcher = pattern.matcher(text);
	// return !numbericMatcher.matches();
	//
	// }
	//
	// /**
	// * 校验带有1-2位小数的正实数
	// *
	// * @param text
	// * @return
	// */
	// public static boolean illegalPointNumber(String text) {
	// if (text.startsWith("0"))
	// return false;
	// Pattern pattern = Pattern.compile("^[0-9]+(.[0-9]{1,2})?$");
	// Matcher numbericMatcher = pattern.matcher(text);
	// return !numbericMatcher.matches();
	// }
	//
	// public static boolean illegalInteger(String str) {
	// Pattern pattern = Pattern.compile("[0-9]*");
	// Matcher numbericMatcher = pattern.matcher(str);
	// if (!numbericMatcher.matches()) {
	// return true;
	// } else {
	// try {
	// Integer.parseInt(str);
	// return false;
	// } catch (NumberFormatException ex) {
	// return true;
	// }
	// }
	// }
	//
	// /**
	// * 校验带有4位小数的正实数
	// *
	// * @param text
	// * @return
	// */
	// public static boolean illegalPointNumber4(String text) {
	// if (text.startsWith("0"))
	// return false;
	// Pattern pattern = Pattern.compile("^[0-9]+(.[0-9]{1,4})?$");
	// Matcher numbericMatcher = pattern.matcher(text);
	// return !numbericMatcher.matches();
	// }
	//
	// public static boolean dateIsnotNull(Date date) {
	// if (null == date) {
	// return false;
	// }
	// return true;
	// }

}
