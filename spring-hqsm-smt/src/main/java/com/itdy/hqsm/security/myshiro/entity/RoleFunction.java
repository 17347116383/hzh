package com.itdy.hqsm.security.myshiro.entity;

import com.itdy.hqsm.security.myshiro.utils.SysBaseEntity;

/**
 * @ProjectName: hqsm-parent
 * @Package: com.itdy.hqsm.security.myshiro.entity
 * @ClassName: RoleFunction
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/10/18 16:25
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class RoleFunction  extends SysBaseEntity {

    private  int roleId;   //角色ID
    private  int  functionId; //权限ID

    @Override
    public String toString() {
        return "RoleFunction{" +
                "roleId=" + roleId +
                ", functionId=" + functionId +
                '}';
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getFunctionId() {
        return functionId;
    }

    public void setFunctionId(int functionId) {
        this.functionId = functionId;
    }
}
