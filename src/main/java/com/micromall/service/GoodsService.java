package com.micromall.service;

import com.google.common.collect.Lists;
import com.micromall.entity.Goods;
import com.micromall.repository.GoodsMapper;
import com.micromall.service.vo.GoodsDetails;
import com.micromall.service.vo.GoodsSearch;
import com.micromall.service.vo.GoodsSearchResult;
import com.micromall.utils.Condition;
import com.micromall.utils.Condition.Criteria;
import com.micromall.utils.ServiceException;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhangzx on 16/3/26.
 */
@Service
public class GoodsService {

	@Resource
	private GoodsMapper     mapper;
	@Resource
	private FavoriteService favoriteService;

	@Transactional(readOnly = true)
	public List<GoodsSearchResult> searchGoods(GoodsSearch search) {
		Criteria criteria = Condition.Criteria.create();
		if (StringUtils.isNotBlank(search.getQuery())) {
			criteria.andLeftLike("title", search.getQuery());
		}
		if (null != search.getPromotion()) {
			criteria.andEqualTo("promotion", search.getPromotion());
		}
		if (null != search.getCategoryId()) {
			criteria.andEqualTo("category_id", search.getCategoryId());
		}
		List<Goods> goodses = mapper.selectPageByWhereClause(criteria.build(search.getSort()), new RowBounds(search.getPage(), search.getLimit()));
		List<GoodsSearchResult> results = Lists.newArrayList();
		try {
			for (Goods goods : goodses) {
				GoodsSearchResult result = new GoodsSearchResult();
				BeanUtils.copyProperties(result, goods);
				results.add(result);
			}
		} catch (Exception e) {
			throw new ServiceException("商品列表加载失败", e);
		}
		return results;
	}

	@Transactional(readOnly = true)
	public GoodsDetails getGoodsDetails(int goodsId, Integer uid) {
		Goods goods = mapper.selectFullByPrimaryKey(goodsId);
		if (null == goods) {
			return null;
		}
		GoodsDetails details = new GoodsDetails();
		details.setFavorite(favoriteService.hasFavorite(uid, goodsId));
		try {
			BeanUtils.copyProperties(details, goods);
		} catch (Exception e) {
			throw new ServiceException("商品信息加载失败", e);
		}
		return details;
	}

	@Transactional(readOnly = true)
	public Goods getGoodsInfo(int goodsId) {
		return mapper.selectByPrimaryKey(goodsId);
	}
}
