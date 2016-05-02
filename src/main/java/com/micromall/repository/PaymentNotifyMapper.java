package com.micromall.repository;

import com.micromall.repository.entity.PaymentNotify;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentNotifyMapper extends BaseMapper<PaymentNotify> {

	int updateNotifyInfo(PaymentNotify paymentNotify);

}