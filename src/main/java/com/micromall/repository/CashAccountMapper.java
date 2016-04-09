package com.micromall.repository;

import com.micromall.entity.cash.CashAccount;
import com.micromall.utils.Condition;
import com.sun.tools.javac.util.List;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

@Repository
public interface CashAccountMapper extends BaseMapper<CashAccount> {

	@Deprecated
	int deleteByPrimaryKey(Integer id);

	@Deprecated
	int deleteByWhereClause(Condition condition);

	@Deprecated
	List<CashAccount> selectPageByWhereClause(Condition condition, RowBounds bounds);

	@Deprecated
	List<CashAccount> selectAllByWhereClause(Condition condition);

}