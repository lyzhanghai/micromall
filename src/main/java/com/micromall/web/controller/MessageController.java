package com.micromall.web.controller;

import com.micromall.service.MessageService;
import com.micromall.web.security.Authentication;
import com.micromall.web.extend.UncaughtException;
import com.micromall.web.resp.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by zhangzx on 16/3/21.
 * 消息通知
 */
@Controller
@RequestMapping(value = "/message")
@Authentication
public class MessageController extends BasisController {
	@Resource
	private MessageService messageService;

	/**
	 * 购物车商品列表
	 *
	 * @return
	 */
	@UncaughtException(msg = "加载消息列表失败")
	@RequestMapping(value = "/list")
	@ResponseBody
	public ResponseEntity<?> list(int p) {
		return ResponseEntity.ok(messageService.list(getLoginUser().getUid(), p));
	}
}
