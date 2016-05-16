package com.micromall.service;

import com.micromall.repository.CommissionRecordMapper;
import com.micromall.repository.OrderGoodsMapper;
import com.micromall.repository.OrderMapper;
import com.micromall.repository.entity.CommissionRecord;
import com.micromall.repository.entity.Member;
import com.micromall.repository.entity.Order;
import com.micromall.repository.entity.OrderGoods;
import com.micromall.repository.entity.common.OrderStatus;
import com.micromall.repository.entity.common.OrderStatus.RefundStatus;
import com.micromall.service.vo.CreateOrder;
import com.micromall.service.vo.ListViewOrder;
import com.micromall.service.vo.LogisticsInfo;
import com.micromall.utils.CommonEnvConstants;
import com.micromall.utils.Condition.Criteria;
import com.micromall.utils.LogicException;
import com.micromall.utils.OrderNumberUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhangzx on 16/3/26.
 */
@Service
@Transactional
public class OrderService {

	private static ExecutorService executor = Executors.newFixedThreadPool(1);
	@Resource
	MemberService memberService;
	@Resource
	private OrderMapper            orderMapper;
	@Resource
	private OrderGoodsMapper       orderGoodsMapper;
	@Resource
	private CommissionRecordMapper commissionRecordMapper;
	@Resource
	private CashAccountService     cashAccountService;

	// [待付款、待发货、待收货、已完成、退货/取消]
	@Transactional(readOnly = true)
	public List<ListViewOrder> findOrders(int uid, int status, int page, int limit) {
		Criteria criteria = Criteria.create();
		criteria.andEqualTo("uid", uid);
		if (status == OrderStatus.待支付 || status == OrderStatus.待发货 || status == OrderStatus.待收货 || status == OrderStatus.已收货) {
			criteria.andEqualTo("status", status);
		} else {
			// criteria.andGreaterThanOrEqualTo("status", OrderStatus.已退款);
			criteria.andIn("status", Arrays.asList(OrderStatus.已退款, OrderStatus.已关闭));
		}
		List<ListViewOrder> orders = orderMapper.selectListViewPageByWhereClause(criteria.build("create_time desc"), new RowBounds(page, limit));
		for (ListViewOrder viewOrder : orders) {
			if (viewOrder.getStatus() != OrderStatus.待支付) {
				viewOrder.setTimeoutCloseTime(null);
			}
			viewOrder.setCanApplyRefund(canApplyRefund(viewOrder.getStatus(), viewOrder.getRefundStatus(), viewOrder.getConfirmDeliveryTime()));
			viewOrder.setGoodsList(
					orderGoodsMapper.selectMultiByWhereClause(Criteria.create().andEqualTo("order_no", viewOrder.getOrderNo()).build()));
		}
		return orders;
	}

	@Transactional(readOnly = true)
	public Order getOrderDetails(int uid, String orderNo) {
		Order order = orderMapper.selectOneByWhereClause(Criteria.create().andEqualTo("uid", uid).andEqualTo("order_no", orderNo).build());
		if (order == null) {
			throw new LogicException("订单不存在");
		}
		order.setCanApplyRefund(canApplyRefund(order.getStatus(), order.getRefundStatus(), order.getConfirmDeliveryTime()));
		order.setGoodsList(orderGoodsMapper.selectMultiByWhereClause(Criteria.create().andEqualTo("order_no", orderNo).build()));
		return order;
	}

	private boolean canApplyRefund(Integer status, Integer refundStatus, Date confirmDeliveryTime) {
		if (status != OrderStatus.待发货 || status != OrderStatus.待收货 || status != OrderStatus.已收货 && (refundStatus != RefundStatus.初始状态
				|| refundStatus != RefundStatus.拒绝退款)) {
			return false;
		}

		// 退货期限
		if (status == OrderStatus.已收货) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(confirmDeliveryTime);
			calendar.add(Calendar.DATE, CommonEnvConstants.ORDER_REFUND_APPLY_VALID_TIME);
			if (calendar.getTime().getTime() <= new Date().getTime()) {
				return false;
			}
		}
		return true;
	}

	@Transactional(readOnly = true)
	public LogisticsInfo getLogisticsInfo(String orderNo) {

		return null;
	}

	public Order createOrder(CreateOrder createOrder) {
		Order order = new Order();
		// 下单
		order.setUid(createOrder.getUid());
		order.setTotalAmount(createOrder.getTotalAmount());
		order.setRealpayAmount(createOrder.getRealpayAmount());
		order.setBalancepayAmount(createOrder.getBalancepayAmount());
		order.setDeductionAmount(createOrder.getDeductionAmount());
		order.setFreight(createOrder.getFreight());
		order.setDiscounts(createOrder.getDiscounts());
		order.setCoupons(createOrder.getCoupons());
		order.setLeaveMessage(createOrder.getLeaveMessage());
		order.setShippingAddress(createOrder.getShippingAddress());
		order.setConsigneeName(createOrder.getConsigneeName());
		order.setConsigneePhone(createOrder.getConsigneePhone());
		order.setPostcode(createOrder.getPostcode());
		// 系统
		order.setOrderNo(OrderNumberUtils.generateNumber());
		order.setStatus(OrderStatus.待支付);
		order.setRefundStatus(RefundStatus.初始状态);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(order.getCreateTime());
		calendar.add(Calendar.MINUTE, CommonEnvConstants.ORDER_NOTPAY_TIMEOUT_CLOSE_TIME);
		order.setTimeoutCloseTime(calendar.getTime());
		order.setCreateTime(new Date());
		orderMapper.insert(order);

		for (OrderGoods orderGoods : createOrder.getGoodsList()) {
			orderGoods.setOrderNo(order.getOrderNo());
			orderGoods.setCreateTime(new Date());
			orderGoodsMapper.insert(orderGoods);
		}
		return order;
	}

	public boolean refundProcess(String orderNo, boolean agreed) {
		Order order = orderMapper.selectOneByWhereClause(Criteria.create().andEqualTo("order_no", orderNo).build());
		if (order == null) {
			throw new LogicException("订单不存在");
		}
		if (!(order.getRefundStatus() == RefundStatus.申请退款 && (order.getStatus() == OrderStatus.待发货 || (order.getStatus() == OrderStatus.待收货) || (
				order.getStatus() == OrderStatus.已收货)))) {
			throw new LogicException("订单状态异常");
		}

		Order _ModifyOrder = createModifyOrder(order);
		if (agreed) {
			_ModifyOrder.setStatus(OrderStatus.已退款); // TODO 这里其实应该有一个[退款中]的中间状态
			_ModifyOrder.setRefundStatus(RefundStatus.同意退款);
			_ModifyOrder.setRefundCompleteTime(new Date());
		} else {
			_ModifyOrder.setRefundStatus(RefundStatus.拒绝退款);
		}
		return orderMapper.updateByPrimaryKey(_ModifyOrder) > 0;
	}

	public boolean delivery(String orderNo, String deliveryCompany, String deliveryCode, String deliveryNumber) {
		Order order = orderMapper.selectOneByWhereClause(Criteria.create().andEqualTo("order_no", orderNo).build());
		if (order == null) {
			throw new LogicException("订单不存在");
		}
		if (order.getStatus() != OrderStatus.待发货) {
			throw new LogicException("订单状态异常");
		}

		Order _ModifyOrder = createModifyOrder(order);
		_ModifyOrder.setStatus(OrderStatus.待收货);
		_ModifyOrder.setDeliveryCompany(deliveryCompany);
		_ModifyOrder.setDeliveryCode(deliveryCode);
		_ModifyOrder.setDeliveryNumber(deliveryNumber);
		_ModifyOrder.setDeliveryTime(new Date());
		return orderMapper.updateByPrimaryKey(_ModifyOrder) > 0;
	}

	private Order createModifyOrder(Order order) {
		Order _ModifyOrder = new Order();
		_ModifyOrder.setId(order.getId());
		_ModifyOrder.setUpdatTime(new Date());
		return _ModifyOrder;
	}

	public boolean paySuccess(String orderNo) {
		Order order = orderMapper.selectOneByWhereClause(Criteria.create().andEqualTo("order_no", orderNo).build());
		if (order == null) {
			throw new LogicException("订单不存在");
		}
		if (order.getStatus() != OrderStatus.待发货) {
			throw new LogicException("订单状态异常");
		}

		Order _ModifyOrder = createModifyOrder(order);
		_ModifyOrder.setStatus(OrderStatus.待发货);
		_ModifyOrder.setPayTime(new Date());
		boolean success = orderMapper.updateByPrimaryKey(_ModifyOrder) > 0;
		if (success) {
			final BigDecimal orderAmount = order.getTotalAmount();
			executor.execute(() -> {
				Member member = memberService.get(order.getUid());
				if (member == null || member.getParentUid() == null) {
					return;
				}

				// 一级分销商商佣金计算
				Member lv1 = memberService.get(member.getParentUid());
				if (lv1 == null || lv1.isDeleted()) {
					return;
				}
				cashAccountService.incrementTotalSales(lv1.getId(), orderAmount);

				if (lv1.getParentUid() != null) {
					// 二级分销商商佣金计算
					Member lv2 = memberService.get(lv1.getParentUid());
					if (lv2 == null || lv2.isDeleted()) {
						return;
					}
					cashAccountService.incrementTotalSales(lv2.getId(), orderAmount);
				}
			});
		}
		return success;
	}

	public boolean confirmDelivery(int uid, String orderNo) {
		Order order = orderMapper.selectOneByWhereClause(Criteria.create().andEqualTo("uid", uid).andEqualTo("order_no", orderNo).build());
		if (order == null) {
			throw new LogicException("订单不存在");
		}
		if (order.getStatus() != OrderStatus.待收货) {
			throw new LogicException("订单状态异常");
		}

		Order _ModifyOrder = createModifyOrder(order);
		_ModifyOrder.setStatus(OrderStatus.已收货);
		_ModifyOrder.setConfirmDeliveryTime(new Date());
		boolean success = orderMapper.updateByPrimaryKey(_ModifyOrder) > 0;

		if (success) {
			final BigDecimal orderAmount = order.getTotalAmount();
			executor.execute(() -> {
				Member member = memberService.get(uid);
				if (member == null || member.getParentUid() == null) {
					return;
				}

				// 一级分销商商佣金计算
				Member lv1 = memberService.get(member.getParentUid());
				if (lv1 == null || lv1.isDeleted()) {
					return;
				}

				BigDecimal commission = orderAmount.multiply(CommonEnvConstants.COMMISSION_DIVIDED_PROPORTION_LV1).setScale(2, BigDecimal
						.ROUND_DOWN);
				CommissionRecord commissionRecord = new CommissionRecord();
				commissionRecord.setUid(lv1.getId());
				commissionRecord.setLowerUid(uid);
				commissionRecord.setOrderNo(orderNo);
				commissionRecord.setOrderAmount(orderAmount);
				commissionRecord.setCommissionAmount(commission);
				commissionRecord.setCreateTime(new Date());
				commissionRecordMapper.insert(commissionRecord);
				cashAccountService.incrementCommission(lv1.getId(), commissionRecord.getCommissionAmount());

				if (lv1.getParentUid() != null) {
					// 二级分销商商佣金计算
					Member lv2 = memberService.get(lv1.getParentUid());
					if (lv2 == null || lv2.isDeleted()) {
						return;
					}

					commission = orderAmount.multiply(CommonEnvConstants.COMMISSION_DIVIDED_PROPORTION_LV2).setScale(2, BigDecimal.ROUND_DOWN);
					commissionRecord = new CommissionRecord();
					commissionRecord.setUid(lv2.getId());
					commissionRecord.setLowerUid(uid);
					commissionRecord.setOrderNo(orderNo);
					commissionRecord.setOrderAmount(orderAmount);
					commissionRecord.setCommissionAmount(commission);
					commissionRecord.setCreateTime(new Date());
					commissionRecordMapper.insert(commissionRecord);
					cashAccountService.incrementCommission(lv2.getId(), commissionRecord.getCommissionAmount());
				}
			});
		}
		return success;
	}

	public boolean closeOrder(int uid, String orderNo) {
		Order order = orderMapper.selectOneByWhereClause(Criteria.create().andEqualTo("uid", uid).andEqualTo("order_no", orderNo).build());
		if (order == null) {
			throw new LogicException("订单不存在");
		}
		return this.closeOrder(orderNo, "用户主动关闭订单");
	}

	public boolean closeOrder(String orderNo, String closelog) {
		Order order = orderMapper.selectOneByWhereClause(Criteria.create().andEqualTo("order_no", orderNo).build());
		if (order == null) {
			throw new LogicException("订单不存在");
		}
		if (order.getStatus() != OrderStatus.待支付) {
			throw new LogicException("订单状态异常");
		}

		Order _ModifyOrder = createModifyOrder(order);
		_ModifyOrder.setStatus(OrderStatus.已关闭);
		_ModifyOrder.setCloseTime(new Date());
		_ModifyOrder.setCloselog(closelog);
		return orderMapper.updateByPrimaryKey(_ModifyOrder) > 0;
	}

	public boolean applyRefund(int uid, String orderNo, String refundReason) {
		Order order = orderMapper.selectOneByWhereClause(Criteria.create().andEqualTo("uid", uid).andEqualTo("order_no", orderNo).build());
		if (order == null) {
			throw new LogicException("订单不存在");
		}
		if (!canApplyRefund(order.getStatus(), order.getRefundStatus(), order.getConfirmDeliveryTime())) {
			throw new LogicException("订单无法申请退货");
		}

		Order _ModifyOrder = createModifyOrder(order);
		_ModifyOrder.setRefundStatus(RefundStatus.申请退款);
		_ModifyOrder.setApplyRefundTime(new Date());
		_ModifyOrder.setRefundReason(refundReason);
		return orderMapper.updateByPrimaryKey(_ModifyOrder) > 0;
	}
}
