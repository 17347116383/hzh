package com.itdy.hqsm.security.myshiro.service.Impl;

import com.itdy.hqsm.security.myshiro.entity.Department;
import com.itdy.hqsm.security.myshiro.entity.UserDepartment;
import com.itdy.hqsm.security.myshiro.mapper.DepartmentMapper;
import com.itdy.hqsm.security.myshiro.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ProjectName: hqsm-parent
 * @Package: com.itdy.hqsm.security.myshiro.service.Impl
 * @ClassName: DepartmentServiceImpl
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/10/18 20:05
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class DepartmentServiceImpl implements DepartmentService {

    private Logger log = LoggerFactory.getLogger(DepartmentService.class);

    @Autowired(required = true)
    private DepartmentMapper departmentMapper;





    //查找部门列表
    public List<Department> findDepartMentList(Department department) {
        log.debug("findDepartMentList:");
        return departmentMapper.findDepartMentList(department);
    }


    //删除部门
    public void del(Department department) {
        log.debug("del:");
        departmentMapper.del(department);
    }

    //修改部门
    public void update(Department department) {
        log.debug("update:");
        departmentMapper.update(department);
    }

    //新增部门
    public void add(Department department) {
        log.debug("add:");
        departmentMapper.add(department);
    }

    // 更新 用户-部门表
    public void update_user_department(UserDepartment user_department) {
        departmentMapper.update_user_department(user_department);
    }

    // 保存 用户-部门表
    public void save_user_department(UserDepartment user_department) {
        departmentMapper.save_user_department(user_department);
    }

    //删除用户-部门表
    public void del_user_departmentt(UserDepartment user_department) {
        departmentMapper.del_user_departmentt(user_department);
    }


}
