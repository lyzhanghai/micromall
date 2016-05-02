package com.micromall.repository;

import com.micromall.repository.entity.ShippingAddress;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingAddressMapper extends BaseMapper<ShippingAddress> {

	@Update("update shipping_address set defaul='0' where uid = #{uid}")
	int cleanDefaulAddress(Integer uid);
}