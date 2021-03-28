package com.itdy.hqsm.security.myshiro.service;

import com.itdy.hqsm.security.myshiro.entity.Permission;
import com.itdy.hqsm.security.myshiro.entity.PermissionRole;

import java.util.List;

/**
 * @ProjectName: hqsm-parent
 * @Package: com.itdy.hqsm.security.myshiro.service
 * @ClassName: PermissonService
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/10/18 20:45
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public interface PermissionService {




    //查找部门列表
    public List<Permission> findPermissionList(Permission permission) ;
    //查询角色权限表
    public List<PermissionRole> find_permission_roleList(PermissionRole permission_role) ;

    //新增权限
    public void save_permission_role(PermissionRole permission_role);

    //删除权限
    public void del_permission_role(PermissionRole permission_role);


    //新增菜单
    public void add(Permission permission);

    //修改部门
    public void update(Permission permission) ;

    //查找菜单列表
    public List<Permission> findDepartMentList(Permission permission) ;

    //删除部门
    public void del(Permission permission);

    public List<Permission> selectMentLists();

}
