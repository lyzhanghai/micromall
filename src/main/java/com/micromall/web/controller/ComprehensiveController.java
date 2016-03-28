package com.micromall.web.controller;

import com.micromall.web.resp.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zhangzx on 16/3/21.
 * 公共接口
 */
@Controller
public class ComprehensiveController extends BasisController {

	/**
	 * 商品类目列表
	 *
	 * @return
	 */
	@RequestMapping(value = "/categorys")
	@Deprecated
	public ResponseEntity<?> categorys() {
		return ResponseEntity.ok(true);
	}

	/**
	 * 广告配置
	 *
	 * @return
	 */
	@RequestMapping(value = "/ad_config")
	public ResponseEntity<?> ad_config() {
		return ResponseEntity.ok(true);
	}

	/**
	 * 文章列表
	 *
	 * @return
	 */
	@RequestMapping(value = "/articles")
	public ResponseEntity<?> articles() {
		return ResponseEntity.ok(true);
	}

}
