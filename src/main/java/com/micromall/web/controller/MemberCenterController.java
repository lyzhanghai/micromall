package com.micromall.web.controller;

import com.micromall.entity.Member;
import com.micromall.service.MemberService;
import com.micromall.service.vo.MemberBasisinfo;
import com.micromall.utils.CommonEnvConstants;
import com.micromall.utils.UploadUtils;
import com.micromall.web.RequestContext;
import com.micromall.web.extend.UncaughtException;
import com.micromall.web.resp.ResponseEntity;
import com.micromall.web.resp.Ret;
import com.micromall.web.security.Authentication;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
@Authentication
public class MemberCenterController extends BasisController {

	private static Logger logger = LoggerFactory.getLogger(MemberCenterController.class);

	@Resource
	private MemberService memberService;

	/**
	 * 登录用户信息获取
	 *
	 * @return
	 */
	@UncaughtException(msg = "加载用户信息失败")
	@RequestMapping(value = "/userinfo")
	@ResponseBody
	public ResponseEntity<?> userinfo() {
		Member member = memberService.get(getLoginUser().getUid());
		if (member == null) {
			return new ResponseEntity<Object>(Ret.error, "用户不存在");
		}

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("uid", member.getId());
		data.put("nickname", member.getNickname());
		data.put("avatar", member.getAvatar());
		// TODO 佣金与销售额计算
		data.put("level", 0);
		data.put("commission", 0);
		data.put("sales", 0);

		return ResponseEntity.ok(data);
	}

	/**
	 * 绑定手机号
	 *
	 * @param phone 手机号
	 * @param verifycode 验证码
	 * @return
	 */
	@UncaughtException(msg = "手机号绑定失败")
	@RequestMapping(value = "/auth/bind_phone")
	@ResponseBody
	public ResponseEntity<?> bind_phone(HttpServletRequest request, HttpServletResponse response, String phone, String verifycode) {
		// TODO 参数验证
		if (StringUtils.isEmpty(phone)) {
			return new ResponseEntity<Object>(Ret.error, "请输入手机号码");
		}
		if (StringUtils.isEmpty(verifycode)) {
			return new ResponseEntity<Object>(Ret.error, "请输入验证码");
		}

		try {
			// 短信验证码验证
			/*String _verifycode = cacheService.get(CommonEnvConstants.VERIFYCODE_KEY, phone);*/
			String _verifycode = (String)request.getSession().getAttribute(CommonEnvConstants.VERIFYCODE_KEY);
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

	/**
	 * 基础信息修改
	 *
	 * @param nickname
	 * @param gender
	 * @param birthday
	 * @return
	 */
	@UncaughtException(msg = "保存用户信息失败")
	@RequestMapping(value = "/update_basisinfo")
	@ResponseBody
	public ResponseEntity<?> update_basisinfo(String nickname, String gender, String birthday) {
		// TODO 参数验证
		MemberBasisinfo basisinfo = new MemberBasisinfo(nickname, gender, birthday);
		return ResponseEntity.ok(memberService.updateBasisinfo(getLoginUser().getUid(), basisinfo));
	}

	/**
	 * 用户头像修改
	 *
	 * @param avatarFile
	 * @return
	 */
	@UncaughtException(msg = "保存用户头像失败")
	@RequestMapping(value = "/update_avatar")
	@ResponseBody
	public ResponseEntity<?> update_avatar(MultipartFile avatarFile) {
		String avatar = UploadUtils.upload(CommonEnvConstants.UPLOAD_MEMBER_IMAGES_DIR, avatarFile);
		if (StringUtils.isNotEmpty(avatar)) {
			return ResponseEntity.ok(memberService.updateAvatar(getLoginUser().getUid(), avatar));
		}
		return ResponseEntity.ok(false);
	}
}
