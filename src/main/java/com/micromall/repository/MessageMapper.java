package com.micromall.repository;

import com.micromall.repository.entity.Message;
import com.micromall.utils.Condition;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageMapper extends BaseMapper<Message> {

	@Deprecated
	int updateByPrimaryKey(Message record);

	@Deprecated
	int deleteByPrimaryKey(Object id);

	@Deprecated
	int deleteByWhereClause(Condition condition);

	@Deprecated
	Message selectByPrimaryKey(Object id);

	@Deprecated
	Message selectOneByWhereClause(Condition condition);

	@Deprecated
	List<Message> selectMultiByWhereClause(Condition condition);

	@Deprecated
	int countByWhereClause(Condition condition);

}