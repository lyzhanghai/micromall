package com.micromall.utils;

/**
 * <pre>
 *  局
 *
 * @author z.x
 * @date 2015 7 17  下 7:18:57
 */
public final class CommonEnvConstants {
	/* 微信公众号APPID */
	public static final String WEIXIN_APPID                     = PropertyConfigurer.getString("weixin.appid");
	/* 微信公众号SECRET_KEY */
	public static final String WEIXIN_APP_SECRET                = PropertyConfigurer.getString("weixin.app_secret");
	/* 微信用户登录授权接口 */
	public static final String WEIXIN_AUTHORIZE_URL             = PropertyConfigurer.getString("weixin.authorize_url");
	/* 获取access_token接口 */
	public static final String WEIXIN_ACCESS_TOKEN_URL          = PropertyConfigurer.getString("weixin.access_token_url");
	/* 微信用户登录授权回调地址 */
	public static final String WEIXIN_AUTH_CALLBACK_URL         = PropertyConfigurer.getString("weixin.auth.callback_url");
	/* 微信授权成功，跳转到的页面 */
	public static final String WEIXIN_AUTH_SUCCESS_REDIRECT_URL = PropertyConfigurer.getString("weixin.auth.success.redirect_url");
	/* 微信授权失败，跳转到的页面 */
	public static final String WEIXIN_AUTH_FAIL_REDIRECT_URL    = PropertyConfigurer.getString("weixin.auth.fail.redirect_url");
	/* 微信授权请求处理出错，跳转到的页面 */
	public static final String WEIXIN_AUTH_ERROR_REDIRECT_URL   = PropertyConfigurer.getString("weixin.auth.error.redirect_url");

	/* 使用手机号的授权登录页面 */
	public static final String MOBILE_AUTHORIZE_LOGIN_URL = PropertyConfigurer.getString("mobile.authorize.loginUrl");

	/* 登录会话 session cookie sid */
	public static final String LOGIN_SESSION_COOKIE_SID = "_sid";
	/* 登录会话 session key */
	public static final String LOGIN_SESSION_KEY        = "_login_user";

}
