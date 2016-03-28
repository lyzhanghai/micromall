package com.micromall.service;

import com.micromall.repository.GoodsRepository;
import com.micromall.service.vo.GoodsDetails;
import com.micromall.service.vo.GoodsSearch;
import com.micromall.service.vo.ListViewGoods;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhangzx on 16/3/26.
 */
@Service
public class GoodsService {
	@Resource
	private GoodsRepository goodsRepository;

	public List<ListViewGoods> findGoods(GoodsSearch search) {
		return null;
	}

	public GoodsDetails getGoodsDetails(int goodsId) {
		return null;
	}
}
