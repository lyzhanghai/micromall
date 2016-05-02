package com.micromall.payment.dto;

import com.micromall.payment.dto.ext.PayChannel;
import com.micromall.payment.dto.ext.PayMethod;
import com.micromall.payment.dto.ext.PlatformType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 支付请求
 */
public class PaymentRequest {

	// 支付渠道
	private PayChannel          payChannel;
	// 支付方式
	private PayMethod           payMethod;
	// 支付平台
	private PlatformType        platformType;
	// 订单号
	private String              orderNo;
	// 金额
	private BigDecimal          amount;
	// 异步通知地址
	private String              backNotifyUrl;
	// 前端通知地址
	private String              frontNotifyUrl;
	// 扩展字段
	private Map<String, String> extendParams;
	// 商品名称
	private String              goodsName;
	// 附言
	private String              purpose;
	// 支付IP
	private String              payIp;
	// 订单关闭时间
	private Date                closeTime;

	public PayChannel getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(PayChannel payChannel) {
		this.payChannel = payChannel;
	}

	public PayMethod getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(PayMethod payMethod) {
		this.payMethod = payMethod;
	}

	public PlatformType getPlatformType() {
		return platformType;
	}

	public void setPlatformType(PlatformType platformType) {
		this.platformType = platformType;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getBackNotifyUrl() {
		return backNotifyUrl;
	}

	public void setBackNotifyUrl(String backNotifyUrl) {
		this.backNotifyUrl = backNotifyUrl;
	}

	public Map<String, String> getExtendParams() {
		return extendParams;
	}

	public void setExtendParams(Map<String, String> extendParams) {
		this.extendParams = extendParams;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getFrontNotifyUrl() {
		return frontNotifyUrl;
	}

	public void setFrontNotifyUrl(String frontNotifyUrl) {
		this.frontNotifyUrl = frontNotifyUrl;
	}

	public String getPayIp() {
		return payIp;
	}

	public void setPayIp(String payIp) {
		this.payIp = payIp;
	}

	public Date getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}
}
