package com.micromall.service;

import com.micromall.service.vo.CartGoods;
import com.micromall.repository.CartRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhangzx on 16/3/26.
 */
@Service
public class CartService {
	@Resource
	private CartRepository cartRepository;

	public List<CartGoods> goodses(int uid) {
		return null;
	}

	public boolean joinCart(int uid, int goodsId, int buyNumber) {
		return false;
	}

	public boolean updateBuyNumber(int uid, int goodsId, int buyNumber) {
		return false;
	}

	public boolean deleteGoods(int uid, int goodsId) {
		return false;
	}
}
