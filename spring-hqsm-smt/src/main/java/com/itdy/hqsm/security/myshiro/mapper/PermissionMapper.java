package com.itdy.hqsm.security.myshiro.mapper;

import com.itdy.hqsm.security.myshiro.entity.Permission;
import com.itdy.hqsm.security.myshiro.entity.PermissionRole;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

/**
 * @ClassName:
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2019/10/13
 * @Author Administrator
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission>  {

    //查找角色表
    List<Permission> findPermissionList(Permission permission);
    //查询角色权限表
    List<PermissionRole> find_permission_roleList(PermissionRole permission_role);
    //新增权限
    void save_permission_role(PermissionRole permission_role);
    //删除权限
    void del_permission_role(PermissionRole permission_role);
    //新增菜单
    void add(Permission permission);
    //修改菜单
    void update(Permission permission);
    //查找菜单列表
    List<Permission> findDepartMentList(Permission permission);
    //删除菜单
    void del(Permission permission);
    //查询菜单信息
    List<Permission> selectMentList();
}
