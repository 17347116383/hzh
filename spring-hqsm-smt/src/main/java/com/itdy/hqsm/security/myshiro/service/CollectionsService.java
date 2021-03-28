package com.itdy.hqsm.security.myshiro.service;


import com.itdy.hqsm.security.myshiro.entity.Collections;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ProjectName: hqsm-parent
 * @Package: com.itdy.hqsm.security.myshiro.service
 * @ClassName: DepartmentService
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/10/18 20:06
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public interface CollectionsService  {



    void save(Collections collections);


    List<Collections> findCollections(String uids);

    //查找详情
    Integer finds(@Param(value="url") String url, String uids);

    //删除
    Integer del(@Param(value="url") String url);
}
