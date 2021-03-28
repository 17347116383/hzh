package com.itdy.hqsm.security.myshiro.controller;




import com.itdy.hqsm.security.myshiro.entity.Collections;
import com.itdy.hqsm.security.myshiro.entity.User;
import com.itdy.hqsm.security.myshiro.service.Impl.CollectionsServiceImpl;
import com.itdy.hqsm.security.myshiro.utils.BaseResult;
import com.itdy.hqsm.security.myshiro.utils.ShiroUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Sara
 * @since 2019-05-29
 */
@RestController
@RequestMapping("/collections")
public class CollectionsController {

   /* @Autowired
    private CollectionsMapper collectionsMapper;*/
    @Autowired
    private CollectionsServiceImpl collectionsServiceImpl;

//    @RequiresPermissions(value = "sys:collections:edit")
    @RequestMapping(value = "/saveCollection")
    @ResponseBody
    public BaseResult saveCollection(String url) {
       User shiroUser = ShiroUserUtils.getShiroUser();
        String userAccount=shiroUser.getUserAccount();
        Long uid=shiroUser.getUid();
        String uids=String.valueOf(uid);

        Collections collections=new Collections();
        Integer count=collectionsServiceImpl.finds(url,uids);
        collections.setUrl(url);
        collections.setUserId(uids);
        if (count>0){
            collectionsServiceImpl.del(url);
            return BaseResult.error("取消成功");
        }else {
            collectionsServiceImpl.save(collections);
            return BaseResult.ok();
        }

    }

    //查找地址
    @RequestMapping(value = "/finds")
    public  BaseResult finds(String url) {
        User shiroUser = ShiroUserUtils.getShiroUser();
        String userAccount=shiroUser.getUserAccount();
        Long uid=shiroUser.getUid();
        String uids=String.valueOf(uid);
//        Map<String, Object> vars = new HashMap<>();
        Integer count=collectionsServiceImpl.finds(url,uids);
//        vars.put("user",userNmae);
        if (count>0){
            return BaseResult.ok();
        }
//        thymeleafViewResolver.setStaticVariables(vars);
       return BaseResult.error();
    }





}

