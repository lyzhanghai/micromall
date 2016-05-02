package com.micromall.service;

import com.micromall.repository.entity.FavoriteGoods;
import com.micromall.repository.entity.Goods;
import com.micromall.repository.FavoriteGoodsMapper;
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
public class FavoriteService {

	@Resource
	private GoodsService        goodsService;
	@Resource
	private FavoriteGoodsMapper mapper;

	@Transactional(readOnly = true)
	public List<FavoriteGoods> listGoods(int uid) {
		return mapper.selectMemberFavoriteGoods(uid);
	}

	@Transactional(readOnly = true)
	public boolean hasFavorite(int uid, int goodsId) {
		return mapper.countByWhereClause(Criteria.create().andEqualTo("uid", uid).andEqualTo("goods_id", goodsId).build()) > 0;
	}

	@Transactional
	public void favoriteGoods(int uid, int goodsId) {
		Goods goods = goodsService.getGoodsInfo(goodsId);
		if (goods == null) {
			throw new LogicException("收藏的商品不存在");
		}
		if (!goods.isShelves()) {
			throw new LogicException("收藏的商品已经下架");
		}

		// TODO 收藏商品上限控制

		mapper.deleteByWhereClause(Criteria.create().andEqualTo("uid", uid).andEqualTo("goods_id", goodsId).build());
		FavoriteGoods favoriteGoods = new FavoriteGoods();
		favoriteGoods.setUid(uid);
		favoriteGoods.setGoodsId(goodsId);
		favoriteGoods.setTitle(goods.getTitle());
		favoriteGoods.setImage(goods.getMainImage());
		favoriteGoods.setPrice(goods.getPrice());
		favoriteGoods.setCreateTime(new Date());
		mapper.insert(favoriteGoods);
	}

	@Transactional
	public void deleteGoods(int uid, int goodsId) {
		mapper.deleteByWhereClause(Criteria.create().andEqualTo("uid", uid).andEqualTo("goods_id", goodsId).build());
	}
}
