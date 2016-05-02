package com.micromall.payment.facade;

import com.micromall.payment.dto.*;

/**
 * @author zhangzxiang91@gmail.com
 * @date 2016/04/27.
 */
public interface FundService {

	/**
	 * 用于付款服务
	 *
	 * @param request
	 * @return
	 */
	public PaymentResult payment(PaymentRequest request);

	/**
	 * 用于付款查询服务
	 *
	 * @param request
	 * @return
	 */
	public PaymentResult paymentQuery(PaymentQueryRequest request);

	/**
	 * 用于退款查询服务
	 *
	 * @param request
	 * @return
	 */
	public RefundQueryResult refundQuery(RefundQueryRequest request);

	/**
	 * 用于退款服务
	 *
	 * @param request
	 * @return
	 */
	public RefundResult refund(RefundRequest request);

	/**
	 * 用于转账服务
	 *
	 * @param request
	 * @return
	 */
	TransferResult transfer(TransferRequest request);

	/**
	 * 用于转账查询服务
	 *
	 * @param request
	 * @return
	 */
	TransferQueryResult transferQuery(TransferQueryRequest request);

	/**
	 * 用于验签服务
	 *
	 * @param request
	 * @return
	 */
	public VerityResult verity(VerityRequest request);
}
