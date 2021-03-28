package com.itdy.hqsm.security.myshiro.service;

import com.itdy.hqsm.security.myshiro.entity.Department;
import com.itdy.hqsm.security.myshiro.entity.UserDepartment;
import com.itdy.hqsm.security.myshiro.mapper.DepartmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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
public interface DepartmentService {

    //查找部门列表
    public List<Department> findDepartMentList(Department department) ;


    //删除部门
    public void del(Department department);


    //修改部门
    public void update(Department department);


    //新增部门
    public void add(Department department) ;


    // 更新 用户-部门表
    public void update_user_department(UserDepartment user_department) ;


    // 保存 用户-部门表
    public void save_user_department(UserDepartment user_department) ;


    //删除用户-部门表
    public void del_user_departmentt(UserDepartment user_department) ;


}
