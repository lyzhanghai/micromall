package com.micromall.web.controller.v;

import com.google.common.collect.Maps;
import com.micromall.service.GoodsService;
import com.micromall.service.vo.GoodsSearch;
import com.micromall.utils.CommonEnvConstants;
import com.micromall.web.controller.BasisController;
import com.micromall.web.extend.UncaughtException;
import com.micromall.web.resp.ResponseEntity;
import com.micromall.web.security.Authentication;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by zhangzx on 16/3/21.
 * 商品
 */
@Controller
@RequestMapping(value = "/goods")
@Authentication(force = false)
public class GoodsController extends BasisController {

	private static final Map<String, String> GOODS_SORT_FIELD_MAP = Maps.newHashMap();

	static {
		GOODS_SORT_FIELD_MAP.put("price_desc", "price desc");// 按价格排序
		GOODS_SORT_FIELD_MAP.put("price_asc", "price asc");// 按价格排序
		GOODS_SORT_FIELD_MAP.put("time_desc", "time desc");//  按时间排序
		GOODS_SORT_FIELD_MAP.put("time_asc", "time asc");//  按时间排序
		GOODS_SORT_FIELD_MAP.put("salesVolume_desc", "sales_volume desc");//按销量排序
		GOODS_SORT_FIELD_MAP.put("salesVolume_asc", "sales_volume asc");//按销量排序
	}

	@Resource
	private GoodsService goodsService;

	/**
	 * 商品搜索
	 *
	 * @return
	 */
	@UncaughtException(msg = "加载商品列表失败")
	@RequestMapping(value = "/search_goods")
	@ResponseBody
	public ResponseEntity<?> search_goods(String query, Integer categoryId, Boolean promotion, String sort,
			@RequestParam(defaultValue = "1") int page) {
		if (StringUtils.isEmpty(sort) || !GOODS_SORT_FIELD_MAP.containsKey(sort)) {
			sort = CommonEnvConstants.GOODS_SEARCH_DEFAULT_SORT;
		}
		GoodsSearch search = GoodsSearch.created(GOODS_SORT_FIELD_MAP.get(sort), page, CommonEnvConstants.GOODS_SEARCH_PERPAGE_SIZE);
		search.setQuery(query).setCategoryId(categoryId).setPromotion(promotion);
		return ResponseEntity.ok(goodsService.searchGoods(search));
	}

	/**
	 * 商品详情
	 *
	 * @param goodsId 商品id
	 * @return
	 */
	@UncaughtException(msg = "加载商品详情失败")
	@RequestMapping(value = "/details")
	@ResponseBody
	public ResponseEntity<?> details(int goodsId) {
		Integer uid = getLoginUser() != null ? getLoginUser().getUid() : null;
		return ResponseEntity.ok(goodsService.getGoodsDetails(goodsId));
	}

}
