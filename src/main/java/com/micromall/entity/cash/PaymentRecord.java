package com.micromall.entity.cash;

import com.micromall.entity.IdEntity;

import java.util.Date;

/**
 * Created by zhangzx on 16/3/23.
 * 支付记录
 */
public class PaymentRecord extends IdEntity {

	// 订单号
	private String insOrderNo;
	private String outOrderNo;
	private Long   amount;
	private String instCode;
	private String channelCode;
	private String ipAddress;
	private String returnOrderNo;
	private String frontNotifyUrl;
	private String backednNotifyUrl;
	private String returnCode;
	private String notifyStatus;
	private String refundStatus;
	private Date   closeTime;
	private String extendsion;
	private String status;

}
