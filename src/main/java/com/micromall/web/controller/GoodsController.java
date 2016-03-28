package com.micromall.web.controller;

import com.micromall.web.resp.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zhangzx on 16/3/21.
 * 商品
 */
@Controller
@RequestMapping(value = "/goods")
public class GoodsController extends BasisController {

	/**
	 * 分类下的商品列表
	 *
	 * @return
	 */
	@RequestMapping(value = "/category_get_goods")
	public ResponseEntity<?> orders() {
		return ResponseEntity.ok(true);
	}

	/**
	 * 商品详情
	 *
	 * @return
	 */
	@RequestMapping(value = "/details")
	public ResponseEntity<?> details() {
		return ResponseEntity.ok(true);
	}

}
