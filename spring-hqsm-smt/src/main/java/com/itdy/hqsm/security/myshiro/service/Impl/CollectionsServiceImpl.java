package com.itdy.hqsm.security.myshiro.service.Impl;

import com.itdy.hqsm.security.myshiro.entity.Collections;
import com.itdy.hqsm.security.myshiro.mapper.CollectionsMapper;
import com.itdy.hqsm.security.myshiro.service.CollectionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ProjectName: hqsm-parent
 * @Package: com.itdy.hqsm.security.myshiro.service.Impl
 * @ClassName: CollectionsServiceImpl
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/10/21 23:00
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CollectionsServiceImpl implements CollectionsService {

    @Autowired(required = true) //表示注入的时候，该bean必须存在，否则就会注入失败
    private CollectionsMapper collectionsMapper;

    /**
     * @param collections
     */
    public void save(Collections collections) {
        collectionsMapper.insertCollections(collections);
    }

    /**
     *
     * @param uids
     * @return
     */
    public List<Collections> findCollections(String uids) {
        return collectionsMapper.findCollections(uids);
    }

    /**
     *
     * @param url
     * @param uids
     * @return
     */
    public Integer finds(String url, String uids) {
        return collectionsMapper.finds(url,uids);
    }

    /**
     *
     * @param url
     * @return
     */
    public Integer del(String url) {
        return collectionsMapper.del(url);
    }



}
