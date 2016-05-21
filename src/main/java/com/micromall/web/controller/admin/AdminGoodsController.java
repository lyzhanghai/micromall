package com.micromall.web.controller.admin;

import com.google.common.collect.Maps;
import com.micromall.service.GoodsService;
import com.micromall.web.controller.BasisController;
import com.micromall.web.resp.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by zhangzx on 16/3/21.
 * 商品管理
 */
@RestController
@RequestMapping(value = "/admin/goods")
public class AdminGoodsController extends BasisController {

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
	private GoodsService goodsService;

	/**
	 * 商品列表
	 *
	 * @param sort 排序方式
	 * @param page 分页页码
	 * @return
	 */
	@RequestMapping(value = "/list")
	public ResponseEntity<?> list(String sort, @RequestParam(defaultValue = "1") int page, Integer limit) {

		return ResponseEntity.Success();
	}

	/**
	 * 商品详情
	 *
	 * @param goodsId 商品id
	 * @return
	 */
	@RequestMapping(value = "/details")
	public ResponseEntity<?> details(int goodsId) {

		return ResponseEntity.Success();
	}

	@RequestMapping(value = "/add")
	public ResponseEntity<?> add() {

		return ResponseEntity.Success();
	}

	@RequestMapping(value = "/update")
	public ResponseEntity<?> update() {

		return ResponseEntity.Success();
	}

	@RequestMapping(value = "/delete")
	public ResponseEntity<?> delete() {

		return ResponseEntity.Success();
	}

	@RequestMapping(value = "/shelves")
	public ResponseEntity<?> shelves() {

		return ResponseEntity.Success();
	}

}
