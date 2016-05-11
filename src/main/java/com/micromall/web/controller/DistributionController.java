package com.micromall.web.controller;

import com.micromall.service.DistributionService;
import com.micromall.web.resp.ResponseEntity;
import com.micromall.web.security.Authentication;
import org.apache.ibatis.session.RowBounds;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by zhangzx on 16/3/28.
 * 分销
 */
@RestController
@RequestMapping(value = "/api/distribution")
@Authentication
public class DistributionController extends BasisController {

	@Resource
	private DistributionService distributionService;

	@RequestMapping(value = "/commission_stat")
	@ResponseBody
	public ResponseEntity<?> commission_stat() {
		return ResponseEntity.ok(distributionService.commissionStat(getLoginUser().getUid()));
	}

	/**
	 * 下级分销商统计
	 *
	 * @return
	 */
	@RequestMapping(value = "/lower_distributors_stat")
	@ResponseBody
	public ResponseEntity<?> lower_distributors_stat() {
		return ResponseEntity.ok(distributionService.lowerDistributorsStat(getLoginUser().getUid()));
	}

	/**
	 * 下级分销商列表
	 *
	 * @param level
	 * @return
	 */
	@RequestMapping(value = "/lower_distributors_list")
	@ResponseBody
	public ResponseEntity<?> lower_distributors_list(String level, @RequestParam(defaultValue = "1") int page, Integer limit) {
		Integer _level = null;
		if (level != null) {
			switch (level) {
				case "lv1":
					_level = 1;
					break;
				case "lv2":
					_level = 2;
					break;
				default:
					return ResponseEntity.fail("参数错误");
			}
		}

		return ResponseEntity.ok(distributionService.lowerDistributorsList(getLoginUser().getUid(), _level, new RowBounds(page, resizeLimit(limit)
		)));
	}

}
