package com.micromall.web.controller;

import com.micromall.repository.entity.CashAccount;
import com.micromall.repository.entity.CertifiedInfo;
import com.micromall.repository.entity.Member;
import com.micromall.repository.entity.common.CertifiedStatus;
import com.micromall.service.CashAccountService;
import com.micromall.service.CertifiedInfoService;
import com.micromall.service.LoginSeesionService;
import com.micromall.service.MemberService;
import com.micromall.utils.CommonEnvConstants;
import com.micromall.utils.Condition.Criteria;
import com.micromall.utils.UploadUtils;
import com.micromall.utils.ValidateUtils;
import com.micromall.web.resp.ResponseEntity;
import com.micromall.web.security.annotation.Authentication;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangzx on 16/3/21.
 * 会员中心
 */
@RestController
@RequestMapping(value = "/api/member")
@Authentication
public class MemberCenterController extends BasisController {

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
	@RequestMapping(value = "/binding_phone")
	@Deprecated
	public ResponseEntity<?> binding_phone(HttpServletRequest request, String phone, String verifycode) {
		if (StringUtils.isEmpty(phone)) {
			return ResponseEntity.Failure("请输入手机号码");
		}
		if (ValidateUtils.illegalMobilePhoneNumber(phone)) {
			return ResponseEntity.Failure("手机号码不正确");
		}
		if (StringUtils.isEmpty(verifycode)) {
			return ResponseEntity.Failure("请输入验证码");
		}

		// 短信验证码验证
		String _verifycode = (String)request.getSession().getAttribute(CommonEnvConstants.VERIFYCODE_KEY);
		if (_verifycode == null) {
			return ResponseEntity.Failure("短信验证码已失效");
		} else if (!_verifycode.equals(verifycode)) {
			return ResponseEntity.Failure("验证码输入错误");
		}
		request.getSession().removeAttribute(CommonEnvConstants.VERIFYCODE_KEY);

		Member member = memberService.findOneByCriteria(Criteria.create().andEqualTo("phone", phone).build());
		if (member != null) {
			return ResponseEntity.Failure("此手机号码已被占用");
		}
		Member _updateMember = new Member();
		_updateMember.setId(getLoginUser().getUid());
		_updateMember.setPhone(phone);
		if (memberService.update(_updateMember)) {
			return ResponseEntity.Success();
		}
		return ResponseEntity.Failure("手机号码绑定失败");
	}

	/**
	 * 登录用户信息获取
	 *
	 * @return
	 */
	@RequestMapping(value = "/userinfo")
	public ResponseEntity<?> userinfo(HttpServletRequest request) {
		Member member = memberService.get(getLoginUser().getUid());
		if (member == null) {
			loginSeesionService.loginout(request, getLoginUser().getUid());
			return ResponseEntity.Failure("获取用户信息失败");
		}

		CashAccount cashAccount = cashAccountService.getCashAccount(member.getId());
		CertifiedInfo certifiedInfo = certifiedInfoService.getCertifiedInfo(member.getId());
		Map<String, Object> data = new HashMap<>();
		data.put("uid", member.getId());
		// data.put("phone", member.getPhone());
		data.put("nickname", member.getNickname());
		data.put("avatar", member.getAvatar());
		data.put("level", member.getLevel());
		data.put("gender", member.getGender());
		data.put("birthday", member.getBirthday());
		data.put("myPromoteCode", member.getMyPromoteCode());
		data.put("commission", cashAccount.getCommission().toPlainString());
		data.put("totalSales", cashAccount.getTotalSales().toPlainString());
		data.put("certifiedInfo", certifiedInfo);
		return ResponseEntity.Success(data);
	}

	/**
	 * 基础信息修改
	 *
	 * @param nickname 昵称
	 * @param gender 性别（1:男，0:女）
	 * @param birthday 生日，格式：1991-09-17
	 * @return
	 */
	@RequestMapping(value = "/update_basisinfo")
	public ResponseEntity<?> update_basisinfo(String nickname, String gender, String birthday) {
		if (StringUtils.isEmpty(nickname)) {
			ResponseEntity.Failure("昵称不能为空");
		}
		if (ValidateUtils.illegalTextLength(1, 15, nickname)) {
			ResponseEntity.Failure("昵称长度超过限制");
		}
		if (StringUtils.isNotEmpty(gender) && !"1".equals(gender) && !"0".equals(gender)) {
			ResponseEntity.Failure("性别输入不正确");
		}
		if (StringUtils.isNotEmpty(birthday) && ValidateUtils.illegalBirthday(birthday)) {
			ResponseEntity.Failure("生日输入不正确");
		}

		Member _updateMember = new Member();
		_updateMember.setId(getLoginUser().getUid());
		_updateMember.setNickname(nickname);
		_updateMember.setGender(StringUtils.trimToNull(gender));
		_updateMember.setBirthday(StringUtils.trimToNull(birthday));
		if (memberService.update(_updateMember)) {
			return ResponseEntity.Success();
		}
		return ResponseEntity.Failure("保存失败");
	}

	/**
	 * 用户头像修改
	 *
	 * @param file 头像文件
	 * @return
	 */
	@RequestMapping(value = "/update_avatar")
	public ResponseEntity<?> update_avatar(MultipartFile file) {
		String avatar = UploadUtils.upload(CommonEnvConstants.UPLOAD_MEMBER_IMAGES_DIR, file);
		if (StringUtils.isNotEmpty(avatar)) {
			Member _updateMember = new Member();
			_updateMember.setId(getLoginUser().getUid());
			_updateMember.setAvatar(avatar);
			if (memberService.update(_updateMember)) {
				return ResponseEntity.Success(avatar);
			}
		}
		return ResponseEntity.Failure("保存头像失败");
	}

	@RequestMapping(value = "/upload_certificate")
	public ResponseEntity<?> upload_certificate(MultipartFile file) {
		String url = UploadUtils.upload(CommonEnvConstants.UPLOAD_CERTIFICATE_IMAGES_DIR, file);
		if (StringUtils.isNotEmpty(url)) {
			return ResponseEntity.Success(url);
		}
		return ResponseEntity.Failure("上传证件失败");
	}

	/**
	 * @param name 姓名
	 * @param phone 手机号
	 * @param idCarNo 身份证号码
	 * @param idCarImage1 身份证正面照
	 * @param idCarImage0 身份证背面照
	 * @return
	 */
	@RequestMapping(value = "/certification")
	public ResponseEntity<?> certification(String name, String phone, String idCarNo, String idCarImage1, String idCarImage0) {
		if (StringUtils.isEmpty(name)) {
			ResponseEntity.Failure("姓名不能为空");
		}
		if (ValidateUtils.illegalTextLength(2, 10, name)) {
			ResponseEntity.Failure("姓名输入不正确");
		}
		if (StringUtils.isEmpty(phone)) {
			return ResponseEntity.Failure("请输入手机号码");
		}
		if (ValidateUtils.illegalMobilePhoneNumber(phone)) {
			return ResponseEntity.Failure("手机号码不正确");
		}
		if (StringUtils.isEmpty(idCarNo)) {
			return ResponseEntity.Failure("请输入身份证号码");
		}
		if (ValidateUtils.illegalIdCarNo(idCarNo)) {
			return ResponseEntity.Failure("身份证号码不正确");
		}
		if (StringUtils.isEmpty(idCarImage1)) {
			return ResponseEntity.Failure("请上传身份证正面照");
		}
		if (StringUtils.isEmpty(idCarImage0)) {
			return ResponseEntity.Failure("请上传身份证背面照");
		}

		CertifiedInfo certifiedInfo = certifiedInfoService.getCertifiedInfo(getLoginUser().getUid());
		boolean exists = false;
		if (certifiedInfo != null) {
			exists = true;
			if (certifiedInfo.getStatus().intValue() == CertifiedStatus.审核通过) {
				return ResponseEntity.Failure("认证信息已经审核通过，不允许修改");
			}
			if (certifiedInfo.getStatus().intValue() == CertifiedStatus.审核中) {
				return ResponseEntity.Failure("认证信息正在审核中，请不要重复提交");
			}
		} else {
			certifiedInfo = new CertifiedInfo();
		}

		certifiedInfo.setName(name);
		certifiedInfo.setPhone(phone);
		certifiedInfo.setIdCarNo(idCarNo);
		certifiedInfo.setIdCarImage1(idCarImage1);
		certifiedInfo.setIdCarImage0(idCarImage0);
		certifiedInfo.setStatus(CertifiedStatus.审核中);

		if (!exists) {
			certifiedInfo.setUid(getLoginUser().getUid());
			certifiedInfo.setCreateTime(new Date());
			certifiedInfoService.insert(certifiedInfo);
		} else {
			certifiedInfo.setAuditlog(null);
			certifiedInfo.setAuditTime(null);
			certifiedInfo.setUpdateTime(new Date());
			if (!certifiedInfoService.update(certifiedInfo)) {
				return ResponseEntity.Failure("提交认证信息失败");
			}
		}
		return ResponseEntity.Success();
	}
}
