package com.itdy.hqsm.security.myshiro.entity;



import com.itdy.hqsm.security.myshiro.utils.SysBaseEntity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * @ClassName:        角色
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2019/10/13
 * @Author Administrator
 */
public class Role extends SysBaseEntity {

    private String rid;
    private String rname;     //角色名
    private int available;    //是否可用
    private Long parentId;    //上级角色ID，一级部门为0
    private Integer orderNum; //排序
    private Boolean open;     //tree属性
    private String parentName;//上级角色名称
    private List<Long> permissiondList;// 角色对应的权限列表
    private Set<User> users = new HashSet<>();
    private Set<Permission> permissions = new HashSet<Permission>();





    public List<Long> getPermissiondList() {
        return permissiondList;
    }

    public void setPermissiondList(List<Long> permissiondList) {
        this.permissiondList = permissiondList;
    }

    ;


    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }
}
