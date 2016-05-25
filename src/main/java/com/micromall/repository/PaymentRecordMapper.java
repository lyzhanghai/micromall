package com.micromall.repository;

import com.github.pagehelper.Page;
import com.micromall.repository.entity.PaymentRecord;
import com.micromall.utils.Condition;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRecordMapper extends BaseMapper<PaymentRecord> {

	@Deprecated
	int deleteByPrimaryKey(Object id);

	@Deprecated
	int deleteByWhereClause(Condition condition);

	@Deprecated
	PaymentRecord selectByPrimaryKey(Object id);

	@Deprecated
	Page<PaymentRecord> selectPageByWhereClause(Condition condition, RowBounds bounds);

	@Deprecated
	int countByWhereClause(Condition condition);
}