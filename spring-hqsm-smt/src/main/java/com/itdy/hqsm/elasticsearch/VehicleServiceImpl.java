package com.itdy.hqsm.elasticsearch;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.SimpleQueryStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Lists;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;


/**
 * @ProjectName: hqsm-parent
 * @Package: com.itdy.hqsm.elasticsearch
 * @ClassName: VehicleServiceImpl
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/11/21 17:59
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private  VehicleRepository vehicleRepository;

    /**
     *
     * @param dto
     */
    public void set(VehicleEntity dto) {
        vehicleRepository.save(dto);
    }

    /**
     *
     * @param ite
     */
    public void setAll(Iterable<VehicleEntity> ite) {
//		vehicleRepository.save(ite);
        vehicleRepository.saveAll(ite);
    }

    /**
     *删除
     * @param id
     */
    public void del(Long id) {
        vehicleRepository.deleteById(id);
    }


    /**
     *
     * @param id
     * @return
     */
    public VehicleEntity findById(Long id) {
//		VehicleEsDto dto = vehicleRepository.findOne(id);
//		return dto;
        Optional<VehicleEntity> dto = vehicleRepository.findById(id);
        return dto.get();
    }

    /**
     * 查询
     * @return
     */
    public List<VehicleEntity> findAll() {
        //分页
        Pageable pageable = PageRequest.of(0,50);
        String carDriver=null;
        //
        List<VehicleEntity> byCarDriverAndPrice = vehicleRepository.findByCarDriverAndPrice(carDriver, 0, pageable);
        List<VehicleEntity> list = Lists.newArrayList(vehicleRepository.findAll());
        return list;
    }

    /**
     *
     * @param carDriver
     * @param pageable
     * @return
     */
    public List<VehicleEntity> findCarDriver(String carDriver, Pageable pageable) {
       // QueryBuilder q =new SimpleQueryStringBuilder() ;

        NativeSearchQuery nativeSearchQuery =new NativeSearchQueryBuilder().withQuery(
                QueryBuilders.queryStringQuery("n实打实的宣传是输入不是").defaultField("title")
        ).withPageable(PageRequest.of(0,50)).build();

        List<VehicleEntity> list = vehicleRepository.findByCarDriver(carDriver, pageable);
        return list;
    }

    /**
     *
     * @param carDriver
     * @param price
     * @param pageable
     * @return
     */
    public List<VehicleEntity> findByCarDriverAndPrice(String carDriver, int price, Pageable pageable) {
        List<VehicleEntity> list = vehicleRepository.findByCarDriverAndPrice(carDriver, price, pageable);
        return list;
    }

    /**
     *
     * @param carDriver
     * @param carType
     * @param pageable
     * @return
     */
    public List<VehicleEntity> findByCarDriverOrCarType(String carDriver, String carType, Pageable pageable) {
        List<VehicleEntity> list = vehicleRepository.findByCarDriverOrCarType(carDriver, carType, pageable);
        return list;
    }

    /**
     *
     * @param price1
     * @param price2
     * @return
     */
    public List<VehicleEntity> findByPriceBetween(int price1, int price2) {
        List<VehicleEntity> list = vehicleRepository.findByPriceBetween(price1, price2);
        return list;
    }

    /**
     *
     * @param name
     * @param pageable
     * @return
     */
    public List<VehicleEntity> findByAddressPointDtoName(String name,Pageable pageable) {
        List<VehicleEntity> byAddressPointDtoName = vehicleRepository.findByAddressPointDtoName(name, pageable);
        return byAddressPointDtoName;
    }


}
