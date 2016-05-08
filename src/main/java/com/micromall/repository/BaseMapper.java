package com.micromall.repository;

import com.github.pagehelper.Page;
import com.micromall.utils.Condition;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author zhangzxiang91@gmail.com
 * @date 2016/04/10.
 */
public interface BaseMapper<T> {

	int insert(T record);

	int updateByPrimaryKey(T record);

	// int updateByWhereClause(@Param("record") T record, @Param("condition") Condition condition);

	int deleteByPrimaryKey(Object id);

	int deleteByWhereClause(Condition condition);

	T selectByPrimaryKey(Object id);

	T selectOneByWhereClause(Condition condition);

	List<T> selectMultiByWhereClause(Condition condition);

	Page<T> selectPageByWhereClause(Condition condition, RowBounds bounds);

	int countByWhereClause(Condition condition);

}
