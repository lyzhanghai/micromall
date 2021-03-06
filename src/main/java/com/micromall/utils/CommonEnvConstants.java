package com.micromall.utils;

import com.micromall.service.PropertiesService;

import java.math.BigDecimal;

/**
 * <pre>
 *
 * @author z.x
 * @date 2015 7 17  下 7:18:57
 */
public final class CommonEnvConstants {

	/* 运行环境 */
	public static final Environment ENV = new Environment(PropertyConfigurerUtils.getString("app.environment"));

	/**
	 * =============== 微信授权/登录配置 ===============
	 **/
	/* 微信公众号APPID */
	public static final String WEIXIN_APPID                     = PropertyConfigurerUtils.getString("weixin.appid");
	/* 微信公众号SECRET_KEY */
	public static final String WEIXIN_APP_SECRET                = PropertyConfigurerUtils.getString("weixin.app_secret");
	/* 商户号 */
	public static final String WEIXIN_MCHID                     = PropertyConfigurerUtils.getString("weixin.mchid");
	/* 商户secuityKey */
	public static final String WEIXIN_SECRET_KEY                = PropertyConfigurerUtils.getString("weixin.secuity_key");
	/* 异步通知回调地址 */
	public static final String WEIXIN_NOTIFY_URL                = PropertyConfigurerUtils.getString("weixin.notifyUrl");
	/* 统一下单url */
	public static final String WEIXIN_UNIFIEDORDER_URL          = PropertyConfigurerUtils.getString("weixin.unifiedorderUrl");
	/* 微信用户授权作用域 */
	public static final String WEIXIN_AUTH_SCOPE                = PropertyConfigurerUtils.getString("weixin.auth.scope");
	/* 微信用户登录授权接口 */
	public static final String WEIXIN_AUTHORIZE_URL             = PropertyConfigurerUtils.getString("weixin.authorize_url");
	/* 获取access_token接口 */
	public static final String WEIXIN_ACCESS_TOKEN_URL          = PropertyConfigurerUtils.getString("weixin.access_token_url");
	/* 获取用户信息接口 */
	public static final String WEIXIN_USERINFO_URL              = PropertyConfigurerUtils.getString("weixin.userinfo_url");
	/* 微信用户登录授权回调地址 */
	public static final String WEIXIN_AUTH_CALLBACK_URL         = PropertyConfigurerUtils.getString("weixin.auth.callback_url");
	/* 微信授权成功，跳转到的页面 */
	public static final String WEIXIN_AUTH_SUCCESS_REDIRECT_URL = PropertyConfigurerUtils.getString("weixin.auth.success.redirect_url");
	/* 微信授权失败，跳转到的页面 */
	public static final String WEIXIN_AUTH_FAIL_REDIRECT_URL    = PropertyConfigurerUtils.getString("weixin.auth.fail.redirect_url");
	/* 服务器请求处理出错，跳转到的页面 */
	public static final String SERVER_ERROR_REDIRECT_URL        = PropertyConfigurerUtils.getString("server.error.redirect_url");
	/* 访问的页面找不到，跳转到的页面 */
	public static final String SERVER_NOTFOUND_REDIRECT_URL     = PropertyConfigurerUtils.getString("server.notfound.redirect_url");
	public static final String SERVER_INDEX_REDIRECT_URL        = PropertyConfigurerUtils.getString("server.index.redirect_url");

	/**
	 * =============== 手机号码登录配置 ===============
	 **/
	/* 是否支持手机号码登录 */
	public static final boolean MOBILE_LOGIN_USABLE        = Boolean.valueOf(PropertyConfigurerUtils.getString("mobile.login.usable"));
	/* 使用手机号的授权登录页面 */
	public static final String  MOBILE_AUTHORIZE_LOGIN_URL = PropertyConfigurerUtils.getString("mobile.authorize.loginUrl");

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
	public static final String VERIFYCODE_TEMPLATE = PropertyConfigurerUtils.getString("verifycode.template");
	/* session 缓存过期时间 */
	//public static final int    SESSION_CACHE_EXPRIED = CacheService.DAY * 30;

	/**
	 * =============== 文件上传目录配置 ===============
	 **/
	/* 文件上传根目录 */
	public static final String UPLOAD_ROOT_DIR               = PropertyConfigurerUtils.getString("upload.root.dir");
	/* 用户头像保存目录 */
	public static final String UPLOAD_MEMBER_IMAGES_DIR      = PropertyConfigurerUtils.getString("upload.member_images.dir");
	/* 商品图片保存目录 */
	public static final String UPLOAD_GOODS_IMAGES_DIR       = PropertyConfigurerUtils.getString("upload.goods_images.dir");
	/* 广告图片保存目录 */
	public static final String UPLOAD_AD_IMAGES_DIR          = PropertyConfigurerUtils.getString("upload.ad_images.dir");
	/* 证件图片保存目录 */
	public static final String UPLOAD_CERTIFICATE_IMAGES_DIR = PropertyConfigurerUtils.getString("upload.certificate_images.dir");

	/* 用户默认头像 */
	public static final String MEMBER_DEFAULT_AVATAR = PropertyConfigurerUtils.getString("member.default.avatar");

	/* 商品搜索默认每页显示个数 */
	// public static final int    GOODS_SEARCH_PERPAGE_DEFAULT_SIZE = Integer.valueOf(PropertyConfigurer.getString("goods.search" + "
	// .perpage_default_size"));
	/*商品搜索每页最大显示个数*/
	// public static final int    GOODS_SEARCH_PERPAGE_MAX_SIZE     = Integer.valueOf(PropertyConfigurer.getString("goods.search.perpage_max_size"));
	/* 商品搜索默认排序字段 */
	public static final String GOODS_SEARCH_DEFAULT_SORT = PropertyConfigurerUtils.getString("goods.search.default_sort");

	/*前端默认分页大小*/
	// public static final int FRONT_DEFAULT_PAGE_LIMIT = Integer.valueOf(PropertyConfigurer.getString("front.default.page_limit"));
	/*全局分页查询每页最大记录数*/
	public static final int GLOBAL_PERPAGE_MAX_LIMIT_SIZE = Integer.valueOf(PropertyConfigurerUtils.getString("global.perpage.max_limit_size"));

	// --------------------------------------------------------动态配置

	/*可申请提现时间区间*/
	public static String WITHDRAW_APPLY_ALLOW_TIME_INTERVAL() {
		return PropertiesService.getInstance().getString("withdraw.apply.allow.time_interval");
	}

	/*单次提现最小金额限制*/
	public static int WITHDRAW_APPLY_SINGLE_MIN_AMOUNT() {return PropertiesService.getInstance().getInteger("withdraw.apply.single.min_amount");}

	/*单次提现最大金额限制*/
	public static int WITHDRAW_APPLY_SINGLE_MAX_AMOUNT() {return PropertiesService.getInstance().getInteger("withdraw.apply.single.max_amount");}

	/*订单未支付超时自动关闭时间, 单位：分钟*/
	public static int ORDER_NOTPAY_TIMEOUT_CLOSE_TIME() {return PropertiesService.getInstance().getInteger("order.notpay.timeout.close_time");}

	/*订单物流已收货超时自动确认收货时间, 单位：天*/
	public static int ORDER_TIMEOUT_CONFIRM_DELIVERY_TIME() {
		return PropertiesService.getInstance().getInteger("order.timeout.confirm_delivery_time");
	}

	/*订单退货申请有效时间, 单位：天*/
	public static int ORDER_REFUND_APPLY_VALID_TIME() {
		return PropertiesService.getInstance().getInteger("order.refund_apply.valid_time");
	}

	/*一级分销商佣金分成比例*/
	public static BigDecimal COMMISSION_DIVIDED_PROPORTION_LV1() {
		return new BigDecimal(PropertiesService.getInstance().getString("commission.divided.proportion.lv1"));
	}

	/*二级分销商佣金分成比例*/
	public static BigDecimal COMMISSION_DIVIDED_PROPORTION_LV2() {
		return new BigDecimal(PropertiesService.getInstance().getString("commission.divided.proportion.lv2"));
	}

	/* 是否开启调试模式 */
	public static boolean DEBUG_AUTH() {
		return PropertiesService.getInstance().getBoolean("debug.auth");
	}

	/* 开发模式下模拟登录用户id */
	public static int DEBUG_AUTH_USERID() {return PropertiesService.getInstance().getInteger("debug.auth.userid");}

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
