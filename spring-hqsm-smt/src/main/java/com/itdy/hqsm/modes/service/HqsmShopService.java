package com.itdy.hqsm.modes.service;

import com.itdy.hqsm.modes.entity.HqsmShop;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface HqsmShopService {

    int deleteByPrimaryKey(Long shopId);

    int insert(HqsmShop record);

    HqsmShop selectByPrimaryKey(Long shopId);

    List<HqsmShop> selectAll();

    int updateByPrimaryKey(HqsmShop record);

}
