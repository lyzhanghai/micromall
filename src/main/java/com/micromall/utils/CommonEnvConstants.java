package com.micromall.utils;

/**
 * <pre>
 *
 * @author z.x
 * @date 2015 7 17  下 7:18:57
 */
public final class CommonEnvConstants {

	/* 运行环境 */
	public static final Environment ENV               = new Environment(PropertyConfigurer.getString("app.environment"));
	/* 开发模式下模拟登录用户id */
	public static final int         DEBUG_AUTH_USERID = Integer.valueOf(PropertyConfigurer.getString("debug.auth.userid"));

	/**
	 * =============== 微信授权/登录配置 ===============
	 **/
	/* 微信公众号APPID */
	public static final String WEIXIN_APPID                     = PropertyConfigurer.getString("weixin.appid");
	/* 微信公众号SECRET_KEY */
	public static final String WEIXIN_APP_SECRET                = PropertyConfigurer.getString("weixin.app_secret");
	/* 微信用户授权作用域 */
	public static final String WEIXIN_AUTH_SCOPE                = PropertyConfigurer.getString("weixin.auth.scope");
	/* 微信用户登录授权接口 */
	public static final String WEIXIN_AUTHORIZE_URL             = PropertyConfigurer.getString("weixin.authorize_url");
	/* 获取access_token接口 */
	public static final String WEIXIN_ACCESS_TOKEN_URL          = PropertyConfigurer.getString("weixin.access_token_url");
	/* 获取用户信息接口 */
	public static final String WEIXIN_USERINFO_URL              = PropertyConfigurer.getString("weixin.userinfo_url");
	/* 微信用户登录授权回调地址 */
	public static final String WEIXIN_AUTH_CALLBACK_URL         = PropertyConfigurer.getString("weixin.auth.callback_url");
	/* 微信授权成功，跳转到的页面 */
	public static final String WEIXIN_AUTH_SUCCESS_REDIRECT_URL = PropertyConfigurer.getString("weixin.auth.success.redirect_url");
	/* 微信授权失败，跳转到的页面 */
	public static final String WEIXIN_AUTH_FAIL_REDIRECT_URL    = PropertyConfigurer.getString("weixin.auth.fail.redirect_url");
	/* 服务器请求处理出错，跳转到的页面 */
	public static final String SERVER_ERROR_REDIRECT_URL        = PropertyConfigurer.getString("server.error.redirect_url");

	/**
	 * =============== 手机号码登录配置 ===============
	 **/
	/* 是否支持手机号码登录 */
	public static final boolean MOBILE_LOGIN_USABLE        = Boolean.valueOf(PropertyConfigurer.getString("mobile.login.usable"));
	/* 使用手机号的授权登录页面 */
	public static final String  MOBILE_AUTHORIZE_LOGIN_URL = PropertyConfigurer.getString("mobile.authorize.loginUrl");

	/**
	 * =============== 登录会话session配置 ===============
	 **/
	/* 登录会话 session cookie sid */
	public static final String LOGIN_SESSION_COOKIE_SID = "_sid";
	/* 登录会话 session key */
	public static final String LOGIN_SESSION_KEY        = "_login_user";

	/**
	 * =============== 短信验证码配置 ===============
	 **/
	/* 短信验证码缓存Key */
	public static final String VERIFYCODE_KEY      = "_Verifycode";
	/* 短信验证码模板 */
	public static final String VERIFYCODE_TEMPLATE = PropertyConfigurer.getString("verifycode.template");
	/* session 缓存过期时间 */
	//public static final int    SESSION_CACHE_EXPRIED = CacheService.DAY * 30;

	/**
	 * =============== 文件上传目录配置 ===============
	 **/
	/* 用户头像保存目录 */
	public static final String UPLOAD_MEMBER_IMAGES_DIR      = PropertyConfigurer.getString("upload.member_images.dir");
	/* 商品图片保存目录 */
	public static final String UPLOAD_GOODS_IMAGES_DIR       = PropertyConfigurer.getString("upload.goods_images.dir");
	/* 广告图片保存目录 */
	public static final String UPLOAD_AD_IMAGES_DIR          = PropertyConfigurer.getString("upload.ad_images.dir");
	/* 证件图片保存目录 */
	public static final String UPLOAD_CERTIFICATE_IMAGES_DIR = PropertyConfigurer.getString("upload.certificate_images.dir");

	/* 用户默认头像 */
	public static final String MEMBER_DEFAULT_AVATAR = PropertyConfigurer.getString("member.default.avatar");

	/* 商品搜索默认每页显示个数 */
	// public static final int    GOODS_SEARCH_PERPAGE_DEFAULT_SIZE = Integer.valueOf(PropertyConfigurer.getString("goods.search" + "
	// .perpage_default_size"));
	/*商品搜索每页最大显示个数*/
	// public static final int    GOODS_SEARCH_PERPAGE_MAX_SIZE     = Integer.valueOf(PropertyConfigurer.getString("goods.search.perpage_max_size"));
	/* 商品搜索默认排序字段 */
	public static final String GOODS_SEARCH_DEFAULT_SORT = PropertyConfigurer.getString("goods.search.default_sort");

	/*前端默认分页大小*/
	// public static final int FRONT_DEFAULT_PAGE_LIMIT = Integer.valueOf(PropertyConfigurer.getString("front.default.page_limit"));
	public static final int GLOBAL_PERPAGE_MAX_LIMIT_SIZE = Integer.valueOf(PropertyConfigurer.getString("global.perpage.max_limit_size"));

	/*可申请提现时间区间*/
	public static final String WITHDRAW_APPLY_ALLOW_TIME_INTERVAL  = PropertyConfigurer.getString("withdraw.apply.allow.time_interval");
	/*单次提现最小金额限制*/
	public static final int    WITHDRAW_APPLY_SINGLE_MIN_AMOUNT    = Integer
			.valueOf(PropertyConfigurer.getString("withdraw.apply.single.min_amount"));
	/*单次提现最大金额限制*/
	public static final int    WITHDRAW_APPLY_SINGLE_MAX_AMOUNT    = Integer
			.valueOf(PropertyConfigurer.getString("withdraw.apply.single.max_amount"));
	/*订单未支付超时自动关闭时间*/
	public static final String ORDER_NOTPAY_TIMEOUT_CLOSE_TIME     = PropertyConfigurer.getString("order.notpay.timeout.close_time");
	/*订单物流已收货超时自动确认收货时间*/
	public static final String ORDER_TIMEOUT_confirm_delivery_TIME = PropertyConfigurer.getString("order.timeout.confirm_delivery_time");

	public static class Environment {

		private String environment;

		public Environment(String environment) {
			this.environment = environment;
		}

		public boolean isDistEnv() {
			return "dist".equals(environment);
		}

		public boolean isDevEnv() {
			return "dev".equals(environment);
		}
	}
}
