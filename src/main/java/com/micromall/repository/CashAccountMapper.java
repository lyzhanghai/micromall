package com.micromall.repository;

import com.github.pagehelper.Page;
import com.micromall.repository.entity.CashAccount;
import com.micromall.utils.Condition;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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

	@Update("UPDATE cash_account set update_time=#{updateTime}, total_sales=(total_sales + #{amount}) where uid=#{uid}")
	int incrementTotalSales(@Param("uid") int uid, @Param("amount") BigDecimal amount, @Param("updateTime") Date updateTime);

	@Deprecated
	int updateByPrimaryKey(CashAccount record);

	@Deprecated
	int deleteByPrimaryKey(Object id);

	@Deprecated
	int deleteByWhereClause(Condition condition);

	@Deprecated
	CashAccount selectOneByWhereClause(Condition condition);

	@Deprecated
	List<CashAccount> selectMultiByWhereClause(Condition condition);

	@Deprecated
	Page<CashAccount> selectPageByWhereClause(Condition condition, RowBounds bounds);

	@Deprecated
	int countByWhereClause(Condition condition);
}