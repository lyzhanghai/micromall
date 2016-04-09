package com.micromall.repository;

import com.micromall.entity.ShippingAddress;
import com.micromall.utils.Condition;
import com.sun.tools.javac.util.List;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingAddressMapper extends BaseMapper<ShippingAddress> {

	//	int insert(T record);

	//	int updateByPrimaryKey(T record);

	//	int deleteByPrimaryKey(Object id);

	//	int deleteByWhereClause(Condition condition);

	//	T selectByPrimaryKey(Object id);

	//	T selectOneByWhereClause(Criteria criteria);

	//	List<T> selectMultiByWhereClause(Criteria criteria);

	@Deprecated
	List<ShippingAddress> selectPageByWhereClause(Condition condition, RowBounds bounds);
}