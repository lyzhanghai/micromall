package com.micromall.repository;

import com.micromall.entity.cash.WithdrawRecord;
import com.micromall.utils.Condition;
import org.springframework.stereotype.Repository;

@Repository
public interface WithdrawRecordMapper extends BaseMapper<WithdrawRecord> {

	//	int insert(T record);

	//	int updateByPrimaryKey(T record);

	@Deprecated
	int deleteByPrimaryKey(Object id);

	@Deprecated
	int deleteByWhereClause(Condition condition);

	//	T selectByPrimaryKey(Object id);

	@Deprecated
	WithdrawRecord selectOneByWhereClause(Condition condition);

	//	List<T> selectMultiByWhereClause(Criteria criteria);

	//	List<T> selectPageByWhereClause(Criteria criteria, RowBounds bounds);

}