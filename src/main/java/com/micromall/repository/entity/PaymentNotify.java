package com.micromall.repository.entity;

import java.util.Date;

public class PaymentNotify extends IdEntity {

	// 订单号
	private String  orderNo;
	// 通知url
	private String  notifyUrl;
	// 通知类型
	private String  notifyType;
	// 通知发送状态
	private String  status;
	// 通知内容
	private String  content;
	// 通知重试次数
	private Integer notifyTimes;
	// 下一次通知时间
	private Date    nextNotifyTime;
	// 准备发送通知时间
	private Date    breakTimeout;
	private Date    createTime;
	private Date    updateTime;
	private int     version;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getNotifyTimes() {
		return notifyTimes;
	}

	public void setNotifyTimes(Integer notifyTimes) {
		this.notifyTimes = notifyTimes;
	}

	public Date getNextNotifyTime() {
		return nextNotifyTime;
	}

	public void setNextNotifyTime(Date nextNotifyTime) {
		this.nextNotifyTime = nextNotifyTime;
	}

	public Date getBreakTimeout() {
		return breakTimeout;
	}

	public void setBreakTimeout(Date breakTimeout) {
		this.breakTimeout = breakTimeout;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
}
