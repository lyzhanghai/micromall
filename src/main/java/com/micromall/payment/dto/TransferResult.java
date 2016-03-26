package com.micromall.payment.dto;

public class TransferResult extends BaseResult {

	//实际退款金额
	private Long amount;

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}


}
