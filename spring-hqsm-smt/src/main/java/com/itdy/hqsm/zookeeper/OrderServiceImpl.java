package com.itdy.hqsm.zookeeper;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 
 * 第一种测试
 * @author Administrator
 *
 */
public class OrderServiceImpl implements Runnable {

	private static OrderCodeGenerator ong = new OrderCodeGenerator();

	private Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	// 同时并发的线程数
	private static final int NUM = 60;
	// 按照线程数初始化倒计数器,倒计数器
	private static CountDownLatch cdl = new CountDownLatch(NUM);
	private Lock lock = new DistributedLock();

	/**
	 * 创建订单接口
	 * 
	 */
	public void createOrder() {
		String orderCode = null;
		// 准备获取锁
		lock.lock();
		try {
			// 获取订单编号
			orderCode = ong.getOrderCode();
		} catch (Exception e) {
		} finally {
			// 完成业务逻辑以后释放锁
			lock.unlock();
		}
		// -----------------业务代码-------------
		logger.info("insert into DB使用id：=======================>" + orderCode);
	}

	public void run() {
		try {
			// 等待其他线程初始化
			cdl.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 创建订单
		createOrder();
	}

	/**
	 * 
	 * 
	 * 测试方法
	 * 
	 * @param args
	 */
	/*public static void main(String[] args) {
		for (int i = 1; i <= NUM; i++) {
			// 按照线程数迭代实例化线程
			new Thread(new OrderServiceImpl()).start();
			// 创建一个线程，倒计数器减1
			cdl.countDown();
		}
	}*/
}
