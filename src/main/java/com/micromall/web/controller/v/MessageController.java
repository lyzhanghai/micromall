package com.micromall.web.controller.v;

import com.micromall.repository.MessageMapper;
import com.micromall.utils.CommonEnvConstants;
import com.micromall.utils.Condition.Criteria;
import com.micromall.web.controller.BasisController;
import com.micromall.web.extend.UncaughtException;
import com.micromall.web.resp.ResponseEntity;
import com.micromall.web.security.Authentication;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	private MessageMapper mapper;

	/**
	 * 用户消息列表
	 *
	 * @return
	 */
	@UncaughtException(msg = "加载消息列表失败")
	@RequestMapping(value = "/list")
	@ResponseBody
	public ResponseEntity<?> list(@RequestParam(defaultValue = "1") int page) {
		return ResponseEntity.ok(mapper
				.selectPageByWhereClause(Criteria.create().andEqualTo("uid", getLoginUser().getUid()).build("id desc"),
						new RowBounds(page, CommonEnvConstants.FRONT_DEFAULT_PAGE_LIMIT)));
	}
}
