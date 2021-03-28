package com.itdy.hqsm.security.myshiro.entity;


import com.itdy.hqsm.security.myshiro.utils.SysBaseEntity;

/**
 * @ClassName:        用户与 角色
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2019/10/13
 * @Author Administrator
 *
 */
public class PermissionRole extends SysBaseEntity {

    private int id;
    private int sys_permission_id;//用户ID
    private int sys_role_id;//  角色ID



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSys_permission_id() {
        return sys_permission_id;
    }

    public void setSys_permission_id(int sys_permission_id) {
        this.sys_permission_id = sys_permission_id;
    }

    public int getSys_role_id() {
        return sys_role_id;
    }

    public void setSys_role_id(int sys_role_id) {
        this.sys_role_id = sys_role_id;
    }
}