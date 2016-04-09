package com.micromall.repository;

import com.micromall.entity.Coupon;
import com.micromall.utils.Condition;
import com.sun.tools.javac.util.List;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponMapper extends BaseMapper<Coupon> {

	@Deprecated
	int deleteByPrimaryKey(Integer id);

	@Deprecated
	int deleteByWhereClause(Condition condition);

	@Deprecated
	List<Coupon> selectPageByWhereClause(Condition condition, RowBounds bounds);

}