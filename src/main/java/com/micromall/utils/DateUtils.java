package com.micromall.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;


public class DateUtils {

	public static final  String                                      YYYY_MM_DD          = "yyyy-MM-dd";
	public static final  String                                      HH_MM_SS            = "HH:mm:ss";
	public static final  String                                      YYYY_MM_DD_HH_MM    = "yyyy-MM-dd HH-mm";
	public static final  String                                      YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final  String                                      YYYY_MM_DD_HH_MM2   = "yyyy-MM-dd HH:mm";
	public static final  String                                      YYYYMMDD            = "yyyyMMdd";
	public static final  String                                      HHMMSS              = "HHmmss";
	public static final  String                                      YYYYMMDDHHMM        = "yyyyMMddHHmm";
	public static final  String                                      YYYYMMDDHHMMSS      = "yyyyMMddHHmmss";
	public static final  String                                      DEFAULT_MONTH       = "MONTH";
	public static final  String                                      DEFAULT_YEAR        = "YEAR";
	public static final  String                                      DEFAULT_DATE        = "DAY";
	private static final Logger                                      logger              = LoggerFactory.getLogger(DateUtils.class);
	private static       ConcurrentHashMap<String, SimpleDateFormat> dataFormats         = new ConcurrentHashMap<String, SimpleDateFormat>();


	private static SimpleDateFormat _DataFormat(String format) {
		if (!dataFormats.containsKey(format.intern())) {
			dataFormats.putIfAbsent(format.intern(), new SimpleDateFormat(format));
		}
		return dataFormats.get(format.intern());
	}

	public static String format(Date date, String format) {
		return _DataFormat(format).format(date);
	}

	public static Date parseDate(String date, String format) {
		if (date == null) {
			logger.warn("转换日期失败：format：{}，date：{}", format, date);
			return null;
		}
		try {

			return _DataFormat(format).parse(date);
		} catch (Exception e) {
			logger.error("转换日期出错：format：{}，date：{}，error：", format, date, e);
		}
		return null;
	}

}