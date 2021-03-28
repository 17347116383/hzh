package com.itdy.hqsm.zookeeper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 第一种测试
 * @author Administrator
 *
 */
public class OrderCodeGenerator {

	// 自增长序列
	private static int i = 0;

	// 按照“年-月-日-小时-分钟-秒-自增长序列”的规则生成订单编号
	public String getOrderCode() {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(now) + ++i;
	}
}
