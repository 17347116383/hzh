package com.itdy.hqsm.security.myshiro.mapper;


import com.itdy.hqsm.security.myshiro.entity.Role;
import com.itdy.hqsm.security.myshiro.entity.UserRole;
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
public interface RoleMapper   extends BaseMapper<Role>  {


    //查找角色表
    List<Role> findRoleList(Role role);
    //新增角色表
    void add(Role role);
    //修改角色表
    void update(Role role);
    //删除角色
    void del(Role role);
    // 更新 用户-角色表
    void update_user_Role(UserRole user_role);
    // 保存 用户-角色表
    void save_user_Role(UserRole user_role);
    //删除用户-角色表
    void del_user_Role(UserRole user_role);


}
