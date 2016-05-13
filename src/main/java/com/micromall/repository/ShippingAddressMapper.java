package com.micromall.repository;

import com.github.pagehelper.Page;
import com.micromall.repository.entity.ShippingAddress;
import com.micromall.utils.Condition;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingAddressMapper extends BaseMapper<ShippingAddress> {

	@Update("update shipping_address set defaul='0' where uid = #{uid}")
	int cleanDefaulAddress(Integer uid);

	int updateByPrimaryKeyUid(ShippingAddress record);

	@Deprecated
	int updateByPrimaryKey(ShippingAddress record);

	@Deprecated
	int deleteByPrimaryKey(Object id);

	@Deprecated
	ShippingAddress selectByPrimaryKey(Object id);

	@Deprecated
	Page<ShippingAddress> selectPageByWhereClause(Condition condition, RowBounds bounds);
}