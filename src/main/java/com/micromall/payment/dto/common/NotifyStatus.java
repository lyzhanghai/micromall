package com.micromall.payment.dto.common;

public interface NotifyStatus {

	String INIT   = "0";//初始状态
	String BREAK  = "2";//暂停状态
	String MISS   = "3";//通知失败
	String FINISH = "9";//通知完成
}
