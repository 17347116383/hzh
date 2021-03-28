package com.itdy.hqsm.security.myshiro.controller;


import com.itdy.hqsm.security.myshiro.entity.DesktopSet;
import com.itdy.hqsm.security.myshiro.entity.User;
import com.itdy.hqsm.security.myshiro.service.Impl.DesktopSetServiceImpl;
import com.itdy.hqsm.security.myshiro.utils.BaseResult;
import com.itdy.hqsm.security.myshiro.utils.ShiroUserUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Sara
 * @since 2019-06-03
 */
@RestController
@RequestMapping("/desktop")
public class DesktopController {



    @Autowired(required=true)
    private DesktopSetServiceImpl desktopServiceImpl;


    /**
     * @param desktop 获取div
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public List<Desktop> list() {
        /*User shiroUser = ShiroUserUtils.getShiroUser();
        String userAccount=shiroUser.getUserAccount();
        Long uid=shiroUser.getUid();
        desktop.setUid(uid);
            List<Desktop> desktopList = desktopServiceImpl.findDesktopList(desktop);*/
            return null;

    }





    /**
     * @param desktopSet 获取角色权限
     * @return
     */
    @RequestMapping("/desktop_list")
    //@ResponseBody
    public List<Long> desktop_list(DesktopSet desktopSet) {
        User shiroUser = ShiroUserUtils.getShiroUser();
        String userAccount=shiroUser.getUserAccount();
        Long uid=shiroUser.getUid();
        desktopSet.setUserId(uid.intValue());

        List<DesktopSet> desktopSet_ist = desktopServiceImpl.find_desktop_list(desktopSet);
        List<Long> list = new ArrayList<Long>();
        for (DesktopSet plist : desktopSet_ist) {
            list.add(Long.parseLong(plist.getSysDivId()+ ""));
        }
        return list;
    }


    //------------------------------------------------------------------



    /**
     * @param
     * @return  desktop/list
     */
  /*  @RequestMapping("/list")
    @ResponseBody
    public List<Long> list(DesktopSet desktopSet) {
        User shiroUser = ShiroUserUtils.getShiroUser();
        String userAccount=shiroUser.getUserAccount();
        Long uid=shiroUser.getUid();
        desktopSet.setUserId(uid.intValue());
        List<DesktopSet> desktopList = desktopServiceImpl.findDesktop(desktopSet);
        List<Long> lists = new ArrayList<Long>();

        for (DesktopSet  de: desktopList) {
//            System.out.println(de.getSys_permission_id());
            lists.add(Long.parseLong(de.getSysDivId() + ""));
        }
        return lists;
    }*/


    /**
     * 修改
     */
    @RequestMapping(value = "/update")
    public BaseResult update(@RequestBody DesktopSet dept) {
        User shiroUser = ShiroUserUtils.getShiroUser();
        String userAccount=shiroUser.getUserAccount();
        Long uid=shiroUser.getUid();
        dept.setUserId(uid.intValue());
        desktopServiceImpl.update(dept);
        return BaseResult.ok();

    }
}

