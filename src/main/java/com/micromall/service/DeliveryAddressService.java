package com.micromall.service;

import com.micromall.entity.DeliveryAddress;
import com.micromall.repository.DeliveryAddressRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhangzx on 16/3/26.
 */
@Service
public class DeliveryAddressService {

	@Resource
	private DeliveryAddressRepository deliveryAddressRepository;

	public List<DeliveryAddress> list(int uid) {
		return null;
	}

	public boolean addAddress(int uid, DeliveryAddress address) {
		return false;
	}

	public boolean updateAddress(int uid, DeliveryAddress address) {
		return false;
	}

	public boolean deleteAddress(int uid, int addressId) {
		return false;
	}

	public DeliveryAddress getAddress(int uid, int addressId) {
		return null;
	}

	public DeliveryAddress getDefaultAddress(int uid) {
		return null;
	}
}
