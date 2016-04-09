package com.micromall.repository;

import com.micromall.entity.Member;
import com.micromall.utils.Condition;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberMapper extends BaseMapper<Member> {

	//	int insert(T record);

	//	int updateByPrimaryKey(T record);

	int deleteByPrimaryKey(Object id);

	int deleteByWhereClause(Condition condition);

	//	T selectByPrimaryKey(Object id);

	//	T selectOneByWhereClause(Criteria criteria);

	//	List<T> selectMultiByWhereClause(Criteria criteria);

	//	List<T> selectPageByWhereClause(Criteria criteria, RowBounds bounds);

}