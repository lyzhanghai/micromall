package com.micromall.repository;

import com.micromall.entity.OrderGoods;
import com.micromall.utils.Condition;
import com.sun.tools.javac.util.List;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderGoodsMapper extends BaseMapper<OrderGoods> {

	//	int insert(T record);

	@Deprecated
	int updateByPrimaryKey(OrderGoods record);

	@Deprecated
	int deleteByPrimaryKey(Object id);

	@Deprecated
	int deleteByWhereClause(Condition condition);

	@Deprecated
	OrderGoods selectByPrimaryKey(Object id);

	@Deprecated
	OrderGoods selectOneByWhereClause(Condition condition);

	//	List<T> selectMultiByWhereClause(Criteria criteria);

	@Deprecated
	List<OrderGoods> selectPageByWhereClause(Condition condition, RowBounds bounds);

}