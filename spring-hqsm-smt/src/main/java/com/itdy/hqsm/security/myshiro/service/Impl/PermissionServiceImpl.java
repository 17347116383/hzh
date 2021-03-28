package com.itdy.hqsm.security.myshiro.service.Impl;

import com.itdy.hqsm.security.myshiro.entity.Permission;
import com.itdy.hqsm.security.myshiro.entity.PermissionRole;
import com.itdy.hqsm.security.myshiro.mapper.PermissionMapper;
import com.itdy.hqsm.security.myshiro.service.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ProjectName: hqsm-parent
 * @Package: com.itdy.hqsm.security.myshiro.service.Impl
 * @ClassName: PermissonServiceImpl
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/10/18 20:45
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class PermissionServiceImpl implements PermissionService {


    private Logger log = LoggerFactory.getLogger(PermissionServiceImpl.class);

    @Autowired(required=true)
    private PermissionMapper permissonMapper;


   /* @Autowired(required=true)
    @Qualifier("permissonMapper")
    public void setPermissonMapper(PermissonMapper permissonMapper) {
        this.permissonMapper = permissonMapper;
    }*/

    //查找部门列表
    public List<Permission> findPermissionList(Permission permission) {
        log.debug("findPermissionList:");
        return permissonMapper.findPermissionList(permission);
    }

    //查询角色权限表
    public List<PermissionRole> find_permission_roleList(PermissionRole permission_role) {
        return permissonMapper.find_permission_roleList(permission_role);
    }

    //新增权限
    public void save_permission_role(PermissionRole permission_role) {
        permissonMapper.save_permission_role(permission_role);
    }

    //删除权限
    public void del_permission_role(PermissionRole permission_role) {
        permissonMapper.del_permission_role(permission_role);
    }


    //新增菜单
    public void add(Permission permission) {
        log.debug("add:");
        permissonMapper.add(permission);
    }

    //修改部门
    public void update(Permission permission) {
        log.debug("update:");
        permissonMapper.update(permission);
    }

    //查找菜单列表
    public List<Permission> findDepartMentList(Permission permission) {
        log.debug("findDepartMentList:");
        return permissonMapper.findDepartMentList(permission);
    }

    //删除部门
    public void del(Permission permission) {
        log.debug("del:");
        permissonMapper.del(permission);
    }

    public List<Permission> selectMentLists(){
        return permissonMapper.selectMentList();
    }

}
