package com.micromall.entity;

import com.micromall.service.vo.Logistics;
import com.micromall.service.vo.OrderCoupon;

import java.util.Date;
import java.util.List;

/**
 * Created by zhangzx on 16/3/23.
 */
public class Order extends IdEntity {
	// 用户ID
	private int               uid;
	// 订单编号
	private String            orderNo;
	// 订单总金额
	private float             totalAmount;
	// 实付金额
	private float             realpayAmount;
	// 优惠劵抵扣金额（优惠劵抵扣金额+实付金额=订单总金额）
	private float             deductionAmount;
	// 使用的优惠劵
	private List<OrderCoupon> coupons;
	// 买家留言
	private String            leaveMessage;
	// 购买的商品信息（商品信息快照，购买数量）
	private List<OrderGoods>  goodses;
	// 支付信息（支付渠道、支付金额、支付时间）
	private PaymentInfo       paymentInfo;
	// <预留>退款信息（退款渠道(人工退款/自动退款)、退款金额、退款时间）
	private RefundInfo        refundInfo;
	// 物流信息
	private Logistics         logistics;
	// 订单状态（待支付、待发货、待收货、已收货、<申请退款、退款完成：预留>、已关闭）
	private int               status;
	// 收货地址信息（省、市、区/县、详细地址）
	private String            deliveryAddress;
	// 收货人姓名
	private String            consigneeName;
	// 收货人电话
	private String            consigneePhone;
	// 订单创建时间
	private Date              createTime;
	// 订单支付时间
	private Date              payTime;
	// 订单发货时间
	private Date              deliveryTime;
	// 订单确认收货时间
	private Date              receiveTime;
	// <预留>订单申请退款时间
	private Date              applyRefundTime;
	// <预留>订单退款完成时间
	private Date              refundCompleteTime;
	// 订单关闭时间
	private Date              closeTime;
	// 修改时间
	private Date              updatTime;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public float getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}

	public float getRealpayAmount() {
		return realpayAmount;
	}

	public void setRealpayAmount(float realpayAmount) {
		this.realpayAmount = realpayAmount;
	}

	public float getDeductionAmount() {
		return deductionAmount;
	}

	public void setDeductionAmount(float deductionAmount) {
		this.deductionAmount = deductionAmount;
	}

	public List<OrderCoupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(List<OrderCoupon> coupons) {
		this.coupons = coupons;
	}

	public String getLeaveMessage() {
		return leaveMessage;
	}

	public void setLeaveMessage(String leaveMessage) {
		this.leaveMessage = leaveMessage;
	}

	public List<OrderGoods> getGoodses() {
		return goodses;
	}

	public void setGoodses(List<OrderGoods> goodses) {
		this.goodses = goodses;
	}

	public PaymentInfo getPaymentInfo() {
		return paymentInfo;
	}

	public void setPaymentInfo(PaymentInfo paymentInfo) {
		this.paymentInfo = paymentInfo;
	}

	public RefundInfo getRefundInfo() {
		return refundInfo;
	}

	public void setRefundInfo(RefundInfo refundInfo) {
		this.refundInfo = refundInfo;
	}

	public Logistics getLogistics() {
		return logistics;
	}

	public void setLogistics(Logistics logistics) {
		this.logistics = logistics;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
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

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
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

	public Date getUpdatTime() {
		return updatTime;
	}

	public void setUpdatTime(Date updatTime) {
		this.updatTime = updatTime;
	}
}
