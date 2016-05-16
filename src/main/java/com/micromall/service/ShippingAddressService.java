package com.micromall.service;

import com.micromall.repository.ShippingAddressMapper;
import com.micromall.repository.entity.ShippingAddress;
import com.micromall.utils.Condition.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangzx on 16/3/26.
 */
@Service
@Transactional
public class ShippingAddressService {

	@Resource
	private ShippingAddressMapper mapper;

	@Transactional(readOnly = true)
	public List<ShippingAddress> list(int uid) {
		return mapper.selectMultiByWhereClause(Criteria.create().andEqualTo("uid", uid).build("defaul desc, id desc"));
	}

	private void _resetDefaultAddress(ShippingAddress address) {
		if (address.isDefaul()) {
			mapper.cleanDefaulAddress(address.getUid());
		} else {
			if (mapper.countByWhereClause(Criteria.create().andEqualTo("uid", address.getUid()).build()) == 0) {
				address.setDefaul(true);
			}
		}
	}

	public void addAddress(ShippingAddress address) {
		_resetDefaultAddress(address);
		address.setId(null);
		address.setCreateTime(new Date());
		address.setUpdateTime(null);
		mapper.insert(address);
	}

	public boolean updateAddress(ShippingAddress address) {
		_resetDefaultAddress(address);
		address.setCreateTime(null);
		address.setUpdateTime(new Date());
		return mapper.updateByPrimaryKeyUid(address) > 0;
	}

	public boolean deleteAddress(int uid, int id) {
		return mapper.deleteByWhereClause(Criteria.create().andEqualTo("uid", uid).andEqualTo("id", id).build()) > 0;
	}

	@Transactional(readOnly = true)
	public ShippingAddress getAddress(int uid, int id) {
		return mapper.selectOneByWhereClause(Criteria.create().andEqualTo("uid", uid).andEqualTo("id", id).build());
	}

	@Transactional(readOnly = true)
	public ShippingAddress getDefaultAddress(int uid) {
		return mapper.selectOneByWhereClause(Criteria.create().andEqualTo("uid", uid).andEqualTo("defaul", true).build());
	}
}
