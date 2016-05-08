package com.micromall.web.controller.v;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.micromall.repository.ArticleMapper;
import com.micromall.repository.entity.common.PropKeys;
import com.micromall.service.PropertiesService;
import com.micromall.utils.ChainMap;
import com.micromall.utils.CommonEnvConstants;
import com.micromall.utils.Condition;
import com.micromall.web.controller.BasisController;
import com.micromall.web.resp.ResponseEntity;
import com.micromall.web.security.Authentication;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangzx on 16/3/21.
 * 公共接口
 */
@Controller
@Authentication(force = false)
public class ComprehensiveController extends BasisController {

	@Resource
	private ArticleMapper     mapper;
	@Resource
	private PropertiesService propertiesService;

	/**
	 * 广告配置
	 *
	 * @return
	 */
	@RequestMapping(value = "/index_ad_config")
	@ResponseBody
	public ResponseEntity<?> index_ad_config() {
		Map<String, Object> map = Maps.newHashMap();
		List<Map<String, Object>> banner = Lists.newArrayList();
		banner.add(new ChainMap<String, Object>().append("image", "http://img11.360buyimg.com/da/53ba3868Nea2f6c42.png")
		                                         .append("link", "http://www.micromall.com/xxx/xxx.html"));
		banner.add(new ChainMap<String, Object>().append("image", "http://img11.360buyimg.com/da/53ba3868Nea2f6c42.png")
		                                         .append("link", "http://www.micromall.com/xxx/xxx.html"));
		banner.add(new ChainMap<String, Object>().append("image", "http://img11.360buyimg.com/da/53ba3868Nea2f6c42.png")
		                                         .append("link", "http://www.micromall.com/xxx/xxx.html"));
		map.put("banner", banner);

		List<Map<String, Object>> middle = Lists.newArrayList();
		middle.add(new ChainMap<String, Object>().append("index", 1).append("image", "http://img11.360buyimg.com/da/53ba3868Nea2f6c42.png")
		                                         .append("link", "http://www.micromall.com/xxx/xxx.html"));
		middle.add(new ChainMap<String, Object>().append("index", 2).append("image", "http://img11.360buyimg.com/da/53ba3868Nea2f6c42.png")
		                                         .append("link", "http://www.micromall.com/xxx/xxx.html"));
		middle.add(new ChainMap<String, Object>().append("index", 3).append("image", "http://img11.360buyimg.com/da/53ba3868Nea2f6c42.png")
		                                         .append("link", "http://www.micromall.com/xxx/xxx.html"));

		map.put("middle", middle);
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
	@ResponseBody
	public ResponseEntity<?> articles(int type, @RequestParam(defaultValue = "1") int page) {
		return ResponseEntity.ok(mapper.selectPageByWhereClause(Condition.Criteria.create().andEqualTo("type", type).build("id desc"),
				new RowBounds(page, CommonEnvConstants.FRONT_DEFAULT_PAGE_LIMIT)));
	}

}
