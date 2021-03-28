package com.itdy.hqsm.security.myshiro.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.itdy.hqsm.security.myshiro.utils.SysBaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @ProjectName: hqsm-parent        权限
 * @Package: com.itdy.hqsm.security.myshiro.entity
 * @ClassName: Function
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/10/18 15:54
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class Function extends SysBaseEntity {

    private  int          functionId;  //权限ID
    private  int          parentId;    //权限父ID
    private  String       functionName;  //权限名
    private  String       functionUrl;   //权限URL
    private  int          functionType;  //权限类型 1菜单 2功能
    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //@JsonFormat(pattern="yyyy-MM-dd HH-mm-ss")
    private Date          createTime;    //创建时间
    private  int          sort;          //排序

    @Override
    public String toString() {
        return "Function{" +
                "functionId=" + functionId +
                ", parentId=" + parentId +
                ", functionName='" + functionName + '\'' +
                ", functionUrl='" + functionUrl + '\'' +
                ", functionType=" + functionType +
                ", createTime=" + createTime +
                ", sort=" + sort +
                '}';
    }

    public int getFunctionId() {
        return functionId;
    }

    public void setFunctionId(int functionId) {
        this.functionId = functionId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getFunctionUrl() {
        return functionUrl;
    }

    public void setFunctionUrl(String functionUrl) {
        this.functionUrl = functionUrl;
    }

    public int getFunctionType() {
        return functionType;
    }

    public void setFunctionType(int functionType) {
        this.functionType = functionType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
