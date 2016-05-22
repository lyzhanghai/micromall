package com.micromall.web.controller.admin;

import com.micromall.service.WithdrawService;
import com.micromall.web.controller.BasisController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by zhangzx on 16/3/28.
 * 提现管理
 */
@RestController
@RequestMapping(value = "/admin")
public class AdminWithdrawController extends BasisController {

	@Resource
	private WithdrawService withdrawService;

	// 提现申请列表

	// 提现审核

}
