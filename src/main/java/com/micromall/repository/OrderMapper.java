package com.micromall.repository;

import com.micromall.entity.Goods;
import com.micromall.utils.Condition;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMapper extends BaseMapper<Goods> {

	//	int insert(T record);

	//	int updateByPrimaryKey(T record);

	@Deprecated
	int deleteByPrimaryKey(Object id);

	@Deprecated
	int deleteByWhereClause(Condition condition);

	//	T selectByPrimaryKey(Object id);
	//
	//	T selectOneByWhereClause(Criteria criteria);
	//
	//	List<T> selectMultiByWhereClause(Criteria criteria);
	//
	//	List<T> selectPageByWhereClause(Criteria criteria, RowBounds bounds);

}