package com.micromall.entity;

import com.micromall.entity.ext.RefundStatus;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhangzx on 16/3/23.
 * 退款申请记录
 */
public class RefundApplyRecord extends IdEntity {

	// 所属用户id
	private Integer    uid;
	// 订单号
	private String     orderNo;
	// 退款金额
	private BigDecimal amount;
	/**
	 * 退款状态（待审核-->(同意退款 or 不同意退款)-->已退款）{@link RefundStatus}
	 */
	private Integer    status;
	// 退款原因
	private String     reason;
	// 审核日志（审核失败会记录失败原因）
	private String     auditlog;
	// 申请时间
	private Date       applyTime;
	// 审核时间
	private Date       auditTime;
	// 退款完成时间
	private Date       completeTime;

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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
