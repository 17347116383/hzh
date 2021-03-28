package com.itdy.hqsm.security.myshiro.service.Impl;

import com.itdy.hqsm.security.myshiro.entity.PermissionRole;
import com.itdy.hqsm.security.myshiro.entity.Role;
import com.itdy.hqsm.security.myshiro.entity.UserRole;
import com.itdy.hqsm.security.myshiro.mapper.RoleMapper;
import com.itdy.hqsm.security.myshiro.service.PermissionService;
import com.itdy.hqsm.security.myshiro.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ProjectName: hqsm-parent
 * @Package: com.itdy.hqsm.security.myshiro.service.Impl
 * @ClassName: RoleServiceImpl
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/10/18 20:48
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class RoleServiceImpl implements RoleService{



    private Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);


    private RoleMapper roleMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissonService;

    @Autowired(required=true)
    @Qualifier("roleMapper")
    public void setRoleMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    //查找用户表
    public List<Role> findRoleList(Role role) {
        return roleMapper.findRoleList(role);
    }

    //新增角色表
    public void add(Role role) {
        roleMapper.add(role);
    }

    //修改角色表
    public void update(Role role) {

        roleMapper.update(role);
        this.savePer(role);
    }

    //删除角色
    public void del(Role role) {
        roleMapper.del(role);
        PermissionRole permission_role = new PermissionRole();
        this.delPer(permission_role, role);
    }

    // 更新 用户-角色表
    public void update_user_Role(UserRole user_role) {
        roleMapper.update_user_Role(user_role);
    }

    // 保存 用户-角色表
    public void save_user_Role(UserRole user_role) {
        roleMapper.save_user_Role(user_role);
    }

    //删除用户-角色表
    public void del_user_Role(UserRole user_role) {
        roleMapper.del_user_Role(user_role);
    }



 /////////////////////////////////////////////////////////////////////////////////////


    //保存角色
    public void save(Role role) {
        roleService.add(role);
        this.savePer(role);


    }

    //修改角色
 /*   public void update(Role role) {
        roleService.update(role);
        ((com.itdy.hqsm.security.myshiro.service.RoleServiceImpl) AopContext.currentProxy()).savePer(role);
    }
*/
    //删除角色
 /*   public void del(Role role) {
        roleService.del(role);
        PermissionRole permission_role = new PermissionRole();
        ((com.itdy.hqsm.security.myshiro.service.RoleServiceImpl) AopContext.currentProxy()).delPer(permission_role, role);

    }
*/

    //保存角色权限  先删除后保存
    public void savePer(Role role) {
        PermissionRole permission_role = new PermissionRole();
        this.delPer(permission_role, role);
        List<Long> permissiondList = role.getPermissiondList();
        for (Long pe : permissiondList) {
            permission_role.setSys_permission_id(Integer.parseInt(pe + ""));
            permissonService.save_permission_role(permission_role);
        }
    }

    //删除角色权限
    public void delPer(PermissionRole permission_role, Role role) {
        //先删除该角色的所有权限
        permission_role.setSys_role_id(Integer.parseInt(role.getRid()));
        permissonService.del_permission_role(permission_role);
    }

}
