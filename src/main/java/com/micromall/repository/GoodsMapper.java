package com.micromall.repository;

import com.micromall.entity.Goods;
import com.micromall.utils.Condition;
import com.sun.tools.javac.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsMapper extends BaseMapper<Goods> {

	@Deprecated
	int deleteByWhereClause(Condition condition);

	@Deprecated
	List<Goods> selectAllByWhereClause(Condition condition);

}