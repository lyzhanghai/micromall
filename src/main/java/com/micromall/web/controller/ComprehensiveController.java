package com.micromall.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zhangzx on 16/3/21.
 */
@Controller
public class ComprehensiveController {

	/**
	 * 商品类目
	 *
	 * @return
	 */
	@RequestMapping(value = "/categorys")
	public ResponseEntity<?> categorys() {
		return ResponseEntity.ok(true);
	}

	/**
	 * 广告配置
	 *
	 * @return
	 */
	@RequestMapping(value = "/advertising")
	public ResponseEntity<?> advertising() {
		return ResponseEntity.ok(true);
	}

}
