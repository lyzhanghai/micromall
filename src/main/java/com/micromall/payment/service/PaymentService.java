package com.micromall.payment.service;

import com.micromall.payment.dto.*;


public interface PaymentService {

	FundInResult fundIn(FundInRequest request);

	RefundResult refund(RefundRequest request);

	VerityResult verity(VerityRequest request);

	TransferResult transfer(TransferRequest request);

}
