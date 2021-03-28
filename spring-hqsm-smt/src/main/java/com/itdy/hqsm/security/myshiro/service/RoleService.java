package com.itdy.hqsm.security.myshiro.service;

import com.itdy.hqsm.security.myshiro.entity.Role;
import com.itdy.hqsm.security.myshiro.entity.UserRole;

import java.util.List;

/**
 * @ProjectName: hqsm-parent
 * @Package: com.itdy.hqsm.security.myshiro.service
 * @ClassName: RoleService
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/10/18 20:49
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public interface RoleService {


    //查找用户表
    public List<Role> findRoleList(Role role);

    //新增角色表
    public void add(Role role);

    //修改角色表
    public void update(Role role);

    //删除角色
    public void del(Role role);

    // 更新 用户-角色表
    public void update_user_Role(UserRole user_role);

    // 保存 用户-角色表
    public void save_user_Role(UserRole user_role) ;

    //删除用户-角色表
    public void del_user_Role(UserRole user_role);

}
