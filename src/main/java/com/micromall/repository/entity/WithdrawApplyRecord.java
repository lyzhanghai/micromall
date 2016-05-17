package com.micromall.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.micromall.repository.entity.common.WithdrawChannels;
import com.micromall.repository.entity.common.WithdrawStatus;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhangzx on 16/3/23.
 * 提现申请记录
 */
public class WithdrawApplyRecord extends IdEntity {

	// 所属用户id
	@JsonIgnore
	private Integer    uid;
	// 提现金额
	private BigDecimal amount;
	/**
	 * 提现渠道 @{@link WithdrawChannels}
	 */
	@JsonIgnore
	private String     channel;
	/**
	 * 提现状态（待审核-->(审核通过 or 审核不通过)-->提现成功）{@link WithdrawStatus}
	 */
	private Integer    status;
	// 审核日志（审核失败会记录失败原因）
	private String     auditlog;
	// 申请提现时间
	private Date       applyTime;
	// 审核时间
	@JsonIgnore
	private Date       auditTime;
	// 提现完成时间
	@JsonIgnore
	private Date       completeTime;

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getAuditlog() {
		return auditlog;
	}

	public void setAuditlog(String auditlog) {
		this.auditlog = auditlog;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public Date getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}
}
