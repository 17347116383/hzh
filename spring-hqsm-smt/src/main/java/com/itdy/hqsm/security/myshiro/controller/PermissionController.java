package com.itdy.hqsm.security.myshiro.controller;

import com.itdy.hqsm.security.myshiro.entity.Permission;
import com.itdy.hqsm.security.myshiro.entity.PermissionRole;
import com.itdy.hqsm.security.myshiro.entity.User;
import com.itdy.hqsm.security.myshiro.service.PermissionService;
import com.itdy.hqsm.security.myshiro.service.UserService;
import com.itdy.hqsm.security.myshiro.utils.BaseResult;

import com.itdy.hqsm.security.myshiro.utils.ShiroUserUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2019/10/13
 * @Author Administrator
 */
@Controller
@RequestMapping(value = "/permission")
public class PermissionController {

    @Resource
    private ThymeleafViewResolver thymeleafViewResolver;
    @Autowired
    private PermissionService permissonService;
    @Autowired
    private UserService userService;


    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/index")
    public String index() {
        System.out.println("123");
        return "sys/menu/permission";
    }



    /**
     * @param permission 获取权限列表
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public List<Permission> list(Permission permission) {
        List<Permission> permissionsList = permissonService.findPermissionList(permission);
        return permissionsList;
    }

    /**
     * @param permission_role 获取角色权限
     * @return
     */
    @RequestMapping("/permission_Rolelist")
    @ResponseBody
    public List<Long> permission_Rolelist(PermissionRole permission_role) {
        List<PermissionRole> permission_roleList = permissonService.find_permission_roleList(permission_role);
        List<Long> list = new ArrayList<Long>();
        for (PermissionRole plist : permission_roleList) {
            System.out.println("--获取角色权限--"+plist.getSys_permission_id());
            list.add(Long.parseLong(plist.getSys_permission_id() + ""));
        }
        return list;
    }

    /**
     * 上级部门Id(管理员则为0)
     */
    @RequestMapping("/info")
    @ResponseBody
    //@RequiresPermissions("sys:dept:list")
    public Map<String, Long> info() {
        Map<String, Long> map = new HashMap<String, Long>();
        long pId = 0;
        User shiroUser = ShiroUserUtils.getShiroUser();
        if (!shiroUser.getUserAccount().equals("admin")) {
            User user = new User();
            user.setUserAccount(shiroUser.getUserAccount());
            if (!CollectionUtils.isEmpty(userService.findOnlyUserList(user))) {
                user = userService.findOnlyUserList(user).get(0);
                pId = Long.parseLong(user.getDepartmentId());
            }
        }
        map.put("pId", pId);
        return map;
    }



    /**
     * 选择部门(添加、修改菜单)
     */
    @RequestMapping("/select")
    @ResponseBody
    public Map<String, List<Permission>> select(Permission permission) {
        Map<String, List<Permission>> map = new HashMap<String, List<Permission>>();
        List<Permission> permissionList = permissonService.findPermissionList(permission);
        User shiroUser = ShiroUserUtils.getShiroUser();
        //添加一级部门
        if (shiroUser.getUserAccount().equals("admin")) {
            Permission root = new Permission();
            root.setPid(0L);
            root.setPname("一级菜单");
            root.setParentid(-1L);
            root.setOpen(true);
            permissionList.add(root);
        }
        map.put("permissionList", permissionList);
        return map;
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @ResponseBody
    public BaseResult save(@RequestBody Permission dept) {
        permissonService.add(dept);
        return BaseResult.ok();
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public BaseResult update(@RequestBody Permission dept) {
        permissonService.update(dept);
        Map<String, Object> vars = new HashMap<>();
        List<Permission> mentList = permissonService.selectMentLists();
        vars.put("mentlist",mentList);
        thymeleafViewResolver.setStaticVariables(vars);
        return BaseResult.ok();
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{pid}")
    @ResponseBody
    public BaseResult info(@PathVariable("pid") Long pid) {
        Permission permission = new Permission();
        permission.setPid(pid);
        try {
            permission = permissonService.findDepartMentList(permission).get(0);
        } catch (Exception e) {
            log.info("/info/{deptId}: 查询为空");
        }
        return BaseResult.ok().put("dept", permission);
    }

    /**
     * 删除
     */
    @RequestMapping("/del")
    @ResponseBody
    public BaseResult del(Permission dept) {
        permissonService.del(dept);
        Map<String, Object> vars = new HashMap<>();
        List<Permission> mentList = permissonService.selectMentLists();
        vars.put("mentlist",mentList);
        thymeleafViewResolver.setStaticVariables(vars);
        return BaseResult.ok();
    }

}
