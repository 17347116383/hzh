package com.itdy.hqsm.elasticsearch;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.index.query.QueryBuilders;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ProjectName: hqsm-parent
 * @Package: com.itdy.hqsm.elasticsearch
 * @ClassName: VehicleElasticsearchController
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/11/21 18:11
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
@RestController
@RequestMapping(value = "/con")
public class VehicleElasticsearchController {

    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private VehicleTemplateService vehicleTemplateService;


    /**
     *
     */
    @RequestMapping(value = "/c")
    @ResponseBody
    public String queryGeos(){
        double lat = 39.929986;
        double lon = 116.395645;
        List<VehicleEntity> list = vehicleTemplateService.queryForList(lat, lon);
        System.out.println("--------->"+list);
        return list.toString();
    }

    /**
     *
     */
    @RequestMapping(value = "/co")
    @ResponseBody
    public void bulkIndex(){
        List<VehicleEntity> list = new ArrayList<>();
        double lat = 39.929986;
        double lon = 116.395645;
        List<VddressPointEntity> VddressPointEntity = new ArrayList<>();
        for (int i = 1 ; i < 10; i++) {
            double max = 0.00001;
            double min = 0.000001;
            Random random = new Random();
            double s = random.nextDouble() % (max - min + 1) + max;
            DecimalFormat df = new DecimalFormat("######0.000000");
            // System.out.println(s);
            String lons = df.format(s + lon);
            String lats = df.format(s + lat);
            Double dlon = Double.valueOf(lons);
            Double dlat = Double.valueOf(lats);

            VddressPointEntity person = new VddressPointEntity();
            person.setId(Long.valueOf(i));
            person.setName("李四" + i);
            person.setType("类型" + i);
            person.setXjTime(new Date());
            if(i%2 == 0) {
                person.setRemark("实打实阿达胜多负少的我爱中国，我发给你同一人爱祖国"+i);
            }
            if(i%3 == 0) {
                person.setRemark("逼人太甚公司发发生的是你有后台我不爱美国，我尔特你腰疼爱祖国"+i);
            }else{
                person.setRemark("不然太鼓达人并发我不爱日本，我爱sdfsfwnt7呃呃特让他祖国"+i);
            }

            person.setAddress(new GeoPoint(dlat,dlon));
            VddressPointEntity.add(person);
        }
        for (int j = 0; j < 10; j++) {
            VehicleEntity VehicleEntity = new VehicleEntity();
            VehicleEntity.setId(Long.valueOf(j));
            VehicleEntity.setCarDriver("李四"+j);
            VehicleEntity.setCarName(j+".2米");
            VehicleEntity.setCarType(j+"");
            VehicleEntity.setPrice(j*1000);
            VehicleEntity.setStatus("1");
            VehicleEntity.setAddressPointDto(VddressPointEntity);
            list.add(VehicleEntity);
        }

        System.out.println("list:----------------"+list);
        vehicleTemplateService.bulkIndex(list);
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "/con")
    @ResponseBody
    public String queryDto(){
//		 Pageable pageable = new PageRequest(0, 5);
//		 NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder().withQuery(QueryBuilders.matchQuery("carDriver", "张三1"));
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder().withQuery(QueryBuilders.matchQuery("VddressPointEntity.name", "张三1")).withPageable(PageRequest.of(0,5));
        SearchQuery searchQuery = builder.build();

        //queryForList默认是分页，走的是queryForPage，默认10个
        List<VehicleEntity> VehicleEntity = elasticsearchTemplate.queryForList(searchQuery, VehicleEntity.class);
        System.err.println("findAll:----0--->"+VehicleEntity);
        return  VehicleEntity.toString();
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "/conn")
    @ResponseBody
    public String findByAddressPointDtoName(){
        String name = "张三1";
        List<VehicleEntity> list = vehicleService.findByAddressPointDtoName(name,PageRequest.of(0,5));
        System.err.println("findAll:--1--->"+list);
        return  list.toString();
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "/conc")
    @ResponseBody
    public String findByCarDriverAndPrice(){
        String carDriver = "张三1";
        int price = 1000;
//		Pageable pageable = new PageRequest(0, 5);
        List<VehicleEntity> list = vehicleService.findByCarDriverAndPrice(carDriver, price, PageRequest.of(0,5));
        System.err.println("findAll:--2---->"+list);
        return  list.toString();
    }

    /**
     * 查询
     * @return
     */
    @RequestMapping(value = "/cona")
    @ResponseBody
    public String findAll(){
        List<VehicleEntity> list = vehicleService.findAll();
        System.err.println("findAll-------->:"+list);
        return  list.toString();
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/cons")
    @ResponseBody
    public void del(){
        Long id = 18L;
        vehicleService.del(id);
    }

    /**
     * 添加数据
     * 创建Index
     */
    @RequestMapping(value = "/conIndex")
    @ResponseBody
    public void createIndex(){
        List<VehicleEntity> list = new ArrayList<>();
        double lat = 39.929986;
        double lon = 116.395645;
        List<VddressPointEntity> VddressPointEntity = new ArrayList<VddressPointEntity>();
        for (int i = 1 ; i < 30; i++) {
            double max = 0.00001;
            double min = 0.000001;
            Random random = new Random();
            double s = random.nextDouble() % (max - min + 1) + max;
            DecimalFormat df = new DecimalFormat("######0.000000");
            // System.out.println(s);
            String lons = df.format(s + lon);
            String lats = df.format(s + lat);
            Double dlon = Double.valueOf(lons);
            Double dlat = Double.valueOf(lats);

            VddressPointEntity person = new VddressPointEntity();
            person.setId(Long.valueOf(i));
            person.setName("张三" + i);
            person.setType("类型" + i);
            person.setXjTime(new Date());
            if(i%2 == 0) {
                person.setRemark("我爱阿二电厂事物地方中国，我爱祖国"+i);
            }
            if(i%3 == 0) {
                person.setRemark("我不存储爱美国，我股份个回复爱祖国"+i);
            }else{
                person.setRemark("我不怕怕爱日本，我爱豆腐干豆腐祖国"+i);
            }

            person.setAddress(new GeoPoint(dlat,dlon));
            VddressPointEntity.add(person);
        }
        for (int j = 30; j < 60; j++) {
            VehicleEntity VehicleEntity = new VehicleEntity();
            VehicleEntity.setId(Long.valueOf(j));
            VehicleEntity.setCarDriver("赵六"+j);
            VehicleEntity.setCarName(j+".2米");
            VehicleEntity.setCarType(j+"");
            VehicleEntity.setPrice(j*1000);
            VehicleEntity.setStatus("1");
            VehicleEntity.setAddressPointDto(VddressPointEntity);
            list.add(VehicleEntity);
        }
//    	vehicleService.set(VehicleEntity);
        System.out.println("list:----------------》"+list);
        //如果需要使用到geo必须用ElasticsearchTemplate的方式添加
        vehicleService.setAll(list);
    }


}
