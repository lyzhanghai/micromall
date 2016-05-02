package com.micromall.service;

import com.micromall.repository.entity.Order;
import com.micromall.repository.OrderMapper;
import com.micromall.service.vo.ListViewOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhangzx on 16/3/26.
 */
@Service
public class OrderService {

	@Resource
	private OrderMapper mapper;

	public List<ListViewOrder> findOrders(int uid, int status, int p) {
		return null;
	}

	public Order getOrderDetails(int uid, String orderNo) {
		return null;
	}

	public boolean confirmDelivery(int uid, String orderNo) {
		return false;
	}

	public boolean closeOrder(int uid, String orderNo) {
		return false;
	}

	public boolean createOrder() {
		return false;
	}

	public boolean applyRefund(int uid, String orderNo) {
		return false;
	}
}
