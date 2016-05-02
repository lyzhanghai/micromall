package com.micromall.repository.entity.common;

/**
 * 佣金提现渠道
 *
 * @author zhangzxiang91@gmail.com
 * @date 2016/04/09.
 */
public class WithdrawChannels {

	// 提现到微信
	public static final String WECHAT = "WECHAT";

	public static boolean support(String channel) {
		return WECHAT.equals(channel);
	}
}
