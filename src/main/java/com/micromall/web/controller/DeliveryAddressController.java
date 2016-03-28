package com.micromall.web.controller;

import com.micromall.entity.DeliveryAddress;
import com.micromall.service.DeliveryAddressService;
import com.micromall.web.extend.Authentication;
import com.micromall.web.extend.UncaughtException;
import com.micromall.web.resp.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by zhangzx on 16/3/28.
 * 收货地址
 */
@Controller
@RequestMapping(value = "/delivery_address")
@Authentication
public class DeliveryAddressController extends BasisController {

	@Resource
	private DeliveryAddressService deliveryAddressService;

	/**
	 * 收货地址列表
	 *
	 * @return
	 */
	@RequestMapping(value = "/list")
	@UncaughtException(msg = "加载收货地址列表失败")
	public ResponseEntity<?> list() {
		return ResponseEntity.ok(deliveryAddressService.list(getLoginUser().getUid()));
	}

	/**
	 * 新增收货地址
	 *
	 * @param province        省份
	 * @param city            城市
	 * @param county          区/县
	 * @param detailedAddress 详细地址
	 * @param consigneeName   收货人姓名
	 * @param consigneePhone  收货人电话
	 * @param defaul          是设为默认地址
	 * @return
	 */
	@UncaughtException(msg = "保存收货地址信息失败")
	@RequestMapping(value = "/add_address")
	public ResponseEntity<?> add_address(String province, String city, String county, String detailedAddress, String consigneeName, String
			consigneePhone, boolean defaul) {
		// TODO 参数验证
		DeliveryAddress address = new DeliveryAddress();
		address.setProvince(province);
		address.setCity(city);
		address.setCounty(county);
		address.setDetailedAddress(detailedAddress);
		address.setConsigneeName(consigneeName);
		address.setConsigneePhone(consigneePhone);
		address.setDefaul(defaul);

		return ResponseEntity.ok(deliveryAddressService.addAddress(getLoginUser().getUid(), address));
	}

	/**
	 * 修改收货地址
	 *
	 * @param addressId       地址id
	 * @param province        省份
	 * @param city            城市
	 * @param county          区/县
	 * @param detailedAddress 详细地址
	 * @param consigneeName   收货人姓名
	 * @param consigneePhone  收货人电话
	 * @param defaul          是设为默认地址
	 * @return
	 */
	@UncaughtException(msg = "保存收货地址信息失败")
	@RequestMapping(value = "/update_address")
	public ResponseEntity<?> update_address(int addressId, String province, String city, String county, String detailedAddress, String
			consigneeName, String consigneePhone, boolean defaul) {
		// TODO 参数验证
		DeliveryAddress address = new DeliveryAddress();
		address.setId(addressId);
		address.setProvince(province);
		address.setCity(city);
		address.setCounty(county);
		address.setDetailedAddress(detailedAddress);
		address.setConsigneeName(consigneeName);
		address.setConsigneePhone(consigneePhone);
		address.setDefaul(defaul);

		return ResponseEntity.ok(deliveryAddressService.updateAddress(getLoginUser().getUid(), address));
	}

	/**
	 * 删除收货地址
	 *
	 * @param addressId 地址id
	 * @return
	 */
	@UncaughtException(msg = "删除收货地址信息失败")
	@RequestMapping(value = "/delete_address")
	public ResponseEntity<?> delete_address(int addressId) {
		return ResponseEntity.ok(deliveryAddressService.deleteAddress(getLoginUser().getUid(), addressId));
	}

	/**
	 * 获取收货地址
	 *
	 * @param addressId 地址id
	 * @return
	 */
	@UncaughtException(msg = "加载收货地址信息失败")
	@RequestMapping(value = "/get_address")
	public ResponseEntity<?> get_address(int addressId) {
		return ResponseEntity.ok(deliveryAddressService.getAddress(getLoginUser().getUid(), addressId));
	}

	/**
	 * 获取默认收货地址
	 *
	 * @return
	 */
	@UncaughtException(msg = "加载收货地址信息失败")
	@RequestMapping(value = "/get_default_address")
	public ResponseEntity<?> get_default() {
		return ResponseEntity.ok(deliveryAddressService.getDefaultAddress(getLoginUser().getUid()));
	}
}
