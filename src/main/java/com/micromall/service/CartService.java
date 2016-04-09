package com.micromall.service;

import com.micromall.entity.CartGoods;
import com.micromall.repository.CartGoodsMapper;
import com.micromall.service.vo.CartGoodsDTO;
import com.micromall.utils.Condition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangzx on 16/3/26.
 */
@Service
public class CartService {

	@Resource
	private CartGoodsMapper mapper;

	@Transactional(readOnly = true)
	public List<CartGoodsDTO> goodses(int uid) {
		return mapper.list(uid);
	}

	@Transactional
	public void joinCart(int uid, int goodsId, int buyNumber) {
		this._updateCart(uid, goodsId, buyNumber);
	}

	@Transactional
	public void updateBuyNumber(int uid, int goodsId, int buyNumber) {
		this._updateCart(uid, goodsId, buyNumber);
	}

	private void _updateCart(int uid, int goodsId, int buyNumber) {
		mapper.deleteByWhereClause(Condition.Criteria.create().andEqualTo("uid", uid).andEqualTo("goods_id", goodsId).build());
		mapper.insert(new CartGoods(uid, goodsId, buyNumber, new Date()));
	}

	@Transactional
	public boolean deleteGoods(int uid, List<Integer> goodsIds) {
		return mapper.deleteByWhereClause(Condition.Criteria.create().andEqualTo("uid", uid).andIn("goods_id", goodsIds).build()) > 0;
	}
}
