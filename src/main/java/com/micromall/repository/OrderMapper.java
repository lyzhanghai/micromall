package com.micromall.repository;

import com.github.pagehelper.Page;
import com.micromall.repository.entity.Order;
import com.micromall.service.vo.ListViewOrder;
import com.micromall.utils.Condition;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OrderMapper extends BaseMapper<Order> {

	List<Map<String, Object>> distributionOrderAmountSum(@Param("uid") int uid, @Param("level") int level);

	List<ListViewOrder> selectListViewPageByWhereClause(Condition build, RowBounds rowBounds);

	Order selectOneByWhereClause(Condition condition);

	@Deprecated
	int deleteByPrimaryKey(Object id);

	@Deprecated
	int deleteByWhereClause(Condition condition);

	@Deprecated
	Order selectByPrimaryKey(Object id);

	@Deprecated
	List<Order> selectMultiByWhereClause(Condition condition);

	@Deprecated
	Page<Order> selectPageByWhereClause(Condition condition, RowBounds bounds);

	@Deprecated
	int countByWhereClause(Condition condition);

}