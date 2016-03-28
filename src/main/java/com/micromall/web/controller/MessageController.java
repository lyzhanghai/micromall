package com.micromall.web.controller;

import com.micromall.web.extend.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zhangzx on 16/3/21.
 * 消息通知
 */
@Controller
@RequestMapping(value = "/message")
@Authentication
public class MessageController extends BasisController {

}
