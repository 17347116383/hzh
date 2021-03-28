package com.itdy.hqsm.security.myshiro.controller;


import com.itdy.hqsm.security.myshiro.entity.Department;
import com.itdy.hqsm.security.myshiro.entity.User;
import com.itdy.hqsm.security.myshiro.service.DepartmentService;
import com.itdy.hqsm.security.myshiro.service.Impl.DepartmentServiceImpl;
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
@RequestMapping(value = "/department")
public class DepartmentController {

    @Autowired
    private DepartmentServiceImpl departmentService;

    @Autowired
    private UserService userService;


    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/index")
    public String index() {

        System.out.println("123");
        return "sys/dept/department";
    }


    /**
     * 上级部门Id(管理员则为0)
     */
    @RequestMapping("/info")
    @ResponseBody
    //@RequiresPermissions("sys:dept:list")
    public Map<String, Long> info() {
        Map<String, Long> map = new HashMap<String, Long>();
        long deptId = 0;
        User shiroUser = ShiroUserUtils.getShiroUser();
        if (!shiroUser.getUserAccount().equals("admin")) {
            User user = new User();
            user.setUserAccount(shiroUser.getUserAccount());
            if (!CollectionUtils.isEmpty(userService.findOnlyUserList(user))) {
                user = userService.findOnlyUserList(user).get(0);
                deptId = Long.parseLong(user.getDepartmentId());
            }
        }
        map.put("deptId", deptId);
        return map;
    }


    @RequestMapping("/list")
    @ResponseBody
    public List<Department> list(Department department) {
        List<Department> deptList = departmentService.findDepartMentList(department);

        return deptList;
    }


    /**
     * 选择部门(添加、修改菜单)
     */
    @RequestMapping("/select")
    @ResponseBody
    public Map<String, List<Department>> select(Department department) {
        Map<String, List<Department>> map = new HashMap<String, List<Department>>();
        List<Department> deptList = departmentService.findDepartMentList(department);

        User shiroUser = ShiroUserUtils.getShiroUser();
        //添加一级部门
        if (shiroUser.getUserAccount().equals("admin")) {
            Department root = new Department();
            root.setDeptId(0L);
            root.setName("一级部门");
            root.setParentId(-1L);
            root.setOpen(true);
            deptList.add(root);
        }
        map.put("deptList", deptList);
        return map;
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{deptId}")
    @ResponseBody
    public BaseResult info(@PathVariable("deptId") Long deptId) {
        Department department = new Department();
        department.setDeptId(deptId);
        try {
            department = departmentService.findDepartMentList(department).get(0);
        } catch (Exception e) {
            log.info("/info/{deptId}: 查询为空");
        }
        return BaseResult.ok().put("dept", department);
    }


    /**
     * 保存
     */
    @RequestMapping("/save")
    @ResponseBody
    public BaseResult save(@RequestBody Department dept) {
        departmentService.add(dept);
        return BaseResult.ok();
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public BaseResult update(@RequestBody Department dept) {
        departmentService.update(dept);
        return BaseResult.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/del")
    @ResponseBody
    public BaseResult del(Department dept) {
        departmentService.del(dept);
        return BaseResult.ok();
    }

}
