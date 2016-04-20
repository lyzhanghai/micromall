package com.micromall.entity.cash;

import com.micromall.entity.IdEntity;
import com.micromall.entity.ext.WithdrawChannels;
import com.micromall.entity.ext.WithdrawStatus;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhangzx on 16/3/23.
 * 提现记录
 * 提交提现申请：扣除账户内佣金余额
 * 审核失败、提现失败：返回提现金额到账户佣金余额
 */
public class WithdrawRecord extends IdEntity {

	// 所属用户id
	private Integer    uid;
	// 提现金额
	private BigDecimal amount;
	/**
	 * 提现渠道 @{@link WithdrawChannels}
	 */
	private String     channel;
	/**
	 * 提现状态（待审核-->审核通过-->审核失败-->提现中-->提现成功-->提现失败）{@link WithdrawStatus}
	 */
	private Integer    status;
	// 审核日志（审核失败会记录失败原因）
	private String     auditlog;
	// 申请提现时间
	private Date       applyTime;
	// 审核时间
	private Date       auditTime;
	// 提现完成时间，包含提现成功与提现失败
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
