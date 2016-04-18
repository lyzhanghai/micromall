package com.micromall.web.controller.v;

import com.micromall.service.ShortMessageService;
import com.micromall.utils.CommonEnvConstants;
import com.micromall.utils.ValidateUtils;
import com.micromall.web.resp.ResponseEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Random;

/**
 * @author zhangzxiang91@gmail.com
 * @date 2016/04/19.
 */
@Controller
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
	@RequestMapping(value = "/verifycode")
	@ResponseBody
	public ResponseEntity<?> verifycode(HttpServletRequest request, String phone) {
		if (StringUtils.isEmpty(phone)) {
			return ResponseEntity.fail("请输入手机号码");
		}
		if (ValidateUtils.illegalMobilePhoneNumber(phone)) {
			return ResponseEntity.fail("手机号码不正确");
		}

		String verifycode = String.valueOf((RANDOM.nextInt(9000) + 1000));
		try {
			boolean sendResult = shortMessageService.sendMessage(phone, String.format(CommonEnvConstants.VERIFYCODE_TEMPLATE, verifycode));
			if (sendResult) {
				request.getSession().setAttribute(CommonEnvConstants.VERIFYCODE_KEY + ":" + phone, verifycode);
				/*cacheService.set(CommonEnvConstants.VERIFYCODE_KEY, phone, verifycode, CacheService.MINUTE * 5);*/
				return ResponseEntity.ok();
			}
		} catch (Exception e) {
			logger.warn("发送短信验证码出错：", e);
		}
		return ResponseEntity.fail("短信验证码发送失败");
	}
}
