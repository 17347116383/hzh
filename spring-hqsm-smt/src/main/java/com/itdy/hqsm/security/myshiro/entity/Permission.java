package com.itdy.hqsm.security.myshiro.entity;



import com.itdy.hqsm.security.myshiro.utils.SysBaseEntity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**          资源
 * @ClassName:
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2019/10/13
 * @Author Administrator
 */
public class Permission extends SysBaseEntity {

    private long pid;
    private String pname;         //资源名称
    private String type;          // 资源类型：menu,button,
    private String url;           //访问url地址
    private String percode;       //权限代码字符串
    private Long parentid;        //父结点id
    private String parentids;     //父结点id列表串
    private String sortstring;    //排序
    private int available;        // 是否可用
    private String parentName;    //上级菜单名称
    private String types;         //    菜单类型
    private String icon;          //图标
    private Boolean open;         //ztree属性
    private List<?> list;
    private Set<Role> roles = new HashSet<>();





    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
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

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }



    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPercode() {
        return percode;
    }

    public void setPercode(String percode) {
        this.percode = percode;
    }

    public Long getParentid() {
        return parentid;
    }

    public void setParentid(Long parentid) {
        this.parentid = parentid;
    }

    public String getParentids() {
        return parentids;
    }

    public void setParentids(String parentids) {
        this.parentids = parentids;
    }

    public String getSortstring() {
        return sortstring;
    }

    public void setSortstring(String sortstring) {
        this.sortstring = sortstring;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }


    @Override
    public String toString() {
        return "Permission{" +
                "pid=" + pid +
                ", pname='" + pname + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", percode='" + percode + '\'' +
                ", parentid=" + parentid +
                ", parentids='" + parentids + '\'' +
                ", sortstring='" + sortstring + '\'' +
                ", available=" + available +
                ", parentName='" + parentName + '\'' +
                ", types='" + types + '\'' +
                ", icon='" + icon + '\'' +
                ", open=" + open +
                ", list=" + list +
                ", roles=" + roles +
                '}';
    }
}
