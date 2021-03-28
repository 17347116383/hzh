package com.itdy.hqsm.security.myshiro.entity;

import com.itdy.hqsm.security.myshiro.utils.SysBaseEntity;


import java.util.List;

/**
 * @ClassName:       部门
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2019/10/13
 * @Author Administrator
 */
public class Department extends SysBaseEntity {


    private Long deptId;         //部门ID
    private Long parentId;       //上级部门ID，一级部门为0
    private String name;         //部门名称
    private String parentName;   //上级部门名称
    private Integer orderNum;    //排序
    private Integer status;
    private String uid;
    private Boolean open;             //tree属性
    private List<Department>  dept;   //二级部门
    private List<User>  user;         // //用户
    private String userDepartmentName;//用户和部门公用字段






    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    public List<Department> getDept() {
        return dept;
    }

    public void setDept(List<Department> dept) {
        this.dept = dept;
    }

    public String getUserDepartmentName() {
        return userDepartmentName;
    }

    public void setUserDepartmentName(String userDepartmentName) {
        this.userDepartmentName = userDepartmentName;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getDeptId() {
        return deptId;
    }

    /**
     * 设置：上级部门ID，一级部门为0
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取：上级部门ID，一级部门为0
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 设置：部门名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取：部门名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置：排序
     */
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * 获取：排序
     */
    public Integer getOrderNum() {
        return orderNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }


}
