/**
 * 
 */
package com.micromall.payment.dto.common;

/**
 * @ClassName: NotifyType
 * @Description: TODO
 * @author Mountain
 * @date 2015年5月11日 下午7:35:42
 * 
 */
public interface NotifyType {

	String FUND_IN_NOTIFY = "01";// 入款通知

	String REFUND_NOTIFY = "02";// 退款通知

	String DOWNLOAD_FILE_NOTIFY = "03";// 对账文件下载通知
	
	String FUND_IN_FRONT = "04";//入款同步通知
	
	String REFUND_FRONT = "05";//退款同步通知
}
