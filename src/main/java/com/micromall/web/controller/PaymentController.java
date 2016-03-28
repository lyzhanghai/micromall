package com.micromall.web.controller;

import com.micromall.web.extend.UncaughtException;
import com.micromall.web.resp.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zhangzx on 16/3/21.
 * 支付
 */
@Controller
@RequestMapping(value = "/payment")
public class PaymentController {

	@UncaughtException(msg = "支付失败")
	@RequestMapping(value = "/pay")
	public ResponseEntity<?> pay(int orderNo) {
		return ResponseEntity.ok(true);
	}
}
