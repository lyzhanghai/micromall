package com.micromall.web.controller.v;

import com.google.common.collect.Maps;
import com.micromall.repository.entity.Goods;
import com.micromall.service.FavoriteService;
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
 * 商品 TODO 【OK】
 */
@Controller
@RequestMapping(value = "/goods")
public class GoodsController extends BasisController {

	private static final Map<String, String> GOODS_SORT_FIELD_MAP = Maps.newHashMap();

	static {
		GOODS_SORT_FIELD_MAP.put("price_desc", "price desc");// 按价格排序
		GOODS_SORT_FIELD_MAP.put("price_asc", "price asc");// 按价格排序
		GOODS_SORT_FIELD_MAP.put("time_desc", "create_time desc");//  按时间排序
		GOODS_SORT_FIELD_MAP.put("time_asc", "create_time asc");//  按时间排序
		GOODS_SORT_FIELD_MAP.put("volume_desc", "sales_volume desc");//按销量排序
		GOODS_SORT_FIELD_MAP.put("volume_asc", "sales_volume asc");//按销量排序
	}

	@Resource
	private GoodsService    goodsService;
	@Resource
	private FavoriteService favoriteService;

	/**
	 * 商品搜索
	 *
	 * @param query 搜索关键字
	 * @param categoryId 商品类目id
	 * @param promotion 是否促销商品
	 * @param sort 排序方式
	 * @param page 分页页码
	 * @return
	 */
	@UncaughtException(msg = "加载商品列表失败")
	@RequestMapping(value = "/search")
	@ResponseBody
	public ResponseEntity<?> search(String query, Integer categoryId, Boolean promotion, String sort, @RequestParam(defaultValue = "1") int page,
			Integer limit) {
		if (StringUtils.isEmpty(sort) || !GOODS_SORT_FIELD_MAP.containsKey(sort)) {
			sort = CommonEnvConstants.GOODS_SEARCH_DEFAULT_SORT;
		}
		if (limit == null || limit > CommonEnvConstants.GOODS_SEARCH_PERPAGE_MAX_SIZE) {
			limit = CommonEnvConstants.GOODS_SEARCH_PERPAGE_DEFAULT_SIZE;
		}

		GoodsSearch search = GoodsSearch.created(GOODS_SORT_FIELD_MAP.get(sort), page, limit);
		search.setQuery(query).setCategoryId(categoryId).setPromotion(promotion);
		return ResponseEntity.ok(goodsService.searchGoods(search));
	}

	/**
	 * 商品详情
	 *
	 * @param goodsId 商品id
	 * @return
	 */
	@Authentication(force = false)
	@UncaughtException(msg = "加载商品详情失败")
	@RequestMapping(value = "/details")
	@ResponseBody
	public ResponseEntity<?> details(int goodsId) {
		Goods goods = goodsService.getGoodsInfo(goodsId);
		if (null == goods) {
			return ResponseEntity.fail("商品信息不存在");
		}
		if (null != getLoginUser()) {
			goods.setFavorite(favoriteService.hasFavorite(getLoginUser().getUid(), goodsId));
		}
		if (goods.getInventory() <= 0) {
			goods.setShelves(false);
		}
		return ResponseEntity.ok(goods);
	}

}
