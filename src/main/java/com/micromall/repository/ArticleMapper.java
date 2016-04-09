package com.micromall.repository;

import com.micromall.entity.Article;
import com.micromall.utils.Condition;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleMapper extends BaseMapper<Article> {

	@Deprecated
	int deleteByWhereClause(Condition condition);

}