package com.micromall.repository;

import com.micromall.entity.Message;
import com.micromall.utils.Condition;
import com.sun.tools.javac.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageMapper extends BaseMapper<Message> {

	//	int insert(T record);

	@Deprecated
	int updateByPrimaryKey(Message record);

	@Deprecated
	int deleteByPrimaryKey(Object id);

	@Deprecated
	int deleteByWhereClause(Condition condition);

	//	T selectByPrimaryKey(Object id);

	@Deprecated
	Message selectOneByWhereClause(Condition condition);

	@Deprecated
	List<Message> selectMultiByWhereClause(Condition condition);

	//	List<T> selectPageByWhereClause(Criteria criteria, RowBounds bounds);

}