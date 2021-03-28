package com.itdy.hqsm.security.myshiro.controller;

import com.itdy.hqsm.security.myshiro.entity.Role;
import com.itdy.hqsm.security.myshiro.entity.User;
import com.itdy.hqsm.security.myshiro.service.Impl.RoleServiceImpl;
import com.itdy.hqsm.security.myshiro.service.RoleService;

import com.itdy.hqsm.security.myshiro.service.UserService;
import com.itdy.hqsm.security.myshiro.utils.BaseResult;

import com.itdy.hqsm.security.myshiro.utils.ShiroUserUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
@RequestMapping(value = "/role")
public class RoleController {


    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleServiceImpl roleServiceImpl;


    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/index")
    public String index() {

        return "sys/role/roleManage";
    }


    /**
     * 上级角色Id(管理员则为0)
     */
    @RequestMapping("/info")
    @ResponseBody
    //@RequiresPermissions("sys:dept:list")
    public Map<String, Long> info() {

        DepartmentController departmentController = new DepartmentController();

        return departmentController.info();
    }

    /**
     * @param role 获取角色
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public List<Role> list(Role role) {
        List<Role> roleList = roleService.findRoleList(role);

        return roleList;
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{deptId}")
    @ResponseBody
    public BaseResult info(@PathVariable("deptId") Long deptId) {
        Role role = new Role();
        //String deptid=String.valueOf(deptId);
        role.setRid(String.valueOf(deptId));
        try {
            role = roleService.findRoleList(role).get(0);
        } catch (Exception e) {
            log.info("/info/{deptId}: 查询为空");
        }
        return BaseResult.ok().put("dept", role);
    }


    /**
     * 选择部门(添加、修改菜单)
     */
    @RequestMapping("/select")
    @ResponseBody
    public Map<String, List<Role>> select(Role role) {
        Map<String, List<Role>> map = new HashMap<String, List<Role>>();
        List<Role> deptList = roleService.findRoleList(role);
        User shiroUser = ShiroUserUtils.getShiroUser();
        /*
        //添加一级部门
        if (shiroUser.getUserAccount().equals("admin")) {
            Role root = new Role();
            root.setRid("0");
            root.setParentName("暂无上级部门");
            root.setParentId(0L);
            root.setOpen(true);
            deptList.add(root);
        }*/
        map.put("deptList", deptList);
        return map;
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @ResponseBody
    public BaseResult save(@RequestBody Role role) {
        try {
            roleServiceImpl.save(role);
            return BaseResult.ok();
        }catch (Exception e){
            log.info("/role/update: 角色保存失败:" + e.toString());
            return BaseResult.error("保存失败！");
        }
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public BaseResult update(@RequestBody Role role) {

        try {
            roleServiceImpl.update(role);
            return BaseResult.ok();
        }catch (Exception e){

            log.info("/role/update: 角色更新失败:" + e.toString());
            return BaseResult.error("更新失败！");
        }
    }



    /**
     * 删除
     */
    @RequestMapping("/del")
    @ResponseBody
    public BaseResult del(Role role) {

        try {
            roleServiceImpl.del(role);

        } catch (Exception e) {

            log.info("/user/del: 用户删除失败:" + e.toString());
            return BaseResult.error("删除用户失败");
        }

        return BaseResult.ok();
    }


}
