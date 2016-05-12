package com.micromall.repository.entity.common;

/**
 * 促销配置Keys
 *
 * @author zhangzxiang91@gmail.com
 * @date 2016/04/09.
 */
public class PromotionConfigKeys {

	public static final String PROMOTION_TYPE_KEY = "PromotionType";

	/**
	 * 促销类型：
	 *  1、单商品促销
	 *      打折促销> 列如：8.5折
	 *      降价促销> 列如：原价:99，现价:70
	 *
	 *  2、所有商品参与促销
	 *      满减> 列如：订单金额满1000，减100
	 *      满打折> 列如：订单金额满1000，打9.8折，最高优惠100元
	 *      满包邮> 列如：订单金额满200，享受包邮
	 *
	 *  3、部分商品参与促销(只计算参与促销的商品订单金额)
	 *      满减> 列如：参与促销商品订单金额满1000，减100
	 *      满打折> 列如：参与促销商品订单金额满1000，打9.8折，最高优惠100元
	 *      满包邮> 列如：参与促销商品订单金额满200，享受包邮
	 */

}
