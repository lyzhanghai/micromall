package com.micromall.repository;

import com.micromall.entity.CashRecord;
import com.micromall.utils.Condition;
import org.springframework.stereotype.Repository;

@Repository
public interface CashRecordMapper extends BaseMapper<CashRecord> {

	@Deprecated
	int updateByPrimaryKey(CashRecord record);

	@Deprecated
	int deleteByPrimaryKey(Integer id);

	@Deprecated
	int deleteByWhereClause(Condition condition);
}