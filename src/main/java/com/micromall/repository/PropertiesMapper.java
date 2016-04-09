package com.micromall.repository;

import com.micromall.entity.Properties;
import com.micromall.utils.Condition;
import com.sun.tools.javac.util.List;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertiesMapper extends BaseMapper<Properties> {

	//	int insert(T record);

	//	int updateByPrimaryKey(T record);

	//	int deleteByPrimaryKey(Object id);

	@Deprecated
	int deleteByWhereClause(Condition condition);

	//	T selectByPrimaryKey(Object id);

	@Deprecated
	Properties selectOneByWhereClause(Condition condition);

	//	List<T> selectMultiByWhereClause(Criteria criteria);

	@Deprecated
	List<Properties> selectPageByWhereClause(Condition condition, RowBounds bounds);

}