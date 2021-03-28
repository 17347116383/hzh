
package com.itdy.hqsm.modes.service.impl;

import com.itdy.hqsm.common.lang.DateUtils;
import com.itdy.hqsm.modes.entity.HqsmShop;
import com.itdy.hqsm.modes.mapper.HqsmShopMapper;
import com.itdy.hqsm.modes.service.HqsmShopService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 *
 * @author
 * @version 2019-05-21
 */
@Service
@Transactional(readOnly = true)
public class HqsmShopServiceImpl implements HqsmShopService {

	@Autowired
	private HqsmShopMapper hqsmShopMapper;


	@Override
	public int deleteByPrimaryKey(Long shopId) {
		return 0;
	}

	@Override
	@Transactional(readOnly = false)
	public int insert(HqsmShop record) {
		record.setShopId(3l);
		record.setCreateDate(DateUtils.formatDate(new Date()));
		return hqsmShopMapper.insert(record);
	}

	@Override
	public HqsmShop selectByPrimaryKey(Long shopId) {

		return hqsmShopMapper.selectByPrimaryKey(shopId);
	}

	@Override
	public List<HqsmShop> selectAll() {
		return null;
	}

	@Override
	public int updateByPrimaryKey(HqsmShop record) {
		return 0;
	}
}