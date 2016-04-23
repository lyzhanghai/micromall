package com.micromall.repository;

import com.github.pagehelper.Page;
import com.micromall.entity.ShippingAddress;
import com.micromall.utils.Condition;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingAddressMapper extends BaseMapper<ShippingAddress> {

	//	int insert(T record);

	//	int updateByPrimaryKey(T record);

	//	int deleteByPrimaryKey(Object id);

	//	int deleteByWhereClause(Condition condition);

	//	T selectByPrimaryKey(Object id);

	//	T selectOneByWhereClause(Criteria criteria);

	//	List<T> selectMultiByWhereClause(Criteria criteria);

	@Deprecated
	Page<ShippingAddress> selectPageByWhereClause(Condition condition, RowBounds bounds);

	@Update("update shipping_address set defaul='0' where uid = #{uid}")
	int cleanDefaulAddress(Integer uid);
}