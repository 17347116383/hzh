package com.itdy.hqsm.modes.controller;

import com.alibaba.fastjson.JSONObject;
import com.itdy.hqsm.elasticsearch.ElasticsearchUtil;
import com.itdy.hqsm.elasticsearch.EsModel;
import com.itdy.hqsm.elasticsearch.EsPage;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 
 * @ClassName:  EsController   
 * @Description:TODO 
 * @author: 
 * @date:   2019年5月12日 下午6:22:39   
 * @version V1.0   
 * @Copyright: 2019 
 *
 */
//@CrossOrigin(origins = "http://domain2.com", maxAge = 3600)
//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/es")
public class ElasticSearchController {
	
	 private static final Logger LOGGER = LoggerFactory.getLogger(ElasticSearchController.class);

    /**
     * 测试索引
     */
    private String indexName = "test_index";
    /**
     * 类型
     */
    private String esType = "external";

    /**
     *
     * 创建索引
     *      http://127.0.0.1:8081/es/createIndex
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/createIndex")
    public String createIndex(HttpServletRequest request, HttpServletResponse response) {
        if (!ElasticsearchUtil.isIndexExist(indexName)) {
            ElasticsearchUtil.createIndex(indexName);
        } else {
            return "索引已经存在";
        }
        return "索引创建成功";
    }

    /**
     * 插入记录
     *     http://127.0.0.1:8081/es/insertJson
     * @return
     */
   @RequestMapping("/insertJson")
    public String insertJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", UUID.randomUUID().toString().replaceAll("-", ""));
        jsonObject.put("age", 25);
        jsonObject.put("name", "j-" + new Random(100).nextInt());
        jsonObject.put("date", new Date());
        String id = ElasticsearchUtil.addData(jsonObject, indexName, esType, jsonObject.getString("id"));
        return id;
    }

    /**
     * 插入记录
     *      http://127.0.0.1:8081/es/insertModel
     * @return
     */
    @RequestMapping("/insertModel")
    public String insertModel() {
    	String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        EsModel esModel = new EsModel();
        esModel.setId(uuid);
        LOGGER.info("------id---->>>>>"+esModel.getId());
        esModel.setName("m-" + new Random(100).nextInt());
        esModel.setAge(30);
        esModel.setDate(new Date());
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(esModel);
        String id = ElasticsearchUtil.addData(jsonObject, indexName, esType, jsonObject.getString("id"));
        return id;
    }

    /**
     * 删除记录
     *        http://127.0.0.1:8081/es/delete
     * @return
     */
    @RequestMapping("/delete")
    public String delete(String id) {
        if (StringUtils.isNotBlank(id)) {
            ElasticsearchUtil.deleteDataById(indexName, esType, id);
            return "删除id=" + id;
        } else {
            return "id为空";
        }
    }

    /**
     * 更新数据
     *    http://127.0.0.1:8081/es/update
     * @return
     */
    @RequestMapping("/update")
    public String update(String id) {
        if (StringUtils.isNotBlank(id)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", id);
            jsonObject.put("age", 31);
            jsonObject.put("name", "修改");
            jsonObject.put("date", new Date());
            ElasticsearchUtil.updateDataById(jsonObject, indexName, esType, id);
            return "id=" + id;
        } else {
            return "id为空";
        }
    }

    /**
     * 获取数据
     * http://127.0.0.1:8081/es/getData?id=2018-04-25%2016:33:44
     *
     * @param id
     * @return
     */
    @RequestMapping("/getData")
    public String getData(String id) {
        if (StringUtils.isNotBlank(id)) {
            Map<String, Object> map = ElasticsearchUtil.searchDataById(indexName, esType, id, null);
            return JSONObject.toJSONString(map);
        } else {
            return "id为空";
        }
    }

    /**
     * 查询数据
     * 模糊查询
     *    http://127.0.0.1:8081/es/queryMatchData
     * @return
     */
    @RequestMapping("/queryMatchData")
    public String queryMatchData() {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolean matchPhrase = false;
        if (matchPhrase == Boolean.TRUE) {
            //不进行分词搜索
            boolQuery.must(QueryBuilders.matchPhraseQuery("name", "m"));
        } else {
            boolQuery.must(QueryBuilders.matchQuery("name", "m-m"));
        }
        List<Map<String, Object>> list = ElasticsearchUtil.
                searchListData(indexName, esType, boolQuery, 10, "name", null, "name");
        return JSONObject.toJSONString(list);
    }

    /**
     * 通配符查询数据
     * 通配符查询 ?用来匹配1个任意字符，*用来匹配零个或者多个字符
     *   http://127.0.0.1:8081/es/queryWildcardData
     * @return
     */
    @RequestMapping("/queryWildcardData")
    public String queryWildcardData() {
        QueryBuilder queryBuilder = QueryBuilders.wildcardQuery("name.keyword", "j-?466");
        List<Map<String, Object>> list = ElasticsearchUtil.searchListData(indexName, esType, queryBuilder, 10, null, null, null);
        return JSONObject.toJSONString(list);
    }

    /**
     * 正则查询
     * http://127.0.0.1:8088/es/queryRegexpData
     * @return
     */
    @RequestMapping("/queryRegexpData")
    public String queryRegexpData() {
        QueryBuilder queryBuilder = QueryBuilders.regexpQuery("name.keyword", "m--[0-9]{1,11}");
        List<Map<String, Object>> list = ElasticsearchUtil.searchListData(indexName, esType, queryBuilder, 10, null, null, null);
        return JSONObject.toJSONString(list);
    }

    /**
     * 查询数字范围数据
     *   http://127.0.0.1:8088/es/queryIntRangeData
     * @return
     */
    @RequestMapping("/queryIntRangeData")
    public String queryIntRangeData() {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.must(QueryBuilders.rangeQuery("age").from(21)
                .to(25));
        List<Map<String, Object>> list = ElasticsearchUtil.searchListData(indexName, esType, boolQuery, 10, null, null, null);
        return JSONObject.toJSONString(list);
    }

    /**
     * 查询日期范围数据
     *   http://127.0.0.1:8088/es/queryDateRangeData
     * @return
     */
    @RequestMapping("/queryDateRangeData")
    public String queryDateRangeData() {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.must(QueryBuilders.rangeQuery("date").from("2019-04-25T08:33:44.840Z")
                .to("2019-05-25T10:03:08.081Z"));
        List<Map<String, Object>> list = ElasticsearchUtil.searchListData(indexName, esType, boolQuery, 10, null, null, null);
        return JSONObject.toJSONString(list);
    }

    /**
     * 查询分页
     *
     * @param startPage 第几条记录开始
     *                  从0开始
     *                  第1页 ：http://127.0.0.1:8088/es/queryPage?startPage=0&pageSize=2
     *                  第2页 ：http://127.0.0.1:8088/es/queryPage?startPage=2&pageSize=2
     * @param pageSize  每页大小
     * @return
     */
    @RequestMapping("/queryPage")
    public String queryPage(String startPage, String pageSize) {
        if (StringUtils.isNotBlank(startPage) && StringUtils.isNotBlank(pageSize)) {
            BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
            boolQuery.must(QueryBuilders.rangeQuery("date").from("2018-04-25T08:33:44.840Z")
                    .to("2019-05-25T10:03:08.081Z"));
            EsPage list = ElasticsearchUtil.searchDataPage(indexName, esType, Integer.parseInt(startPage), Integer.parseInt(pageSize), boolQuery, null, null, null);
            return JSONObject.toJSONString(list);
        } else {
            return "startPage或者pageSize缺失";
        }
    }
}

