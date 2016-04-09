package com.micromall.repository;

import com.micromall.entity.CartGoods;
import com.micromall.entity.FavoriteGoods;
import com.micromall.utils.Condition;
import com.sun.tools.javac.util.List;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteGoodsMapper extends BaseMapper<FavoriteGoods> {

	@Deprecated
	int updateByPrimaryKey(CartGoods record);

	@Deprecated
	int deleteByPrimaryKey(Integer id);

	@Deprecated
	FavoriteGoods selectByPrimaryKey(Integer id);

	@Deprecated
	List<FavoriteGoods> selectPageByWhereClause(Condition condition, RowBounds bounds);
}