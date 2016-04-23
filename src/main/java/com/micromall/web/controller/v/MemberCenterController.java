package com.micromall.web.controller.v;

import com.micromall.entity.CertifiedInfo;
import com.micromall.entity.Member;
import com.micromall.entity.CashAccount;
import com.micromall.entity.ext.CertifiedStatus;
import com.micromall.service.CashAccountService;
import com.micromall.service.CertifiedInfoService;
import com.micromall.service.LoginSeesionService;
import com.micromall.service.MemberService;
import com.micromall.utils.CommonEnvConstants;
import com.micromall.utils.Condition.Criteria;
import com.micromall.utils.UploadUtils;
import com.micromall.utils.ValidateUtils;
import com.micromall.web.controller.BasisController;
import com.micromall.web.extend.UncaughtException;
import com.micromall.web.resp.ResponseEntity;
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
	private MemberService        memberService;
	@Resource
	private CertifiedInfoService certifiedInfoService;
	@Resource
	private CashAccountService   cashAccountService;
	@Resource
	private LoginSeesionService  loginSeesionService;

	/**
	 * 绑定手机号
	 *
	 * @param phone 手机号
	 * @param verifycode 验证码
	 * @return
	 */
	@UncaughtException(msg = "手机号码绑定失败")
	@RequestMapping(value = "/binding_phone")
	@ResponseBody
	public ResponseEntity<?> binding_phone(HttpServletRequest request, String phone, String verifycode) {
		if (StringUtils.isEmpty(phone)) {
			return ResponseEntity.fail("请输入手机号码");
		}
		if (ValidateUtils.illegalMobilePhoneNumber(phone)) {
			return ResponseEntity.fail("手机号码不正确");
		}
		if (StringUtils.isEmpty(verifycode)) {
			return ResponseEntity.fail("请输入验证码");
		}

		// 短信验证码验证
		String _verifycode = (String)request.getSession().getAttribute(CommonEnvConstants.VERIFYCODE_KEY);
		if (_verifycode == null) {
			return ResponseEntity.fail("短信验证码已失效");
		} else if (!_verifycode.equals(verifycode)) {
			return ResponseEntity.fail("验证码输入错误");
		}
		request.getSession().removeAttribute(CommonEnvConstants.VERIFYCODE_KEY);

		Member member = memberService.findOneByCriteria(Criteria.create().andEqualTo("phone", phone).build());
		if (member != null) {
			return ResponseEntity.fail("此手机号码已被占用");
		}
		Member _updateMember = new Member();
		_updateMember.setId(getLoginUser().getUid());
		_updateMember.setPhone(phone);
		if (memberService.update(_updateMember)) {
			return ResponseEntity.ok();
		}
		return ResponseEntity.fail("手机号码绑定失败");
	}

	/**
	 * 登录用户信息获取
	 *
	 * @return
	 */
	@UncaughtException(msg = "获取用户信息失败")
	@RequestMapping(value = "/userinfo")
	@ResponseBody
	public ResponseEntity<?> userinfo(HttpServletRequest request) {
		Member member = memberService.get(getLoginUser().getUid());
		if (member == null) {
			loginSeesionService.loginout(request);
			return ResponseEntity.fail("获取用户信息失败");
		}

		CashAccount cashAccount = cashAccountService.getCashAccount(member.getId());
		CertifiedInfo certifiedInfo = certifiedInfoService.getCertifiedInfo(member.getId());
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("uid", member.getId());
		data.put("nickname", member.getNickname());
		data.put("avatar", member.getAvatar());
		data.put("level", member.getLevel());
		data.put("commission", cashAccount.getCommission());
		data.put("totalSales", cashAccount.getTotalSales());
		data.put("certifiedInfo", certifiedInfo);

		return ResponseEntity.ok(data);
	}

	/**
	 * 基础信息修改
	 *
	 * @param nickname 昵称
	 * @param gender 性别（1:男，0:女）
	 * @param birthday 生日，格式：1991-09-17
	 * @return
	 */
	@UncaughtException(msg = "保存用户信息失败")
	@RequestMapping(value = "/update_basisinfo")
	@ResponseBody
	public ResponseEntity<?> update_basisinfo(String nickname, String gender, String birthday) {
		if (StringUtils.isEmpty(nickname)) {
			ResponseEntity.fail("昵称不能为空");
		}
		if (ValidateUtils.illegalTextLength(1, 15, nickname)) {
			ResponseEntity.fail("昵称长度超过限制");
		}
		if (StringUtils.isNotEmpty(gender) && !"1".equals(gender) && !"0".equals(gender)) {
			ResponseEntity.fail("性别输入不正确");
		}
		if (StringUtils.isNotEmpty(birthday) && ValidateUtils.illegalBirthday(birthday)) {
			ResponseEntity.fail("生日输入不正确");
		}

		Member _updateMember = new Member();
		_updateMember.setId(getLoginUser().getUid());
		_updateMember.setNickname(nickname);
		_updateMember.setGender(StringUtils.trimToNull(gender));
		_updateMember.setBirthday(StringUtils.trimToNull(birthday));
		if (memberService.update(_updateMember)) {
			return ResponseEntity.ok();
		}
		return ResponseEntity.fail("保存用户信息失败");
	}

	/**
	 * 用户头像修改
	 *
	 * @param file 头像文件
	 * @return
	 */
	@UncaughtException(msg = "保存用户头像失败")
	@RequestMapping(value = "/update_avatar")
	@ResponseBody
	public ResponseEntity<?> update_avatar(MultipartFile file) {
		String avatar = UploadUtils.upload(CommonEnvConstants.UPLOAD_MEMBER_IMAGES_DIR, file);
		if (StringUtils.isNotEmpty(avatar)) {
			Member _updateMember = new Member();
			_updateMember.setId(getLoginUser().getUid());
			_updateMember.setAvatar(avatar);
			if (memberService.update(_updateMember)) {
				return ResponseEntity.ok();
			}
		}
		return ResponseEntity.fail("保存用户头像失败");
	}

	@UncaughtException(msg = "上传证件信息失败")
	@RequestMapping(value = "/upload_certificate")
	@ResponseBody
	public ResponseEntity<?> upload_certificate(MultipartFile file) {
		String url = UploadUtils.upload(CommonEnvConstants.UPLOAD_CERTIFICATE_IMAGES_DIR, file);
		if (StringUtils.isNotEmpty(url)) {
			return ResponseEntity.ok(url);
		}
		return ResponseEntity.fail("上传证件信息失败");
	}

	/**
	 * @param name 姓名
	 * @param phone 手机号
	 * @param idCarNo 身份证号码
	 * @param idCarImage1 身份证正面照
	 * @param idCarImage0 身份证背面照
	 * @return
	 */
	@UncaughtException(msg = "提交用户认证信息失败")
	@RequestMapping(value = "/certification")
	@ResponseBody
	public ResponseEntity<?> certification(String name, String phone, String idCarNo, String idCarImage1, String idCarImage0) {
		if (StringUtils.isEmpty(name)) {
			ResponseEntity.fail("姓名不能为空");
		}
		if (ValidateUtils.illegalTextLength(2, 10, name)) {
			ResponseEntity.fail("姓名输入不正确");
		}
		if (StringUtils.isEmpty(phone)) {
			return ResponseEntity.fail("请输入手机号码");
		}
		if (ValidateUtils.illegalMobilePhoneNumber(phone)) {
			return ResponseEntity.fail("手机号码不正确");
		}
		if (StringUtils.isEmpty(idCarNo)) {
			return ResponseEntity.fail("请输入身份证号码");
		}
		if (ValidateUtils.illegalIdCarNo(idCarNo)) {
			return ResponseEntity.fail("身份证号码不正确");
		}
		if (StringUtils.isEmpty(idCarImage1)) {
			return ResponseEntity.fail("请上传身份证正面照");
		}
		if (StringUtils.isEmpty(idCarImage0)) {
			return ResponseEntity.fail("请上传身份证背面照");
		}

		CertifiedInfo certifiedInfo = certifiedInfoService.getCertifiedInfo(getLoginUser().getUid());
		boolean exists = true;
		if (certifiedInfo != null) {
			exists = false;
			if (certifiedInfo.getStatus().intValue() == CertifiedStatus.审核通过) {
				return ResponseEntity.fail("认证信息已经审核通过，不允许修改");
			}
			if (certifiedInfo.getStatus().intValue() == CertifiedStatus.审核中) {
				return ResponseEntity.fail("认证信息正在审核中，请不要重复提交");
			}
		}
		certifiedInfo.setUid(getLoginUser().getUid());
		certifiedInfo.setName(name);
		certifiedInfo.setPhone(phone);
		certifiedInfo.setIdCarNo(idCarNo);
		certifiedInfo.setIdCarImage1(idCarImage1);
		certifiedInfo.setIdCarImage0(idCarImage0);
		certifiedInfo.setStatus(CertifiedStatus.审核中);
		if (exists) {
			certifiedInfoService.insert(certifiedInfo);
		} else {
			if (!certifiedInfoService.update(certifiedInfo)) {
				return ResponseEntity.fail("提交用户认证信息失败");
			}
		}
		return ResponseEntity.ok();
	}
}
