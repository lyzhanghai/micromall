package com.micromall.repository;

import com.micromall.entity.Category;
import com.micromall.utils.Condition;
import com.sun.tools.javac.util.List;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryMapper extends BaseMapper<Category> {

	@Deprecated
	int deleteByWhereClause(Condition condition);

	@Deprecated
	List<Category> selectPageByWhereClause(Condition condition, RowBounds bounds);

}