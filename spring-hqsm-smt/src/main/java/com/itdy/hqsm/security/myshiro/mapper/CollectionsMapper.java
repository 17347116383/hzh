package com.itdy.hqsm.security.myshiro.mapper;



import com.itdy.hqsm.security.myshiro.entity.Collections;
import com.itdy.hqsm.security.myshiro.entity.Department;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Sara
 * @since 2019-05-29
 */
@Mapper
@CacheNamespace
public interface CollectionsMapper  extends BaseMapper<Collections> {
    //查找详情
//    Collections saveCollection(@Param(value ="url") String url);

    //@Select("SELECT * from sys_collection where user_id=#{uids}")
    List<Collections> findCollections(String uids);

    //查找详情
    int  finds(@Param(value = "url") String url, @Param(value = "uids")String uids);

    //删除
    Integer del(@Param(value = "url") String url);

    void  insertCollections(Collections collections);
}
