package com.micromall.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.micromall.repository.entity.common.WithdrawChannels;
import com.micromall.repository.entity.common.WithdrawStatus;
import com.micromall.service.WithdrawService;
import com.micromall.utils.CommonEnvConstants;
import com.micromall.utils.Condition.Criteria;
import com.micromall.web.resp.ResponseEntity;
import com.micromall.web.security.annotation.Authentication;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Calendar;

/**
 * Created by zhangzx on 16/3/28.
 * 提现
 */
@RestController
@RequestMapping(value = "/api")
@Authentication
public class WithdrawController extends BasisController {

	@Resource
	private WithdrawService withdrawService;

	/**
	 * 申请提现
	 *
	 * @param amount 提现金额
	 * @return
	 */
	@RequestMapping(value = "/withdraw/apply")
	public ResponseEntity<?> apply(int amount) {
		if (amount < CommonEnvConstants.WITHDRAW_APPLY_SINGLE_MIN_AMOUNT()) {
			return ResponseEntity.Failure("提现金额不得低于" + CommonEnvConstants.WITHDRAW_APPLY_SINGLE_MIN_AMOUNT() + "元");
		}
		if (amount > CommonEnvConstants.WITHDRAW_APPLY_SINGLE_MAX_AMOUNT()) {
			return ResponseEntity.Failure("提现金额不得大于" + CommonEnvConstants.WITHDRAW_APPLY_SINGLE_MAX_AMOUNT() + "元");
		}
		String channel = "WECHAT";
		if (StringUtils.isEmpty(channel)) {
			return ResponseEntity.Failure("未选择提现渠道");
		}
		if (!WithdrawChannels.support(channel)) {
			return ResponseEntity.Failure("提现渠道不支持或不存在");
		}
		if (StringUtils.isNotEmpty(CommonEnvConstants.WITHDRAW_APPLY_ALLOW_TIME_INTERVAL())) {
			JSONObject jsonObject = JSON.parseObject(CommonEnvConstants.WITHDRAW_APPLY_ALLOW_TIME_INTERVAL());
			Calendar calendar = Calendar.getInstance();
			int _now_day = calendar.get(Calendar.DAY_OF_MONTH);
			int _now_week = calendar.get(Calendar.DAY_OF_WEEK);
			int _now_hour = calendar.get(Calendar.HOUR_OF_DAY);
			switch (_now_week) {
				case Calendar.MONDAY:
				case Calendar.TUESDAY:
				case Calendar.WEDNESDAY:
				case Calendar.THURSDAY:
				case Calendar.FRIDAY:
				case Calendar.SATURDAY:
					--_now_week;
					break;
				case Calendar.SUNDAY:
					_now_week = 7;
					break;
			}

			JSONArray days = jsonObject.getJSONArray("days");
			JSONArray weeks = jsonObject.getJSONArray("weeks");
			String hour = jsonObject.getString("hour");

			if (days != null && !days.isEmpty() && !days.contains(_now_day)) {
				return ResponseEntity.Failure("当前时间不能申请提现");
			}
			if (weeks != null && !weeks.isEmpty() && !weeks.contains(_now_week)) {
				return ResponseEntity.Failure("当前时间不能申请提现");
			}
			if (StringUtils.isNotEmpty(hour)) {
				String[] arrays = hour.split("-");
				int begin = Integer.valueOf(arrays[0]);
				int end = Integer.valueOf(arrays[1]);
				if (!(_now_hour >= begin && _now_hour <= end)) {
					return ResponseEntity.Failure("当前时间不能申请提现");
				}
			}
		}
		withdrawService.applyWithdraw(getLoginUser().getUid(), amount, channel);
		return ResponseEntity.Success();
	}

	/**
	 * 提现记录
	 *
	 * @param status audit:审核中，through:已通过，not_through:未通过
	 * @return
	 */
	@RequestMapping(value = "/withdraw/records")
	public ResponseEntity<?> records(String status) {
		Criteria criteria = Criteria.create().andEqualTo("uid", getLoginUser().getUid());
		if (status != null) {
			switch (status) {
				case "audit":
					criteria.andEqualTo("status", WithdrawStatus.待审核);
					break;
				case "through":
					criteria.andEqualTo("status", WithdrawStatus.审核通过);
					// criteria.andIn("status", Arrays.asList(WithdrawStatus.审核通过, WithdrawStatus.提现成功));
					break;
				case "not_through":
					criteria.andEqualTo("status", WithdrawStatus.审核不通过);
					break;
				default:
					return ResponseEntity.Failure("参数错误");

			}
		}
		return ResponseEntity.Success(withdrawService.records(criteria.build("id desc"), new RowBounds()));
	}

}
