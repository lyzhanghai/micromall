package com.micromall.web.controller;

import com.micromall.repository.entity.common.PropKeys;
import com.micromall.service.PropertiesService;
import com.micromall.web.resp.ResponseEntity;
import com.micromall.web.security.annotation.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by zhangzx on 16/3/21.
 * 公共接口
 */
@RestController
@RequestMapping(value = "/api")
@Authentication
public class ComprehensiveController extends BasisController {

	@Resource
	private PropertiesService propertiesService;

	/**
	 * 广告配置
	 *
	 * @return
	 */
	@RequestMapping(value = "/index_ad_config")
	public ResponseEntity<?> index_ad_config() {
		return ResponseEntity.Success(propertiesService.getJSONObject(PropKeys.INDEX_AD_CONFIG, Map.class));
	}

}
