package com.micromall.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhangzx on 16/3/23.
 * 订单信息
 */
public class Order extends IdEntity {

	// 所属用户id
	private Integer    uid;
	// 订单编号
	private String     orderNo;
	// 订单总金额
	private BigDecimal totalAmount;
	// 实付金额
	private BigDecimal realpayAmount;
	// 余额支付金额
	private BigDecimal balancepayAmount;
	// 优惠劵抵扣金额（优惠劵抵扣金额+实付金额=订单总金额）
	private BigDecimal deductionAmount;
	// 使用的优惠劵(JSON)
	private String     coupons;
	// 买家留言
	private String     leaveMessage;
	// 订单商品信息
	// private List<OrderGoods>  list;
	// 支付信息
	// private PaymentInfo       paymentInfo;
	// 退款信息
	// private RefundInfo        refundInfo;
	// 物流信息
	// private List<Logistics> logistics;
	/**
	 * 订单状态 {@link com.micromall.entity.ext.OrderStatus}
	 */
	private Integer    status;
	// 收货地址信息（省、市、区/县、详细地址）
	private String     shippingAddress;
	// 收货人姓名
	private String     consigneeName;
	// 收货人电话
	private String     consigneePhone;
	// 邮政编码
	private String     postcode;

	// 发货快递公司
	private String     deliveryCompany;
	// 发货快递单号
	private String     deliveryNumber;
	// 发货时间
	private Date       deliveryTime;

	// 订单支付时间
	private Date       payTime;
	// 订单确认收货时间
	private Date       confirmGoodsTime;
	// 订单申请退款时间<预留>
	@Deprecated
	private Date       applyRefundTime;
	// 订单退款完成时间<预留>
	@Deprecated
	private Date       refundCompleteTime;
	// 订单关闭时间
	private Date       closeTime;
	// 超时未支付自动关闭时间
	private Date       timeoutCloseTime;
	// 关闭日志
	private String     closelog;
	// 订单创建时间
	private Date       createTime;
	// 修改时间
	private Date       updatTime;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getRealpayAmount() {
		return realpayAmount;
	}

	public void setRealpayAmount(BigDecimal realpayAmount) {
		this.realpayAmount = realpayAmount;
	}

	public BigDecimal getBalancepayAmount() {
		return balancepayAmount;
	}

	public void setBalancepayAmount(BigDecimal balancepayAmount) {
		this.balancepayAmount = balancepayAmount;
	}

	public BigDecimal getDeductionAmount() {
		return deductionAmount;
	}

	public void setDeductionAmount(BigDecimal deductionAmount) {
		this.deductionAmount = deductionAmount;
	}

	public String getCoupons() {
		return coupons;
	}

	public void setCoupons(String coupons) {
		this.coupons = coupons;
	}

	public String getLeaveMessage() {
		return leaveMessage;
	}

	public void setLeaveMessage(String leaveMessage) {
		this.leaveMessage = leaveMessage;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getConsigneePhone() {
		return consigneePhone;
	}

	public void setConsigneePhone(String consigneePhone) {
		this.consigneePhone = consigneePhone;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getDeliveryCompany() {
		return deliveryCompany;
	}

	public void setDeliveryCompany(String deliveryCompany) {
		this.deliveryCompany = deliveryCompany;
	}

	public String getDeliveryNumber() {
		return deliveryNumber;
	}

	public void setDeliveryNumber(String deliveryNumber) {
		this.deliveryNumber = deliveryNumber;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Date getConfirmGoodsTime() {
		return confirmGoodsTime;
	}

	public void setConfirmGoodsTime(Date confirmGoodsTime) {
		this.confirmGoodsTime = confirmGoodsTime;
	}

	public Date getApplyRefundTime() {
		return applyRefundTime;
	}

	public void setApplyRefundTime(Date applyRefundTime) {
		this.applyRefundTime = applyRefundTime;
	}

	public Date getRefundCompleteTime() {
		return refundCompleteTime;
	}

	public void setRefundCompleteTime(Date refundCompleteTime) {
		this.refundCompleteTime = refundCompleteTime;
	}

	public Date getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}

	public Date getTimeoutCloseTime() {
		return timeoutCloseTime;
	}

	public void setTimeoutCloseTime(Date timeoutCloseTime) {
		this.timeoutCloseTime = timeoutCloseTime;
	}

	public String getCloselog() {
		return closelog;
	}

	public void setCloselog(String closelog) {
		this.closelog = closelog;
	}

	public Date getUpdatTime() {
		return updatTime;
	}

	public void setUpdatTime(Date updatTime) {
		this.updatTime = updatTime;
	}

}
