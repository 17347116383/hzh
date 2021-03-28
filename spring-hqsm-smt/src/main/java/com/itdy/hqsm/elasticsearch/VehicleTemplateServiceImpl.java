package com.itdy.hqsm.elasticsearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;



/**
 * @ProjectName: hqsm-parent
 * @Package: com.itdy.hqsm.elasticsearch
 * @ClassName: VehicleTemplateServiceImpl
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/11/21 18:04
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
@Service
public class VehicleTemplateServiceImpl implements VehicleTemplateService {


    @Autowired
    private  ElasticsearchTemplate elasticsearchTemplate;

    /**
     *  添加数据
     * @param VehicleEntity
     */
    public void bulkIndex(List<VehicleEntity> VehicleEntity) {
        int counter = 0;
        List<IndexQuery> queries = new ArrayList<>();
        //https://github.com/spring-projects/spring-data-elasticsearch/wiki/Geo-indexing-and-request
        //必须使用Mapping先去创建然后在添加数据，否则会出现geo类型错误
        elasticsearchTemplate.createIndex(VehicleEntity.class);
        elasticsearchTemplate.putMapping(VehicleEntity.class);
        for (VehicleEntity vehiclePointEsDto : VehicleEntity) {
            IndexQuery indexQuery = new IndexQuery();
            indexQuery.setId(vehiclePointEsDto.getId() + "");
            indexQuery.setObject(vehiclePointEsDto);
            //上面的那几步也可以使用IndexQueryBuilder来构建
            //IndexQuery index = new IndexQueryBuilder().withId(VehiclePointEsDto.getId() + "").withObject(VehicleEntity).build();
            queries.add(indexQuery);
            if (counter % 500 == 0) {
                elasticsearchTemplate.bulkIndex(queries);
                queries.clear();
                System.out.println("bulkIndex counter :-------- " + counter);
            }
            counter++;
        }
        if (queries.size() > 0) {
            elasticsearchTemplate.bulkIndex(queries);
            System.out.println("运行完成");
        }
        System.out.println("bulkIndex completed.-------");
    }

    /**
     *
     geo_distance: 查找距离某个中心点距离在一定范围内的位置
     geo_bounding_box: 查找某个长方形区域内的位置
     geo_distance_range: 查找距离某个中心的距离在min和max之间的位置
     geo_polygon: 查找位于多边形内的地点。
     sort可以用来排序
     */
    @Override
    public List<VehicleEntity> queryForList(double lat, double lon) {
        Long nowTime = System.currentTimeMillis();
        //查询某经纬度10000米范围内
        GeoDistanceQueryBuilder builder =
                QueryBuilders.geoDistanceQuery("addressPointEntity.address").point(lat, lon)
                .distance(10000, DistanceUnit.METERS);

        GeoDistanceSortBuilder sortBuilder = SortBuilders
//        		.geoDistanceSort("address")
                .geoDistanceSort("addressPointEntity.address",new GeoPoint(lat, lon))//高版本使用
                .point(lat, lon)
                .unit(DistanceUnit.METERS)
                .order(SortOrder.ASC);
        //分页查询50条
//		Pageable pageable = new PageRequest(0, 50);
        //查询经纬度10000米范围内，并根据坐标排序
//        NativeSearchQueryBuilder builder1 = new NativeSearchQueryBuilder().withFilter(builder).withPageable(pageable).withSort(sortBuilder);
        /**
         * 使用QueryBuilder
         * termQuery("key", obj) 完全匹配(不进行分词匹配)
         * termsQuery("key", obj1, obj2..)   一次匹配多个值
         * matchQuery("key", Obj) 单个匹配(分词匹配), field不支持通配符, 前缀具高级特性
         * multiMatchQuery("text", "field1", "field2"..);  匹配多个字段, field有通配符忒行
         * matchAllQuery();         匹配所有文件
         * matchPhraseQuery() 类似于数据库里的“%名字57%”这种
         */
        //查询经纬度10000米范围内，name字段的值为名字57，并根据坐标排序
//        QueryBuilder queryBuilder = QueryBuilders.termQuery("name", "名字57");
//        NativeSearchQueryBuilder builder1 = new NativeSearchQueryBuilder().withQuery(queryBuilder).withFilter(builder).withPageable(pageable).withSort(sortBuilder);
//        NativeSearchQueryBuilder builder1 = new NativeSearchQueryBuilder().withQuery(QueryBuilders.termQuery("name", "名字57")).withFilter(builder).withPageable(pageable).withSort(sortBuilder);

        /**
         * 组合查询
         * must(QueryBuilders) :   AND
         * mustNot(QueryBuilders): NOT
         * should:                  : OR
         */
        QueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("carDriver", "李四")).should(QueryBuilders.matchQuery("addressPointEntity.name", "李四"));
//        QueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("remark", "祖国"));
//        QueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("name", "名字57")).mustNot(QueryBuilders.matchQuery("id", "43")).should(QueryBuilders.matchQuery("type", "类型36"));
        NativeSearchQueryBuilder builder1 = new NativeSearchQueryBuilder().withQuery(queryBuilder).withFilter(builder).withPageable(PageRequest.of(0,50)).withSort(sortBuilder);
        SearchQuery searchQuery = builder1.build();

        //queryForList默认是分页，走的是queryForPage，默认10个
        List<VehicleEntity> VehiclePointEsDtoList = elasticsearchTemplate.queryForList(searchQuery, VehicleEntity.class);

        System.out.println("耗时：" + (System.currentTimeMillis() - nowTime));
        return VehiclePointEsDtoList;
    }

    /**
     *
     * @return
     */
    public List<VehicleEntity> queryDto() {
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder().withQuery(QueryBuilders.termQuery("carDriver", "张三1"));
        SearchQuery searchQuery = builder.build();

        //queryForList默认是分页，走的是queryForPage，默认10个
        List<VehicleEntity> VehiclePointEsDtoList = elasticsearchTemplate.queryForList(searchQuery, VehicleEntity.class);
        return VehiclePointEsDtoList;
    }

}
