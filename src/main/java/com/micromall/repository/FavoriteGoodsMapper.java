package com.micromall.repository;

import com.github.pagehelper.Page;
import com.micromall.repository.entity.FavoriteGoods;
import com.micromall.utils.Condition;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteGoodsMapper extends BaseMapper<FavoriteGoods> {

	List<FavoriteGoods> selectFavoriteGoods(int uid);

	@Deprecated
	int updateByPrimaryKey(FavoriteGoods record);

	@Deprecated
	int deleteByPrimaryKey(Object id);

	@Deprecated
	FavoriteGoods selectByPrimaryKey(Object id);

	@Deprecated
	FavoriteGoods selectOneByWhereClause(Condition condition);

	@Deprecated
	List<FavoriteGoods> selectMultiByWhereClause(Condition condition);

	@Deprecated
	Page<FavoriteGoods> selectPageByWhereClause(Condition condition, RowBounds bounds);

}