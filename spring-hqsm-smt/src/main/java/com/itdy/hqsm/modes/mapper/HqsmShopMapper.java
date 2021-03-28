package com.itdy.hqsm.modes.mapper;

import com.itdy.hqsm.modes.entity.HqsmShop;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HqsmShopMapper {
    int deleteByPrimaryKey(Long shopId);

    int insert(HqsmShop record);

    HqsmShop selectByPrimaryKey(Long shopId);

    List<HqsmShop> selectAll();

    int updateByPrimaryKey(HqsmShop record);
}