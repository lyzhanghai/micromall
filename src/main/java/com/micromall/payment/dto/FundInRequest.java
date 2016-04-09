package com.micromall.payment.dto;

import java.util.Date;

public class FundInRequest extends BaseRequest {

	// 商品名称
	private String goodsName;
	// 附言
	private String purpose;
	// 前端通知地址
	private String frontNotifyUrl;
	// 订单关闭时间
	private Date   closeTime;

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

	public Date getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}
}
