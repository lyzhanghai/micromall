package com.micromall.web.controller;

import com.micromall.entity.LoginUser;
import com.micromall.web.RequestContext;

/**
 * Created by zhangzx on 16/3/28.
 */
public class BasisController {

	protected LoginUser getLoginUser() {
		return RequestContext.getLoginUser();
	}
}
