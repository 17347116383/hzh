package com.itdy.hqsm.security.myshiro.entity;

import com.itdy.hqsm.security.myshiro.utils.SysBaseEntity;

import java.util.List;

/**
 * @ClassName:        用户与部门
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2019/10/13
 * @Author Administrator
 * //部门-用户ID
 */
public class UserDepartment extends SysBaseEntity {


    private Long id;
    private String sys_user_id;    //用户id
    private String sys_department_id;   //部门





    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSys_user_id() {
        return sys_user_id;
    }

    public void setSys_user_id(String sys_user_id) {
        this.sys_user_id = sys_user_id;
    }

    public String getSys_department_id() {
        return sys_department_id;
    }

    public void setSys_department_id(String sys_department_id) {
        this.sys_department_id = sys_department_id;
    }
}
