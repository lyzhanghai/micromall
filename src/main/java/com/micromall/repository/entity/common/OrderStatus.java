package com.micromall.repository.entity.common;

/**
 * 订单状态
 *
 * @author zhangzxiang91@gmail.com
 * @date 2016/04/09.
 */
public class OrderStatus {

	public static final int 待支付 = 0;
	public static final int 待发货 = 1;
	public static final int 待收货 = 2;
	public static final int 已收货 = 3;
	public static final int 已退款 = 4;
	public static final int 已关闭 = 5;

	public static class RefundStatus {

		public static final int 初始状态 = 0;
		public static final int 申请退款 = 1;
		public static final int 拒绝退款 = 2;
		public static final int 同意退款 = 3;
	}

}
