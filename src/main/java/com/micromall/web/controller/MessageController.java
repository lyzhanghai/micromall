package com.micromall.web.controller;

import com.micromall.repository.MessageMapper;
import com.micromall.utils.Condition.Criteria;
import com.micromall.web.resp.ResponseEntity;
import com.micromall.web.security.annotation.Authentication;
import org.apache.ibatis.session.RowBounds;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by zhangzx on 16/3/21.
 * 消息通知
 */
@RestController
@RequestMapping(value = "/api")
@Authentication
public class MessageController extends BasisController {

	@Resource
	private MessageMapper mapper;

	/**
	 * 用户消息列表
	 *
	 * @return
	 */
	@RequestMapping(value = "/message/list")
	public ResponseEntity<?> list(@RequestParam(defaultValue = "1") int page, Integer limit) {
		return ResponseEntity.Success(mapper.selectPageByWhereClause(Criteria.create().andEqualTo("uid", getLoginUser().getUid()).build("id desc"),
				new RowBounds(page, resizeLimit(limit))));
	}
}
