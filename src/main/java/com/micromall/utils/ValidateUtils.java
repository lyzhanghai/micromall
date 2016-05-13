package com.micromall.utils;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtils {

	private static final Pattern POSTCODE            = Pattern.compile("^[1-9]\\d{5}$");
	private static final Pattern MOBILE_PHONE_NUMBER = Pattern.compile("^1[34578]\\d{9}$");

	private static final Pattern             BIRTHDAY          = Pattern.compile(
			"^([1-9][0-9]{0,3}-(((0?[13578]|1[02])-(0?[1-9]|([12][0-9]|3[01])))|((0?[469]|11)-(0?[1-9]|" + "([12][0-9]|30)))" + "|((0?2)-"
					+ "(0?[1-9]|([12][0-9])))))$");
	private static final Map<String, String> IDCARNO_AREA_CODE = Maps.newHashMap();

	static {
		IDCARNO_AREA_CODE.put("11", "北京");
		IDCARNO_AREA_CODE.put("12", "天津");
		IDCARNO_AREA_CODE.put("13", "河北");
		IDCARNO_AREA_CODE.put("14", "山西");
		IDCARNO_AREA_CODE.put("15", "内蒙古");
		IDCARNO_AREA_CODE.put("21", "辽宁");
		IDCARNO_AREA_CODE.put("22", "吉林");
		IDCARNO_AREA_CODE.put("23", "黑龙江");
		IDCARNO_AREA_CODE.put("31", "上海");
		IDCARNO_AREA_CODE.put("32", "江苏");
		IDCARNO_AREA_CODE.put("33", "浙江");
		IDCARNO_AREA_CODE.put("34", "安徽");
		IDCARNO_AREA_CODE.put("35", "福建");
		IDCARNO_AREA_CODE.put("36", "江西");
		IDCARNO_AREA_CODE.put("37", "山东");
		IDCARNO_AREA_CODE.put("41", "河南");
		IDCARNO_AREA_CODE.put("42", "湖北");
		IDCARNO_AREA_CODE.put("43", "湖南");
		IDCARNO_AREA_CODE.put("44", "广东");
		IDCARNO_AREA_CODE.put("45", "广西");
		IDCARNO_AREA_CODE.put("46", "海南");
		IDCARNO_AREA_CODE.put("50", "重庆");
		IDCARNO_AREA_CODE.put("51", "四川");
		IDCARNO_AREA_CODE.put("52", "贵州");
		IDCARNO_AREA_CODE.put("53", "云南");
		IDCARNO_AREA_CODE.put("54", "西藏");
		IDCARNO_AREA_CODE.put("61", "陕西");
		IDCARNO_AREA_CODE.put("62", "甘肃");
		IDCARNO_AREA_CODE.put("63", "青海");
		IDCARNO_AREA_CODE.put("64", "宁夏");
		IDCARNO_AREA_CODE.put("65", "新疆");
		IDCARNO_AREA_CODE.put("71", "台湾");
		IDCARNO_AREA_CODE.put("81", "香港");
		IDCARNO_AREA_CODE.put("82", "澳门");
		IDCARNO_AREA_CODE.put("91", "国外");
	}

	/**
	 * 邮政编码验证
	 *
	 * @param postcode
	 * @return
	 */
	public static boolean illegalPostcode(String postcode) {
		return !POSTCODE.matcher(postcode).matches();
	}

	/**
	 * 字符串长度验证
	 *
	 * @param min
	 * @param max
	 * @param text
	 * @return
	 */
	public static boolean illegalTextLength(int min, int max, String text) {
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
	 * 手机号码验证
	 *
	 * @param text
	 * @return
	 */
	public static boolean illegalMobilePhoneNumber(String text) {
		return !MOBILE_PHONE_NUMBER.matcher(text).matches();
	}

	/**
	 * 出生日期验证
	 *
	 * @param birthday
	 * @return
	 */
	public static boolean illegalBirthday(String birthday) {
		return !BIRTHDAY.matcher(birthday).matches();
	}

	// ----------------------------------------------------------------------------------------------------------------------------
	// -----------身份证号校验-------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------------------

	private static boolean _idCarNo_checkNumeric(String idCarNo) {
		char a1 = idCarNo.charAt(idCarNo.length() - 1);
		if (a1 == 'X') {
			return !_idCarNo_illegalNum(idCarNo.substring(0, 17));
		} else {
			return !_idCarNo_illegalNum(idCarNo);
		}
	}

	private static boolean _idCarNo_illegalNum(String text) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher numbericMatcher = pattern.matcher(text);
		return !numbericMatcher.matches();
	}

	private static boolean _idCarNo_checkDate(String dateText) {
		int year = Integer.parseInt(dateText.substring(0, 4));
		if (year % 4 == 0 && !(year % 100 == 0) || year % 400 == 0) {
			String month = dateText.substring(5, 7);
			int day = Integer.parseInt(dateText.substring(8, 10));
			if ("02".equals(month)) {
				return day <= 29;
			} else if ("01".equals(month) || "03".equals(month) || "05".equals(month) || "07".equals(month) || "08".equals(month) || "10"
					.equals(month) || "12".equals(month)) {
				return day <= 31;
			} else if ("04".equals(month) || "06".equals(month) || "09".equals(month) || "11".equals(month)) {
				return day <= 30;
			}
			return false;
		} else {
			String ereg = _idCarNo_getPattern(dateText);
			Pattern pattern = Pattern.compile(ereg);
			Matcher matcher = pattern.matcher(dateText);
			return matcher.matches();
		}
	}

	private static String _idCarNo_getPattern(String dateStr) {
		String ereg = "";
		int year = Integer.parseInt(dateStr.substring(0, 4));
		if (!(year % 4 == 0 && !(year % 100 == 0) || year % 400 == 0)) {
			ereg = "([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|("
					+ "(0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8])))";
		}
		return ereg;
	}

	/**
	 * 获得身份证最后一位识别码
	 */
	public static String _idCarNo_getLastNum(String idCarNo) {
		String[] ValCodeArr = {"1", "0", "x", "9", "8", "7", "6", "5", "4", "3", "2"};
		String[] Wi = {"7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2"};
		String Ai = "";

		if (idCarNo.length() == 18) {
			Ai = idCarNo.substring(0, 17);
		} else if (idCarNo.length() == 15) {
			Ai = idCarNo.substring(0, 6) + "19" + idCarNo.substring(6, 15);
		}
		int TotalmulAiWi = 0;
		for (int i = 0; i < 17; i++) {
			TotalmulAiWi = TotalmulAiWi + Integer.parseInt(String.valueOf(Ai.charAt(i))) * Integer.parseInt(Wi[i]);
		}
		return ValCodeArr[TotalmulAiWi % 11];
	}

	public static boolean illegalIdCarNo(String idCarNo) {
		// 验证长度
		if (idCarNo.length() != 15 && idCarNo.length() != 18) {
			return true;
		}
		// 验证数字
		if (!_idCarNo_checkNumeric(idCarNo)) {
			return true;
		}
		// 验证地区
		String cantonCode = idCarNo.substring(0, 2);
		if (!IDCARNO_AREA_CODE.containsKey(cantonCode)) {
			return true;
		}
		// 验证出生日期
		String dateText = "";
		if (idCarNo.length() == 15) {
			dateText = "19" + idCarNo.substring(6, 8) + "-" + idCarNo.substring(8, 10) + "-" + idCarNo.substring(10, 12);
		}
		if (idCarNo.length() == 18) {
			dateText = idCarNo.substring(6, 10) + "-" + idCarNo.substring(10, 12) + "-" + idCarNo.substring(12, 14);
		}
		if (!_idCarNo_checkDate(dateText)) {
			return true;
		}
		// 18位身份证需要 验证最后一位数字码
		if (idCarNo.length() == 18 && !_idCarNo_getLastNum(idCarNo).equalsIgnoreCase(idCarNo.substring(idCarNo.length() - 1, idCarNo.length()))) {
			return true;
		}
		return false;
	}

	// -----------身份证号校验 End-------------------------------------------------------------------------------------------------------

	/**
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 */

	// private static final Pattern PHONE_1  = Pattern.compile("^(\\+\\d+)?1[34578]\\d{9}$");
	// private static final Pattern PHONE_2  = Pattern.compile("^((\\+86)?\\d{3,4}-?)?\\d{7,8}$");
	//	private static final Pattern DECIMALS        = Pattern.compile("(-?\\d+)(\\.\\d+)?");
	//	private static final Pattern POSITIVE_NUMBER = Pattern.compile("^(0|([1-9](\\d+)*))$");
	//	private static final Pattern SIMPLE_DATE     = Pattern.compile(
	//			"^([1-9][0-9]{0,3}-(((0?[13578]|1[02])-(0?[1-9]|([12][0-9]|3[01])))|(" + "(0?[469]|11)-"
	//					+ "(0?[1-9]|([12][0-9]|30)))|((0?2)-(0?[1-9]|([12][0-9])))))$");
	//	/**
	//	 * <pre>
	//	 * 正负浮点数验证，支持：-1，1，1.0，-1.0
	//	 *
	//	 * @param text
	//	 * @return boolean
	//	 * @date 2015年6月15日 下午4:21:01
	//	 */
	//	public static boolean illegalDecimals(String text) {
	//		return !DECIMALS.matcher(text).matches();
	//	}
	//
	//	/**
	//	 * <pre>
	//	 * 正整数
	//	 *
	//	 * @param text
	//	 * @return boolean
	//	 * @date 2015年6月15日 下午4:57:57
	//	 */
	//	public static boolean illegalPositiveNumber(String text) {
	//		return !POSITIVE_NUMBER.matcher(text).matches();
	//	}
	//
	//	/**
	//	 * 日期（年-月-日）
	//	 *
	//	 * @param text
	//	 * @return
	//	 */
	//	public static boolean illegalSimpleDate(String text) {
	//		return !SIMPLE_DATE.matcher(text).matches();
	//	}

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
