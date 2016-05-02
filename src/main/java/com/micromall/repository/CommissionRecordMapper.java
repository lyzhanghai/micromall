package com.micromall.repository;

import com.micromall.entity.CommissionRecord;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface CommissionRecordMapper extends BaseMapper<CommissionRecord> {

	@Select("SELECT sum(commission_amount) FROM commission_record WHERE uid = #{uid}")
	@ResultType(BigDecimal.class)
	BigDecimal withdrawStat(int uid);
}