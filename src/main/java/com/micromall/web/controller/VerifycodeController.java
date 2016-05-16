package com.micromall.web.controller;

import com.micromall.service.ShortMessageService;
import com.micromall.utils.CommonEnvConstants;
import com.micromall.utils.ValidateUtils;
import com.micromall.web.resp.ResponseEntity;
import com.micromall.web.security.annotation.Authentication;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Random;

/**
 * @author zhangzxiang91@gmail.com
 * @date 2016/04/19.
 */
@RestController
@RequestMapping(value = "/api")
@Authentication(force = false)
@Deprecated
public class VerifycodeController {

	private static final Random RANDOM = new Random();

	private static Logger logger = LoggerFactory.getLogger(VerifycodeController.class);

	@Resource
	private ShortMessageService shortMessageService;

	/**
	 * 发送短信验证码
	 *
	 * @param phone 手机号码
	 * @return
	 */
	@RequestMapping(value = "/verifycode/send")
	public ResponseEntity<?> verifycodeSend(HttpServletRequest request, String phone) {
		if (StringUtils.isEmpty(phone)) {
			return ResponseEntity.Failure("请输入手机号码");
		}
		if (ValidateUtils.illegalMobilePhoneNumber(phone)) {
			return ResponseEntity.Failure("手机号码不正确");
		}

		try {
			String verifycode = String.valueOf((RANDOM.nextInt(9000) + 1000));
			boolean sendResult = shortMessageService.sendMessage(phone, String.format(CommonEnvConstants.VERIFYCODE_TEMPLATE, verifycode));
			if (CommonEnvConstants.ENV.isDevEnv()) {
				request.getSession().setAttribute(CommonEnvConstants.VERIFYCODE_KEY, verifycode);
				return ResponseEntity.Success(verifycode);
			} else if (sendResult) {
				request.getSession().setAttribute(CommonEnvConstants.VERIFYCODE_KEY, verifycode);
				return ResponseEntity.Success();
			}
		} catch (Exception e) {
			logger.error("发送短信验证码出错：", e);
		}
		return ResponseEntity.Failure("验证码发送失败");
	}
}
