package com.itdy.hqsm.elasticsearch;
import java.util.List;
import java.util.Optional;

import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: hqsm-parent
 * @Package: com.itdy.hqsm.elasticsearch
 * @ClassName: VehicleRepository
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/11/21 17:50
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
@Component
public interface VehicleRepository  extends ElasticsearchRepository<VehicleEntity, Long> {

    @Override
    Iterable<VehicleEntity> findAllById(Iterable<Long> longs);
    @Override
    Iterable<VehicleEntity> search(QueryBuilder query);
    @Override
    Page<VehicleEntity> search(QueryBuilder query, Pageable pageable);
    @Override
    Page<VehicleEntity> search(SearchQuery searchQuery);
    @Override
    <S extends VehicleEntity> Iterable<S> saveAll(Iterable<S> entities);
    @Override
    Page<VehicleEntity> searchSimilar(VehicleEntity entity, String[] fields, Pageable pageable);
    @Override
    Class<VehicleEntity> getEntityClass();
    @Override
    <S extends VehicleEntity> S save(S entity);
    @Override
    Iterable<VehicleEntity> findAll();
    @Override
    Optional<VehicleEntity> findById(Long aLong);
    @Override
    Iterable<VehicleEntity> findAll(Sort sort);
    @Override
    Page<VehicleEntity> findAll(Pageable pageable);
    @Override
    <S extends VehicleEntity> S index(S entity);




    /**
     *
     * @param carDriver
     * @param pageable
     * @return
     */
    List<VehicleEntity> findByCarDriver(String carDriver,Pageable pageable);

    /**
     *
     * @param name
     * @param pageable
     * @return
     */
    List<VehicleEntity> findByAddressPointDtoName(String name,Pageable pageable);

    /**
     *
     * @param carDriver
     * @param price
     * @param pageable
     * @return
     */
    List<VehicleEntity> findByCarDriverAndPrice(String carDriver,int price,Pageable pageable);

    /**
     *
     * @param carDriver
     * @param carType
     * @param pageable
     * @return
     */
    List<VehicleEntity> findByCarDriverOrCarType(String carDriver,String carType,Pageable pageable);

    /**
     * @param price1
     * @param price2
     * @return
     */
    List<VehicleEntity> findByPriceBetween(int price1, int price2);


}
