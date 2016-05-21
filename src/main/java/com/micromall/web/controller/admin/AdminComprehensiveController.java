package com.micromall.web.controller.admin;

import com.micromall.service.PropertiesService;
import com.micromall.web.controller.BasisController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by zhangzx on 16/3/21.
 * 公共接口
 */
@RestController
@RequestMapping(value = "/admin")
public class AdminComprehensiveController extends BasisController {

	@Resource
	private PropertiesService propertiesService;

	// 首页广告配置
}
