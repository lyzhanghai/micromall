package com.micromall.repository;

import com.github.pagehelper.Page;
import com.micromall.entity.CartGoods;
import com.micromall.utils.Condition;
import com.sun.tools.javac.util.List;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

@Repository
public interface CartGoodsMapper extends BaseMapper<CartGoods> {

	@Deprecated
	int updateByPrimaryKey(CartGoods record);

	@Deprecated
	int deleteByPrimaryKey(Integer id);

	@Deprecated
	CartGoods selectByPrimaryKey(Integer id);

	@Deprecated
	Page<CartGoods> selectPageByWhereClause(Condition condition, RowBounds bounds);

	List<CartGoods> listGoods(int uid);

}