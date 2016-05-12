package com.micromall.web.controller;

import com.alibaba.fastjson.JSON;
import com.micromall.repository.ArticleMapper;
import com.micromall.repository.entity.common.PropKeys;
import com.micromall.service.PropertiesService;
import com.micromall.utils.Condition;
import com.micromall.web.resp.ResponseEntity;
import com.micromall.web.security.Authentication;
import org.apache.ibatis.session.RowBounds;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by zhangzx on 16/3/21.
 * 公共接口
 */
@RestController
@RequestMapping(value = "/api")
@Authentication(force = false)
public class ComprehensiveController extends BasisController {

	@Resource
	private ArticleMapper     mapper;
	@Resource
	private PropertiesService propertiesService;

	public static void main(String[] args) {
		System.out.println(JSON.toJSONString(ResponseEntity.fail("这是错误信息")));

		System.out.println(JSON.toJSONString(ResponseEntity.ok("这是返回的数据")));
	}

	/**
	 * 广告配置
	 *
	 * @return
	 */
	@RequestMapping(value = "/index_ad_config")
	public ResponseEntity<?> index_ad_config() {
		return ResponseEntity.ok(propertiesService.getJSONObject(PropKeys.INDEX_AD_CONFIG, Map.class));
	}

	/**
	 * 文章列表
	 *
	 * @param type 文章类型
	 * @param page 分页页码
	 * @return
	 */
	@RequestMapping(value = "/articles")
	public ResponseEntity<?> articles(int type, @RequestParam(defaultValue = "1") int page, Integer limit) {
		return ResponseEntity.ok(mapper.selectPageByWhereClause(Condition.Criteria.create().andEqualTo("type", type).build("id desc"),
				new RowBounds(page, resizeLimit(limit))));
	}

}
