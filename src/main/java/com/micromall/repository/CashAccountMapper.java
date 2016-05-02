package com.micromall.repository;

import com.micromall.repository.entity.CashAccount;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;

@Repository
public interface CashAccountMapper extends BaseMapper<CashAccount> {

	@Update("UPDATE cash_account set update_time=#{updateTime}, commission=(commission - #{amount}) where uid=#{uid} and commission >= #{amount}")
	int decrementCommission(@Param("uid") int uid, @Param("amount") BigDecimal amount, @Param("updateTime") Date updateTime);

	@Update("UPDATE cash_account set update_time=#{updateTime}, commission=(commission + #{amount}) where uid=#{uid}")
	int incrementCommission(@Param("uid") int uid, @Param("amount") BigDecimal amount, @Param("updateTime") Date updateTime);

	@Update("UPDATE cash_account set update_time=#{updateTime}, balance=(balance - #{amount}) where uid=#{uid} and balance >= #{amount}")
	int decrementBalance(@Param("uid") int uid, @Param("amount") BigDecimal amount, @Param("updateTime") Date updateTime);

	@Update("UPDATE cash_account set update_time=#{updateTime}, balance=(balance + #{amount}) where uid=#{uid}")
	int incrementBalance(@Param("uid") int uid, @Param("amount") BigDecimal amount, @Param("updateTime") Date updateTime);
}