package com.micromall.service;

import com.google.common.collect.Lists;
import com.micromall.repository.GoodsMapper;
import com.micromall.repository.entity.Goods;
import com.micromall.service.vo.GoodsSearch;
import com.micromall.service.vo.GoodsSearchResult;
import com.micromall.utils.Condition;
import com.micromall.utils.Condition.Criteria;
import com.micromall.utils.LogicException;
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
@Transactional
public class GoodsService {

	@Resource
	private GoodsMapper mapper;

	@Transactional(readOnly = true)
	public List<GoodsSearchResult> searchGoods(GoodsSearch search) {
		Criteria criteria = Condition.Criteria.create();
		if (StringUtils.isNotBlank(search.getQuery())) {
			// criteria.andLeftLike("title", search.getQuery());
			criteria.andAnyLike("title", search.getQuery());
		}
		if (null != search.getPromotion()) {
			criteria.andEqualTo("promotion", search.getPromotion());
		}
		if (null != search.getCategoryId()) {
			criteria.andEqualTo("category_id", search.getCategoryId());
		}

		criteria.andEqualTo("shelves", true);// 商品状态为上架
		criteria.andGreaterThan("inventory", 0);// 库存量大于0

		List<Goods> goodses = mapper.selectPageByWhereClause(criteria.build(search.getSort()), new RowBounds(search.getPage(), search.getLimit()));
		List<GoodsSearchResult> results = Lists.newArrayList();
		try {
			for (Goods goods : goodses) {
				GoodsSearchResult result = new GoodsSearchResult();
				result.setGoodsId(goods.getId());
				result.setImage(goods.getMainImage());
				result.setPromotion(goods.isPromotion());
				BeanUtils.copyProperties(result, goods);
				results.add(result);
			}
		} catch (Exception e) {
			throw new LogicException("商品列表加载失败", e);
		}
		return results;
	}

	@Transactional(readOnly = true)
	public Goods getGoodsInfo(int goodsId) {
		return mapper.selectByPrimaryKey(goodsId);
	}
}
