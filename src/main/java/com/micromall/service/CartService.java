package com.micromall.service;

import com.micromall.entity.CartGoods;
import com.micromall.entity.Goods;
import com.micromall.repository.CartGoodsMapper;
import com.micromall.utils.Condition.Criteria;
import com.micromall.utils.LogicException;
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
	@Resource
	private GoodsService    goodsService;

	@Transactional(readOnly = true)
	public List<CartGoods> listGoods(int uid) {
		return mapper.listGoods(uid);
	}

	@Transactional
	public void updateCartGoods(int uid, int goodsId, int buyNumber) {
		Goods goods = goodsService.getGoodsInfo(goodsId);
		if (goods == null) {
			throw new LogicException("购买的商品不存在");
		}
		if (!goods.isShelves()) {
			throw new LogicException("购买的商品已经下架");
		}
		if (buyNumber > goods.getInventory()) {
			throw new LogicException("购买数量不能大于商品库存量");
		}

		mapper.deleteByWhereClause(Criteria.create().andEqualTo("uid", uid).andEqualTo("goods_id", goodsId).build());
		mapper.insert(new CartGoods(uid, goodsId, buyNumber, new Date()));
	}

	@Transactional
	public void deleteGoods(int uid, List<Integer> goodsIds) {
		mapper.deleteByWhereClause(Criteria.create().andEqualTo("uid", uid).andIn("goods_id", goodsIds).build());
	}
}
