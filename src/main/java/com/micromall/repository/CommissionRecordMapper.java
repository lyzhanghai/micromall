package com.micromall.repository;

import com.micromall.entity.cash.CommissionRecord;
import com.micromall.utils.Condition;
import com.sun.tools.javac.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface CommissionRecordMapper extends BaseMapper<CommissionRecord> {

	@Deprecated
	int updateByPrimaryKey(CommissionRecord record);

	@Deprecated
	int deleteByPrimaryKey(Integer id);

	@Deprecated
	int deleteByWhereClause(Condition condition);

	@Deprecated
	List<CommissionRecord> selectAllByWhereClause(Condition condition);

}