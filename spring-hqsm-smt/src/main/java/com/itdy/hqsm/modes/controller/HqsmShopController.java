package com.itdy.hqsm.modes.controller;

import com.itdy.hqsm.modes.entity.HqsmShop;
import com.itdy.hqsm.modes.service.HqsmShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author
 * @version 2019-05-21
 */
//@CrossOrigin(origins = "http://domain2.com", maxAge = 3600)
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/hqsmShop")
public class HqsmShopController  {

	private static Logger logger = LoggerFactory.getLogger(HqsmShopController.class);

	@Autowired
	private HqsmShopService hqsmShopService;

	@RequestMapping(value = "/add")
	public int insert(HqsmShop record){

		return hqsmShopService.insert(record);
	}

	@RequestMapping(value = "/find")
	HqsmShop selectByPrimaryKey( Long shopId){

		return hqsmShopService.selectByPrimaryKey(shopId);
	}

}