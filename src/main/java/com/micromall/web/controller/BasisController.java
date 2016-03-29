package com.micromall.web.controller;

import com.micromall.web.RequestContext;
import com.micromall.web.security.LoginUser;

/**
 * Created by zhangzx on 16/3/28.
 */
public class BasisController {

	protected LoginUser getLoginUser() {
		return RequestContext.getLoginUser();
	}
}
