package com.micromall.web.controller;

import com.micromall.web.security.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhangzxiang91@gmail.com
 * @date 2016/04/11.
 */
@Controller
@RequestMapping(value = "/buy")
@Authentication
public class BuyController extends BasisController {

}
