package com.micromall.web.controller.v;

import com.micromall.entity.ShippingAddress;
import com.micromall.service.ShippingAddressService;
import com.micromall.utils.ArgumentValidException;
import com.micromall.utils.ValidateUtils;
import com.micromall.web.controller.BasisController;
import com.micromall.web.extend.UncaughtException;
import com.micromall.web.resp.ResponseEntity;
import com.micromall.web.security.Authentication;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by zhangzx on 16/3/28.
 * 收货地址
 */
@Controller
@RequestMapping(value = "/shipping_address")
@Authentication
public class ShippingAddressController extends BasisController {

	@Resource
	private ShippingAddressService deliveryAddressService;

	/**
	 * 收货地址列表
	 *
	 * @return
	 */
	@RequestMapping(value = "/list")
	@UncaughtException(msg = "加载收货地址信息失败")
	@ResponseBody
	public ResponseEntity<?> list() {
		return ResponseEntity.ok(deliveryAddressService.list(getLoginUser().getUid()));
	}

	/**
	 * 新增收货地址
	 *
	 * @param province 省份
	 * @param city 城市
	 * @param county 区/县
	 * @param detailedAddress 详细地址
	 * @param consigneeName 收货人姓名
	 * @param consigneePhone 收货人电话
	 * @param defaul 是否设为默认地址
	 * @return
	 */
	@UncaughtException(msg = "保存收货地址信息失败")
	@RequestMapping(value = "/add")
	@ResponseBody
	public ResponseEntity<?> add_address(String province, String city, String county, String detailedAddress, String consigneeName,
			String consigneePhone, String postcode, @RequestParam(defaultValue = "false") boolean defaul) {

		ShippingAddress address = new ShippingAddress();
		address.setUid(getLoginUser().getUid());
		address.setProvince(province);
		address.setCity(city);
		address.setCounty(county);
		address.setDetailedAddress(detailedAddress);
		address.setConsigneeName(consigneeName);
		address.setConsigneePhone(consigneePhone);
		address.setPostcode(postcode);
		address.setDefaul(defaul);
		address.setCreateTime(new Date());
		validateAddress(address);
		deliveryAddressService.addAddress(address);

		return ResponseEntity.ok(address);
	}

	/**
	 * 修改收货地址
	 *
	 * @param addressId 地址id
	 * @param province 省份
	 * @param city 城市
	 * @param county 区/县
	 * @param detailedAddress 详细地址
	 * @param consigneeName 收货人姓名
	 * @param consigneePhone 收货人电话
	 * @param defaul 是否设为默认地址
	 * @return
	 */
	@UncaughtException(msg = "保存收货地址信息失败")
	@RequestMapping(value = "/update")
	@ResponseBody
	public ResponseEntity<?> update_address(int addressId, String province, String city, String county, String detailedAddress, String consigneeName,
			String consigneePhone, String postcode, @RequestParam(defaultValue = "false") boolean defaul) {

		ShippingAddress address = new ShippingAddress();
		address.setId(addressId);
		address.setUid(getLoginUser().getUid());
		address.setProvince(province);
		address.setCity(city);
		address.setCounty(county);
		address.setDetailedAddress(detailedAddress);
		address.setConsigneeName(consigneeName);
		address.setConsigneePhone(consigneePhone);
		address.setPostcode(postcode);
		address.setDefaul(defaul);
		address.setUpdateTime(new Date());
		validateAddress(address);
		if (!deliveryAddressService.updateAddress(address)) {
			return ResponseEntity.fail("保存收货地址信息失败");
		}
		return ResponseEntity.ok();
	}

	private void validateAddress(ShippingAddress address) {
		if (StringUtils.isBlank(address.getProvince())) {
			throw new ArgumentValidException("省份不能为空");
		}
		if (StringUtils.isBlank(address.getCity())) {
			throw new ArgumentValidException("城市不能为空");
		}
		if (StringUtils.isBlank(address.getCounty())) {
			throw new ArgumentValidException("区县不能为空");
		}
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
		if (ValidateUtils.illegalTextLength(1, 15, address.getCounty())) {
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
		if (ValidateUtils.illegalPostcode(address.getPostcode())) {
			throw new ArgumentValidException("邮政编码输入不正确");
		}
	}

	/**
	 * 删除收货地址
	 *
	 * @param id 地址id
	 * @return
	 */
	@UncaughtException(msg = "删除收货地址信息失败")
	@RequestMapping(value = "/delete")
	@ResponseBody
	public ResponseEntity<?> delete_address(int id) {
		deliveryAddressService.deleteAddress(getLoginUser().getUid(), id);
		return ResponseEntity.ok();
	}

	/**
	 * 获取收货地址
	 *
	 * @param id 地址id
	 * @return
	 */
	@UncaughtException(msg = "加载收货地址信息失败")
	@RequestMapping(value = "/get")
	@ResponseBody
	public ResponseEntity<?> get_address(int id) {
		return ResponseEntity.ok(deliveryAddressService.getAddress(getLoginUser().getUid(), id));
	}

}
