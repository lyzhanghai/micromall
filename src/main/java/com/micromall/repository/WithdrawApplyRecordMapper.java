package com.micromall.repository;

import com.micromall.entity.WithdrawApplyRecord;
import com.micromall.utils.Condition;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface WithdrawApplyRecordMapper extends BaseMapper<WithdrawApplyRecord> {

	BigDecimal withdrawStat(Condition condition);

}