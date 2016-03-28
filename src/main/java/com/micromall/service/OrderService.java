package com.micromall.service;

import com.micromall.repository.OrderRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zhangzx on 16/3/26.
 */
@Service
public class OrderService {
	@Resource
	private OrderRepository orderRepository;
}
