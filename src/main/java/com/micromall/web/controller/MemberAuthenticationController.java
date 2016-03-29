package com.micromall.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.micromall.entity.Member;
import com.micromall.service.MemberService;
import com.micromall.service.ShortMessageService;
import com.micromall.utils.CommonEnvConstants;
import com.micromall.utils.CookieUtils;
import com.micromall.utils.HttpUtils;
import com.micromall.utils.UploadUtils;
import com.micromall.web.security.LoginUser;
import com.micromall.web.resp.ResponseEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Created by zhangzx on 16/3/21.
 * 登录授权认证
 */
@Controller
public class MemberAuthenticationController {
	private static final Pattern PHONE_PATTERN = Pattern.compile("^(\\+\\d+)?1[34578]\\d{9}$");
	private static final Random  RANDOM        = new Random();

	private static Logger logger = LoggerFactory.getLogger(MemberAuthenticationController.class);

	@Resource
	private ShortMessageService shortMessageService;
	@Resource
	private MemberService       memberService;

	/**
	 * 发送短信验证码
	 *
	 * @param phone 手机号码
	 * @return
	 */
	@RequestMapping(value = "/auth/verifycode")
	public ResponseEntity<?> verifycode(HttpServletRequest request, String phone) {
		// TODO 参数验证
		if (StringUtils.isEmpty(phone)) {
			return ResponseEntity.fail("请输入手机号码");
		}
		if (!PHONE_PATTERN.matcher(phone).matches()) {
			return ResponseEntity.fail("手机号码输入不正确");
		}

		String verifycode = String.valueOf((RANDOM.nextInt(9000) + 1000));
		boolean sendResult = false;
		try {
			sendResult = shortMessageService.sendMessage(phone, String.format(CommonEnvConstants.VERIFYCODE_TEMPLATE, verifycode));
			if (sendResult) {
				request.getSession().setAttribute(CommonEnvConstants.VERIFYCODE_KEY + ":" + phone, verifycode);
				/*cacheService.set(CommonEnvConstants.VERIFYCODE_KEY, phone, verifycode, CacheService.MINUTE * 5);*/
			}
			// logger.warn("发送短信验证码失败，result={}", sendResult);
		} catch (Exception e) {
			logger.warn("发送短信验证码出错：", e);
		}
		return ResponseEntity.ok(sendResult);
	}

	/**
	 * 短信验证码登录（如用户未注册，则自动注册）
	 *
	 * @param phone      手机号
	 * @param verifycode 验证码
	 * @return
	 */
	@RequestMapping(value = "/auth/loginVerify")
	public ResponseEntity<?> loginVerify(HttpServletRequest request, HttpServletResponse response, String phone, String verifycode) {

		if (StringUtils.isEmpty(phone)) {
			return ResponseEntity.fail("请输入手机号码");
		}
		if (StringUtils.isEmpty(verifycode)) {
			return ResponseEntity.fail("请输入验证码");
		}

		String verifycodeKey = CommonEnvConstants.VERIFYCODE_KEY + ":" + phone;
		try {
			// 短信验证码验证
			/*String _verifycode = cacheService.get(CommonEnvConstants.VERIFYCODE_KEY, phone);*/
			String _verifycode = (String) request.getSession().getAttribute(verifycodeKey);
			if (_verifycode == null) {
				return ResponseEntity.fail("短信验证码已失效");
			} else if (!_verifycode.equals(verifycode)) {
				return ResponseEntity.fail("验证码输入错误");
			}
			/*cacheService.del(CommonEnvConstants.VERIFYCODE_KEY, phone);*/
			request.getSession().removeAttribute(verifycodeKey);

			Member member = memberService.findByPhone(phone);
			// 用户未绑定，进行用户绑定操作
			if (member == null) {
				try {
					member = memberService.registerForPhone(phone);
				} catch (Exception e) {
					member = memberService.findByPhone(phone);
					if (member == null) {
						logger.error("手机用户[{}]注册失败", phone);
						throw e;
					}
				}
			}

			_processLogin(LoginUser.LoginType.Phone, member, request, response);

			return ResponseEntity.ok();
		} catch (Exception e) {
			logger.error("手机号登录授权后台处理出错：", e);
			return ResponseEntity.fail("登录失败");
		}
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
				org.springframework.http.ResponseEntity<String> responseEntity = HttpUtils.executeRequest(CommonEnvConstants
						.WEIXIN_ACCESS_TOKEN_URL, _params, String.class);
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

			Member member = memberService.findByWechatId(openid);
			// 用户未绑定，进行用户绑定操作
			if (member == null) {
				try {
					member = memberService.registerForWechatId(openid);
				} catch (Exception e) {
					member = memberService.findByWechatId(openid);
					if (member == null) {
						logger.error("微信用户[{}]注册失败：", openid);
						throw e;
					}
				}

				try {
					_params.clear();
					_params.put("access_token", access_token);
					_params.put("openid", openid);
					_params.put("lang", "zh_CN");

					org.springframework.http.ResponseEntity<String> responseEntity = HttpUtils.executeRequest(CommonEnvConstants
							.WEIXIN_USERINFO_URL, _params, String.class);
					if (responseEntity.getStatusCode() == HttpStatus.OK && StringUtils.isNotEmpty(responseEntity.getBody())) {
						JSONObject jsonObject = JSON.parseObject(responseEntity.getBody());
						member.setNickname(jsonObject.getString("nickname"));
						String avatar = UploadUtils.upload(CommonEnvConstants.UPLOAD_MEMBER_IMAGES_DIR, jsonObject.getString("headimgurl"));
						member.setAvatar(avatar);
						if (StringUtils.isEmpty(avatar)) {
							member.setAvatar(CommonEnvConstants.MEMBER_DEFAULT_AVATAR);
						}
						String gender = jsonObject.getString("sex");
						member.setGender("1".equals(gender) ? "男" : "1".equals(gender) ? "女" : null);
						memberService.updateBasisInfo(member);
					}
				} catch (Exception e) {
					logger.error("获取微信用户信息出错：", e);
				}
			}

			_processLogin(LoginUser.LoginType.WeChat, member, request, response);

			// 跳转到来源页面，默认首页
			String redirect_url = request.getParameter("return_url");
			if (StringUtils.isNotEmpty(redirect_url)) {
				response.sendRedirect(redirect_url);
			} else {
				response.sendRedirect(CommonEnvConstants.WEIXIN_AUTH_SUCCESS_REDIRECT_URL);
			}
		} catch (Exception e) {
			logger.error("微信登录授权后台处理出错：", e);
			response.sendRedirect(CommonEnvConstants.SERVER_ERROR_REDIRECT_URL);
		}
	}


	private void _processLogin(LoginUser.LoginType loginType, Member member, HttpServletRequest request, HttpServletResponse response) {
		LoginUser loginUser = LoginUser.create(loginType, member, request);

		// 更新登录数据
		member.setLastLoginIp(loginUser.getLoginIp());
		member.setLastLoginTime(loginUser.getLoginTime());
		memberService.updateLoginInfo(member);

		// 保存会话session，重定向到用户最开始访问的页面
		String sid = UUID.randomUUID().toString().replace("-", "");
		CookieUtils.addCookie(response, CommonEnvConstants.LOGIN_SESSION_COOKIE_SID, sid);
		/*cacheService.set(CommonEnvConstants.LOGIN_SESSION_KEY, sid, loginUser, CommonEnvConstants.SESSION_CACHE_EXPRIED);*/
		request.getSession().setAttribute(CommonEnvConstants.LOGIN_SESSION_KEY, loginUser);
	}
}