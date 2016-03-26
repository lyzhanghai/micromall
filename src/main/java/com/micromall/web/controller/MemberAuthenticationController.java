package com.micromall.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.micromall.core.utils.HttpUtils;
import com.micromall.entity.LoginUser;
import com.micromall.utils.CommonEnvConstants;
import com.micromall.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by zhangzx on 16/3/21.
 * 会员认证
 */
@Controller
public class MemberAuthenticationController {

	private static Logger logger = LoggerFactory.getLogger(MemberAuthenticationController.class);

	@RequestMapping(value = "/auth/login")
	public ResponseEntity<?> login() {
		return ResponseEntity.ok(true);
	}

	@RequestMapping(value = "/member/register")
	public ResponseEntity<?> register() {
		return ResponseEntity.ok(true);
	}

	/**
	 * 微信登录授权回调
	 *
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/weixin/auth_callback")
	public void weixinAuthCallback(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			String code = request.getParameter("code");
			if (StringUtils.isEmpty(code)) {
				logger.error("微信用户授权失败：缺少<code>");
				response.sendRedirect(CommonEnvConstants.WEIXIN_AUTH_FAIL_REDIRECT_URL);
				return;
			}

			Map<String, String> _params = new HashMap<String, String>();
			_params.put("appid", CommonEnvConstants.WEIXIN_APPID);
			_params.put("secret", CommonEnvConstants.WEIXIN_APP_SECRET);
			_params.put("code", code);
			_params.put("grant_type", "authorization_code");

			String access_token = null;
			String openid = null;
			try {
				ResponseEntity<String> responseEntity = HttpUtils.executeRequest(CommonEnvConstants.WEIXIN_ACCESS_TOKEN_URL, _params, String.class);
				if (responseEntity.getStatusCode() == HttpStatus.OK && StringUtils.isNotEmpty(responseEntity.getBody())) {
					JSONObject jsonObject = JSON.parseObject(responseEntity.getBody());
					openid = jsonObject.getString("openid");
					access_token = jsonObject.getString("access_token");
				}
			} catch (Exception e) {
				logger.error("微信授权出错：换取网页授权access_token出错：", e);
			}
			if (StringUtils.isEmpty(openid) || StringUtils.isEmpty(access_token)) {
				logger.error("微信用户授权失败：openid={}, access_token={}", openid, access_token);
				response.sendRedirect(CommonEnvConstants.WEIXIN_AUTH_FAIL_REDIRECT_URL);
				return;
			}

			// TODO 检查用户是否绑定
			LoginUser loginUser = null;

			// 保存会话信息
			String sid = UUID.randomUUID().toString().replace("-", "");
			CookieUtils.addCookie(response, CommonEnvConstants.LOGIN_SESSION_COOKIE_SID, sid);
			request.getSession().setAttribute(CommonEnvConstants.LOGIN_SESSION_KEY, loginUser);


			// 跳转到来源页面，默认首页
			String redirect_url = request.getParameter("return_url");
			if (StringUtils.isNotEmpty(redirect_url)) {
				response.sendRedirect(redirect_url);
			} else {
				response.sendRedirect(CommonEnvConstants.WEIXIN_AUTH_SUCCESS_REDIRECT_URL);
			}
		} catch (Exception e) {
			logger.error("微信登录授权后台处理出错：", e);
			response.sendRedirect(CommonEnvConstants.WEIXIN_AUTH_ERROR_REDIRECT_URL);
		}
	}
}