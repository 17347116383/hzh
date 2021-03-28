package com.itdy.hqsm.elasticsearch;

import java.util.List;

import org.springframework.data.domain.Pageable;
/**
 * @ProjectName: hqsm-parent
 * @Package: com.itdy.hqsm.elasticsearch
 * @ClassName: VehicleService
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/11/21 17:57
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public interface VehicleService {

    public void set(VehicleEntity dto);

    public void setAll(Iterable<VehicleEntity> ite);

    public void del(Long id);

    public VehicleEntity findById(Long id);

    public List<VehicleEntity> findAll();

    public List<VehicleEntity> findCarDriver(String carDriver,Pageable pageable);

    public List<VehicleEntity> findByAddressPointDtoName(String name,Pageable pageable);

    public List<VehicleEntity> findByCarDriverAndPrice(String carDriver,int price,Pageable pageable);

    public List<VehicleEntity> findByCarDriverOrCarType(String carDriver,String carType,Pageable pageable);

    public List<VehicleEntity> findByPriceBetween(int price1, int price2);

}
