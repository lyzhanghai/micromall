package com.micromall.payment.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.micromall.payment.dto.common.NotifyStatus;
import com.micromall.repository.PaymentNotifyMapper;
import com.micromall.repository.entity.PaymentNotify;
import com.micromall.utils.Condition.Criteria;
import com.micromall.utils.HttpUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

@Component
public class PaymentNotifyTask {

	static final   int    MAX_NOTIFY_TIMES = 8;// 通知重试次数
	static final   int    BREAK_TIMEOUT    = 30;// 进入BREAK状态后未执行超时时间
	private static Logger logger           = LoggerFactory.getLogger(PaymentNotifyTask.class);

	@Resource(name = "paymentNotifyTaskThreadPool")
	public  ThreadPoolTaskExecutor executor;
	@Resource
	private PaymentNotifyMapper    paymentNotifyMapper;

	static PaymentNotify createUpdatePaymentNotify(PaymentNotify paymentNotify) {
		PaymentNotify _update_paymentNotify = createUpdatePaymentNotify(paymentNotify);
		_update_paymentNotify.setId(_update_paymentNotify.getId());
		_update_paymentNotify.setVersion(paymentNotify.getVersion());
		_update_paymentNotify.setUpdateTime(new Date());
		return _update_paymentNotify;
	}

	@PostConstruct
	public void init() {
		/*executor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
			@Override
			public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
				if (r instanceof NotifyThread) {
					((NotifyThread)r).cancel();
				}
			}
		});*/
		interruptRestore();
	}

	// 2秒钟执行一次
	@Scheduled(cron = "0/2 * * * * ?")
	public void execute() {
		Criteria criteria = Criteria.create();
		criteria.andEqualTo("status", NotifyStatus.INIT);
		criteria.andLessThan("notifyTimes", MAX_NOTIFY_TIMES);
		criteria.andLessThan("nextNotifyTime", new Date());// 下次通知时间小于当前时间

		// 查询数据库获取需要发送的数据,每次查询50条
		List<PaymentNotify> notifys = paymentNotifyMapper.selectPageByWhereClause(criteria.build("id desc"), new RowBounds(1, 50));
		if (CollectionUtils.isEmpty(notifys)) {
			return;
		}

		for (PaymentNotify paymentNotify : notifys) {
			PaymentNotify _update_paymentNotify = createUpdatePaymentNotify(paymentNotify);
			_update_paymentNotify.setStatus(NotifyStatus.BREAK);
			_update_paymentNotify.setBreakTimeout(new Date(System.currentTimeMillis() + (1000 * 60 * BREAK_TIMEOUT)));
			if (paymentNotifyMapper.updateNotifyInfo(_update_paymentNotify) == 0) {
				// 更新失败说明已经被其他线程处理了
				continue;
			}

			NotifyThread notifyThread = new NotifyThread(paymentNotify.getId(), paymentNotifyMapper);
			try {
				// 加入任务队列
				executor.execute(notifyThread);
			} catch (RejectedExecutionException e) {
				try {
					notifyThread.cancel();
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e1) {
					// ignore
				}
			}
		}
	}

	// 10分钟钟执行一次
	@Scheduled(cron = "00 0/10 * * * ?")
	public void interruptRestore() {
		Criteria criteria = Criteria.create();
		criteria.andEqualTo("status", NotifyStatus.BREAK);
		criteria.andLessThan("breakTimeout", new Date());

		// 查询数据库获取需要发送的数据,每次查询50条
		List<PaymentNotify> notifys = paymentNotifyMapper.selectPageByWhereClause(criteria.build("id desc"), new RowBounds(1, 50));
		if (CollectionUtils.isEmpty(notifys)) {
			return;
		}

		for (PaymentNotify paymentNotify : notifys) {
			PaymentNotify _update_paymentNotify = createUpdatePaymentNotify(paymentNotify);
			_update_paymentNotify.setStatus(NotifyStatus.INIT);
			paymentNotifyMapper.updateNotifyInfo(paymentNotify);
		}
	}
}

class NotifyThread implements Runnable {

	private final static Logger logger = LoggerFactory.getLogger(NotifyThread.class);

	private int notifyId;
	private boolean runing = false;
	private PaymentNotifyMapper paymentNotifyMapper;

	public NotifyThread(int notifyId, PaymentNotifyMapper paymentNotifyMapper) {
		this.notifyId = notifyId;
		this.paymentNotifyMapper = paymentNotifyMapper;
	}

	public void cancel() {
		if (!this.runing) {
			PaymentNotify paymentNotify = paymentNotifyMapper.selectByPrimaryKey(notifyId);
			if (paymentNotify == null || !paymentNotify.getStatus().equals(NotifyStatus.BREAK)) {
				return;
			}
			PaymentNotify _update_paymentNotify = PaymentNotifyTask.createUpdatePaymentNotify(paymentNotify);
			_update_paymentNotify.setStatus(NotifyStatus.INIT);
			paymentNotifyMapper.updateNotifyInfo(_update_paymentNotify);
		}
	}

	public void run() {
		this.runing = true;
		PaymentNotify paymentNotify = paymentNotifyMapper.selectByPrimaryKey(notifyId);
		// 如果订单不是暂停的状态说明有其他线程在调用
		if (paymentNotify == null || !paymentNotify.getStatus().equals(NotifyStatus.BREAK)) {
			return;
		}

		PaymentNotify _update_paymentNotify = PaymentNotifyTask.createUpdatePaymentNotify(paymentNotify);
		_update_paymentNotify.setStatus(NotifyStatus.INIT);// 把订单状态修改成初始状态

		ResponseEntity<String> responseEntity = null;
		try {
			JSONObject param = JSON.parseObject(paymentNotify.getContent());
			param.put("notifyType", paymentNotify.getNotifyType());
			// 发送通知获取结果
			responseEntity = HttpUtils.executeRequest(paymentNotify.getNotifyUrl(), JSON.toJSONString(param), String.class);
		} catch (Exception e) {
			logger.error("发送通知消息异常", e);
		}
		// 判断是否已经接受成功
		if (null != responseEntity && "success".equals(responseEntity.getBody())) {
			_update_paymentNotify.setStatus(NotifyStatus.FINISH);
		} else {
			_update_paymentNotify.setNextNotifyTime(_getNextNotifyTime(paymentNotify.getNotifyTimes()));
			if (paymentNotify.getNotifyTimes() == (PaymentNotifyTask.MAX_NOTIFY_TIMES - 1)) {
				_update_paymentNotify.setStatus(NotifyStatus.MISS);
			}
			_update_paymentNotify.setNotifyTimes(paymentNotify.getNotifyTimes() + 1);
		}

		_update_paymentNotify.setUpdateTime(new Date());
		paymentNotifyMapper.updateNotifyInfo(_update_paymentNotify);
	}

	// 获取下一次通知时间
	private Date _getNextNotifyTime(int times) {
		switch (times) {
			case 0:
				return new Date(System.currentTimeMillis() + 1000 * 300);
			case 1:
				return new Date(System.currentTimeMillis() + 1000 * 1500);
			case 2:
				return new Date(System.currentTimeMillis() + 1000 * 1500);
			case 3:
				return new Date(System.currentTimeMillis() + 3000 * 3600);
			case 4:
			case 5:
			case 6:
				return new Date(System.currentTimeMillis() + 4000 * 3600);
		}
		return null;
	}
}
