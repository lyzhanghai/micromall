package com.micromall.repository;

import com.github.pagehelper.Page;
import com.micromall.repository.entity.CartGoods;
import com.micromall.utils.Condition;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartGoodsMapper extends BaseMapper<CartGoods> {

	List<CartGoods> selectCartGoods(int uid);

	@Deprecated
	int updateByPrimaryKey(CartGoods record);

	@Deprecated
	int deleteByPrimaryKey(Object id);

	@Deprecated
	CartGoods selectByPrimaryKey(Object id);

	@Deprecated
	CartGoods selectOneByWhereClause(Condition condition);

	@Deprecated
	List<CartGoods> selectMultiByWhereClause(Condition condition);

	@Deprecated
	Page<CartGoods> selectPageByWhereClause(Condition condition, RowBounds bounds);

	@Deprecated
	int countByWhereClause(Condition condition);

}