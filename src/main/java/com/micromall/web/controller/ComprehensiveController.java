package com.micromall.web.controller;

import com.micromall.entity.ext.PropKeys;
import com.micromall.repository.ArticleMapper;
import com.micromall.service.PropertiesService;
import com.micromall.utils.CommonEnvConstants;
import com.micromall.utils.Condition;
import com.micromall.web.resp.ResponseEntity;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by zhangzx on 16/3/21.
 * 公共接口
 */
@Controller
public class ComprehensiveController extends BasisController {

	/*@Resource
	private CategoryRepository categoryRepository;*/
	@Resource
	private ArticleMapper     mapper;
	@Resource
	private PropertiesService propertiesService;

	/**
	 * 商品类目列表
	 *
	 * @return
	 */
	/*
	@RequestMapping(value = "/categorys")
	@ResponseBody
	public ResponseEntity<?> categorys() {
		return ResponseEntity.ok(categoryRepository.selectAll());
	}*/

	/**
	 * 广告配置
	 *
	 * @return
	 */
	@RequestMapping(value = "/ad_config")
	@ResponseBody
	public ResponseEntity<?> ad_config() {
		return ResponseEntity.ok(propertiesService.get(PropKeys.AD_CONFIG));
	}

	/**
	 * 文章列表
	 *
	 * @return
	 */
	@RequestMapping(value = "/articles")
	@ResponseBody
	public ResponseEntity<?> articles(int type, @RequestParam(defaultValue = "1") int p) {
		return ResponseEntity.ok(mapper.selectPageByWhereClause(Condition.Criteria.create().andEqualTo("type", type).build("id desc"),
				new RowBounds(p, CommonEnvConstants.ARTICLE_PAGE_LIMIT)));
	}

}
