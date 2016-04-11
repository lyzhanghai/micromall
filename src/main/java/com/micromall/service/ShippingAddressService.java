package com.micromall.service;

import com.micromall.entity.ShippingAddress;
import com.micromall.repository.ShippingAddressMapper;
import com.micromall.utils.Condition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhangzx on 16/3/26.
 */
@Service
public class ShippingAddressService {

	@Resource
	private ShippingAddressMapper mapper;

	@Transactional(readOnly = true)
	public List<ShippingAddress> list(int uid) {
		return mapper.selectMultiByWhereClause(Condition.Criteria.create().andEqualTo("uid", uid).build("id desc"));
	}

	@Transactional
	public void addAddress(ShippingAddress address) {
		if (address.isDefaul() != null && address.isDefaul()) {
			mapper.cleanDefaulAddress(address.getUid());
		}
		mapper.insert(address);
	}

	@Transactional
	public boolean updateAddress(ShippingAddress address) {
		if (address.isDefaul() != null && address.isDefaul()) {
			mapper.cleanDefaulAddress(address.getUid());
		}
		return mapper.updateByPrimaryKey(address) > 0;
	}

	@Transactional
	public boolean deleteAddress(int uid, int id) {
		return mapper.deleteByWhereClause(Condition.Criteria.create().andEqualTo("uid", uid).andEqualTo("id", id).build()) > 0;
	}

	@Transactional(readOnly = true)
	public ShippingAddress getAddress(int uid, int id) {
		return mapper.selectOneByWhereClause(Condition.Criteria.create().andEqualTo("uid", uid).andEqualTo("id", id).build());
	}

	@Transactional(readOnly = true)
	public ShippingAddress getDefaultAddress(int uid) {
		return mapper.selectOneByWhereClause(Condition.Criteria.create().andEqualTo("uid", uid).andEqualTo("defaul", true).build());
	}
}
