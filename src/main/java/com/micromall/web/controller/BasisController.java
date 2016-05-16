package com.micromall.web.controller;

import com.micromall.utils.CommonEnvConstants;
import com.micromall.web.RequestContext;
import com.micromall.web.security.entity.LoginUser;

/**
 * Created by zhangzx on 16/3/28.
 */
public class BasisController {

	protected LoginUser getLoginUser() {
		return RequestContext.getLoginUser();
	}

	protected int resizeLimit(Integer limit) {
		if (limit == null || limit <= 0 || limit > CommonEnvConstants.GLOBAL_PERPAGE_MAX_LIMIT_SIZE) {
			limit = CommonEnvConstants.GLOBAL_PERPAGE_MAX_LIMIT_SIZE;
		}
		return limit;
	}
}
