package com.micromall.web.controller;

import com.micromall.repository.entity.ShippingAddress;
import com.micromall.service.ShippingAddressService;
import com.micromall.utils.ArgumentValidException;
import com.micromall.utils.ValidateUtils;
import com.micromall.web.resp.ResponseEntity;
import com.micromall.web.security.annotation.Authentication;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by zhangzx on 16/3/28.
 * 收货地址
 */
@RestController
@RequestMapping(value = "/api")
@Authentication
public class ShippingAddressController extends BasisController {

	@Resource
	private ShippingAddressService shippingAddressService;

	/**
	 * 收货地址列表
	 *
	 * @return
	 */
	@RequestMapping(value = "/address/list")
	public ResponseEntity<?> list() {
		return ResponseEntity.Success(shippingAddressService.list(getLoginUser().getUid()));
	}

	/**
	 * 新增收货地址
	 *
	 * @return
	 */
	@RequestMapping(value = "/address/add")
	public ResponseEntity<?> add_address(ShippingAddress address) {
		validateAddress(address);
		address.setUid(getLoginUser().getUid());
		shippingAddressService.addAddress(address);
		return ResponseEntity.Success(address);
	}

	/**
	 * 修改收货地址
	 *
	 * @return
	 */
	@RequestMapping(value = "/address/update")
	public ResponseEntity<?> update_address(ShippingAddress address) {
		validateAddress(address);
		address.setUid(getLoginUser().getUid());
		if (!shippingAddressService.updateAddress(address)) {
			return ResponseEntity.Failure("保存收货地址信息失败");
		}
		return ResponseEntity.Success();
	}

	/**
	 * 删除收货地址
	 *
	 * @param id 地址id
	 * @return
	 */
	@RequestMapping(value = "/address/delete")
	public ResponseEntity<?> delete_address(int id) {
		shippingAddressService.deleteAddress(getLoginUser().getUid(), id);
		return ResponseEntity.Success();
	}

	/**
	 * 获取收货地址
	 *
	 * @param id 地址id
	 * @return
	 */
	@RequestMapping(value = "/address/get")
	public ResponseEntity<?> get_address(int id) {
		return ResponseEntity.Success(shippingAddressService.getAddress(getLoginUser().getUid(), id));
	}

	private void validateAddress(ShippingAddress address) {
		if (StringUtils.isBlank(address.getProvince())) {
			throw new ArgumentValidException("省份不能为空");
		}
		if (StringUtils.isBlank(address.getCity())) {
			throw new ArgumentValidException("城市不能为空");
		}
		/*if (StringUtils.isBlank(address.getCounty())) {
			throw new ArgumentValidException("区县不能为空");
		}*/
		if (StringUtils.isBlank(address.getDetailedAddress())) {
			throw new ArgumentValidException("详细地址不能为空");
		}
		if (StringUtils.isBlank(address.getConsigneeName())) {
			throw new ArgumentValidException("收货人姓名不能为空");
		}
		if (StringUtils.isBlank(address.getConsigneePhone())) {
			throw new ArgumentValidException("收货人电话不能为空");
		}
		if (ValidateUtils.illegalTextLength(1, 15, address.getProvince())) {
			throw new ArgumentValidException("省份名称长度超过限制");
		}
		if (ValidateUtils.illegalTextLength(1, 15, address.getCity())) {
			throw new ArgumentValidException("城市名称长度超过限制");
		}
		if (StringUtils.isNotBlank(address.getCounty()) && ValidateUtils.illegalTextLength(1, 15, address.getCounty())) {
			throw new ArgumentValidException("区县名称长度超过限制");
		}
		if (ValidateUtils.illegalTextLength(1, 80, address.getDetailedAddress())) {
			throw new ArgumentValidException("详细地址长度超过限制");
		}
		if (ValidateUtils.illegalTextLength(1, 15, address.getConsigneeName())) {
			throw new ArgumentValidException("收货人姓名长度超过限制");
		}
		if (ValidateUtils.illegalMobilePhoneNumber(address.getConsigneePhone())) {
			throw new ArgumentValidException("收货人电话输入不正确");
		}
		if (address.getPostcode() != null && ValidateUtils.illegalPostcode(address.getPostcode())) {
			throw new ArgumentValidException("邮政编码输入不正确");
		}
	}
}
