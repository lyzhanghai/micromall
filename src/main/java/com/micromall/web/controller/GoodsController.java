package com.micromall.web.controller;

import com.micromall.service.GoodsService;
import com.micromall.service.vo.GoodsSearch;
import com.micromall.utils.CommonEnvConstants;
import com.micromall.web.extend.UncaughtException;
import com.micromall.web.resp.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by zhangzx on 16/3/21.
 * 商品
 */
@Controller
@RequestMapping(value = "/goods")
public class GoodsController extends BasisController {

	@Resource
	private GoodsService goodsService;

	/**
	 * 根据类目返回商品列表
	 *
	 * @param categoryId 类目id
	 * @param p          分页页码
	 * @return
	 */
	@UncaughtException(msg = "加载商品列表失败")
	@RequestMapping(value = "/category_get_goods")
	@ResponseBody
	public ResponseEntity<?> category_get_goods(int categoryId, String sort, int p) {
		GoodsSearch search = GoodsSearch.created(sort, p, CommonEnvConstants.INDEX_GOODS_PERPAGE_SIZE);
		search.setCategoryId(categoryId);
		return ResponseEntity.ok(goodsService.findGoods(search));
	}

	/**
	 * 促销商品
	 *
	 * @param p 分页页码
	 * @return
	 */
	@UncaughtException(msg = "加载商品列表失败")
	@RequestMapping(value = "/promotion_get_goods")
	@ResponseBody
	public ResponseEntity<?> promotion_get_goods(String sort, int p) {
		GoodsSearch search = GoodsSearch.created(sort, p, CommonEnvConstants.INDEX_GOODS_PERPAGE_SIZE);
		search.setPromotion(true);
		return ResponseEntity.ok(goodsService.findGoods(search));
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
		return ResponseEntity.ok(goodsService.getGoodsDetails(goodsId));
	}

}
