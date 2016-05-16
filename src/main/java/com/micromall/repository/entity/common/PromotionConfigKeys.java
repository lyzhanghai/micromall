package com.micromall.repository.entity.common;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Objects;

/**
 * 促销配置Keys
 *
 * @author zhangzxiang91@gmail.com
 * @date 2016/04/09.
 */
public class PromotionConfigKeys {

	public static final String PROMOTION_TYPE_KEY = "type";

	public static final String PROMOTION_TYPE_SINGLE_DISCOUNT   = "single:discount";
	public static final String PROMOTION_TYPE_SINGLE_DEPRECIATE = "single:depreciate";

	// single:discount
	// single:depreciate

	/**
	 * 促销类型：
	 * 1、单商品促销
	 * 打折促销> 列如：8.5折
	 * 降价促销> 列如：原价:99，现价:70
	 * <p>
	 * 2、所有商品参与促销
	 * 满减> 列如：订单金额满1000，减100
	 * 满打折> 列如：订单金额满1000，打9.8折，最高优惠100元
	 * 满包邮> 列如：订单金额满200，享受包邮
	 * <p>
	 * 3、部分商品参与促销(只计算参与促销的商品订单金额)
	 * 满减> 列如：参与促销商品订单金额满1000，减100
	 * 满打折> 列如：参与促销商品订单金额满1000，打9.8折，最高优惠100元
	 * 满包邮> 列如：参与促销商品订单金额满200，享受包邮
	 */

	public interface Promotion {

		String getType();

		Map<String, Objects> getParams();

	}

	public class SingleDiscountPromotion implements Promotion {

		private float discount;

		@Override
		public String getType() {
			return "single:discount";
		}

		@Override
		public Map<String, Objects> getParams() {
			Map<String, Object> params = Maps.newHashMap();
			params.put("discount", discount);
			return null;
		}
	}
}
