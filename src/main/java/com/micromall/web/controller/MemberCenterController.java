package com.micromall.web.controller;

import com.micromall.entity.LoginUser;
import com.micromall.entity.Member;
import com.micromall.service.MemberService;
import com.micromall.utils.CommonEnvConstants;
import com.micromall.web.RequestContext;
import com.micromall.web.resp.ResponseEntity;
import com.micromall.web.resp.Ret;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangzx on 16/3/21.
 * 会员中心
 */
@Controller
@RequestMapping(value = "/member_center")
public class MemberCenterController {

	private static Logger logger = LoggerFactory.getLogger(MemberCenterController.class);

	@Resource
	private MemberService memberService;

	/**
	 * 登录用户信息获取
	 *
	 * @return
	 */
	@RequestMapping(value = "/userinfo")
	public ResponseEntity<?> userinfo() {
		LoginUser loginUser = RequestContext.getLoginUser();
		Member member = memberService.get(loginUser.getUid());
		if (member == null) {
			return new ResponseEntity<Object>(Ret.error, "用户不存在");
		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("uid", member.getId());
		data.put("nickname", member.getNickname());
		data.put("avatar", member.getAvatar());
		data.put("level", member.getLevel());
		// TODO 佣金与销售额计算
		data.put("commission", 0);
		data.put("sales", 0);
		return new ResponseEntity<Object>(Ret.ok, data);
	}


	/**
	 * 绑定手机号
	 *
	 * @param phone      手机号
	 * @param verifycode 验证码
	 * @return
	 */
	@RequestMapping(value = "/auth/bindPhone")
	public ResponseEntity<?> bindPhone(HttpServletRequest request, HttpServletResponse response, String phone, String verifycode) {

		if (StringUtils.isEmpty(phone)) {
			return new ResponseEntity<Object>(Ret.error, "请输入手机号码");
		}
		if (StringUtils.isEmpty(verifycode)) {
			return new ResponseEntity<Object>(Ret.error, "请输入验证码");
		}

		try {
			// 短信验证码验证
			/*String _verifycode = cacheService.get(CommonEnvConstants.VERIFYCODE_KEY, phone);*/
			String _verifycode = (String) request.getSession().getAttribute(CommonEnvConstants.VERIFYCODE_KEY);
			if (_verifycode == null) {
				return new ResponseEntity<Object>(Ret.error, "短信验证码已失效");
			} else if (!_verifycode.equals(verifycode)) {
				return new ResponseEntity<Object>(Ret.error, "验证码输入错误");
			}
			/*cacheService.del(CommonEnvConstants.VERIFYCODE_KEY, phone);*/
			request.getSession().removeAttribute(CommonEnvConstants.VERIFYCODE_KEY);

			Member member = memberService.findByPhone(phone);
			if (member != null) {
				return new ResponseEntity<Object>(Ret.error, "该手机号码已被绑定");
			}
			boolean result = memberService.updatePhone(RequestContext.getLoginUser().getUid(), phone);
			return new ResponseEntity<Object>(Ret.ok, result);
		} catch (Exception e) {
			logger.error("绑定手机号后台处理出错：", e);
			return new ResponseEntity<Object>(Ret.error, "手机号绑定失败");
		}
	}
}
