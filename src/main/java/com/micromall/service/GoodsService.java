package com.micromall.service;

import com.micromall.entity.Goods;
import com.micromall.repository.GoodsMapper;
import com.micromall.service.vo.GoodsDetails;
import com.micromall.service.vo.GoodsSearch;
import com.micromall.service.vo.GoodsSearchResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhangzx on 16/3/26.
 */
@Service
public class GoodsService {

	@Resource
	private GoodsMapper mapper;

	public List<GoodsSearchResult> searchGoods(GoodsSearch search) {
		return null;
	}

	public GoodsDetails getGoodsDetails(int goodsId) {
		return null;
	}

	public Goods getGoodsSimpleInfo(int goodsId) {
		return null;
	}
}
