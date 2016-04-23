package com.micromall.repository;

import com.github.pagehelper.Page;
import com.micromall.entity.Category;
import com.micromall.utils.Condition;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryMapper extends BaseMapper<Category> {

	@Deprecated
	int deleteByWhereClause(Condition condition);

	@Deprecated
	Page<Category> selectPageByWhereClause(Condition condition, RowBounds bounds);

}