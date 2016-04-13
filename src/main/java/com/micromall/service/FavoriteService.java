package com.micromall.service;

import com.micromall.entity.FavoriteGoods;
import com.micromall.entity.Goods;
import com.micromall.repository.FavoriteGoodsMapper;
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
public class FavoriteService {

	@Resource
	private GoodsService        goodsService;
	@Resource
	private FavoriteGoodsMapper mapper;

	@Transactional(readOnly = true)
	public List<FavoriteGoods> listGoods(int uid) {
		return mapper.selectMultiByWhereClause(Condition.Criteria.create().andEqualTo("uid", uid).build("id desc"));
	}

	@Transactional(readOnly = true)
	public boolean hasFavorite(int uid, int goodsId) {
		return mapper.countByWhereClause(Condition.Criteria.create().andEqualTo("uid", uid).andEqualTo("goods_id", goodsId).build()) > 0;
	}

	@Transactional
	public boolean favoriteGoods(int uid, int goodsId) {
		Goods goods = goodsService.getGoodsInfo(goodsId);
		if (goods != null) {
			mapper.deleteByWhereClause(Condition.Criteria.create().andEqualTo("uid", uid).andEqualTo("goods_id", goodsId).build());
			FavoriteGoods favoriteGoods = new FavoriteGoods();
			favoriteGoods.setUid(uid);
			favoriteGoods.setGoodsId(goodsId);
			favoriteGoods.setTitle(goods.getTitle());
			favoriteGoods.setImage(goods.getMainImage());
			favoriteGoods.setPrice(goods.getPrice());
			favoriteGoods.setCreateTime(new Date());
			mapper.insert(favoriteGoods);
			return true;
		}
		return false;
	}

	@Transactional
	public boolean deleteGoods(int uid, int goodsId) {
		return mapper.deleteByWhereClause(Condition.Criteria.create().andEqualTo("uid", uid).andEqualTo("goods_id", goodsId).build()) > 0;
	}
}
