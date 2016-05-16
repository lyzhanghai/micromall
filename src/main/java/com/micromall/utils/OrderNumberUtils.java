/*
 * Copyright (C), 2014-2015, 杭州小卡科技有限公司
 * Created by ciwei@xiaokakeji.com on 2016/05/15.
 */
package com.micromall.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ciwei@xiaokakeji.com
 * @date 2016/05/15.
 */
public class OrderNumberUtils {

	public static final  SimpleDateFormat SIMPLEDATEFORMATHHMMSS = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	public static final  SimpleDateFormat SIMPLEDATEFORMAT       = new SimpleDateFormat("yyyyMMdd");
	private static final AtomicInteger    COMMONATOMICINTEGER    = new AtomicInteger();
	private static String commonSerializable;

	public static String generateNumber() {
		String serializable = commonSerializable;
		int length = 1;
		if (commonSerializable == null || commonSerializable.length() < 8 || !SIMPLEDATEFORMAT.format(new Date())
		                                                                                      .equals(commonSerializable.substring(0, 8))) {
			serializable = null;
			commonSerializable = serializable;
		}
		if (serializable == null) {
			StringBuilder ssb = new StringBuilder();
			ssb.append(SIMPLEDATEFORMATHHMMSS.format(new Date()));
			int ordersIndex = new Random().nextInt(10000);
			if (ordersIndex < 1000) {
				ordersIndex += 1000;
			}
			ssb.append(ordersIndex);
			serializable = ssb.toString();
		}
		String o = String.valueOf(COMMONATOMICINTEGER.getAndIncrement());
		int remainLength = length - o.length();
		StringBuilder sb = new StringBuilder();
		sb.append(serializable);
		if (remainLength > 0) {
			for (int r = 0; r < remainLength; r++) {
				sb.append("0");
			}
		}
		sb.append(o);
		return sb.toString();
	}

	public static void main(String[] args) {
		while (true) {
			System.err.println(generateNumber());
		}
	}
}
