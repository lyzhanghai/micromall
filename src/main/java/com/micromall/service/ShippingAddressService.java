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
		mapper.insert(address);
	}

	@Transactional
	public boolean updateAddress(ShippingAddress address) {
		if (address.isDefaul() != null && address.isDefaul()) {
			ShippingAddress _address = this.getDefaultAddress(address.getUid());
			if (_address != null && !_address.getId().equals(address.getId())) {
				ShippingAddress update = new ShippingAddress();
				update.setId(_address.getId());
				update.setDefaul(false);
				mapper.updateByPrimaryKey(update);
			}
		}
		return mapper.updateByPrimaryKey(address) > 0;
	}

	@Transactional
	public boolean deleteAddress(int uid, int addressId) {
		return mapper.deleteByWhereClause(Condition.Criteria.create().andEqualTo("uid", uid).andEqualTo("id", addressId).build()) > 0;
	}

	@Transactional(readOnly = true)
	public ShippingAddress getAddress(int uid, int addressId) {
		return mapper.selectOneByWhereClause(Condition.Criteria.create().andEqualTo("uid", uid).andEqualTo("id", addressId).build());
	}

	@Transactional(readOnly = true)
	public ShippingAddress getDefaultAddress(int uid) {
		return mapper.selectOneByWhereClause(Condition.Criteria.create().andEqualTo("uid", uid).andEqualTo("defaul", true).build());
	}
}
