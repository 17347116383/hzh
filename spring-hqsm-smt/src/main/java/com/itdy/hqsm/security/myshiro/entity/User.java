package com.itdy.hqsm.security.myshiro.entity;




import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.tools.ant.types.Permissions;
import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @ClassName:        用户
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2019/10/13
 * @Author Administrator
 */

public class User implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long uid;
    private String userAccount;   //账号
    private String userName;      //姓名
    private String phone;         //手机号码
    private String email;         //邮箱
    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH-mm-ss")
    private Date registerDate;    //注册时间
    private Long locked;          //状态 账号是否锁定，1：锁定，0未锁定
    private String userPassword;  // 密码
    private String salt;          // 密码干扰
    private String roleId;        //角色id
    private String departmentId;  //部门 id
    private int orderNum;         // 排序
    private Integer type;         // 1 OA  2 商城

    //@DateTimeFormat(pattern = "yyyy-MM-dd") //入参
    //@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")//出参
    //@TableField(exist=false) SpringBoot 实体类   临时字段注解
    private String newPassword;// 商城修改密码
    private String roleName;       //角色名
    private String departmentName;   // 部门名字
    private String userDepartmentName;//用户和部门公用字段
    private String oldpassword;       //用户和部门公用字段
    private Set<Role> roles = new HashSet<>();

    /*private Permissions permissions;
    @Transient  Object ctms= (Object)parseObject.get("CtmsTaskInfo");
    public Permissions getPermissions() {
        return permissions;
    }
    public void setPermissions(Permissions permissions) {
        this.permissions = permissions;
    }*/

    @Transient
    public String getOldpassword() {
        return oldpassword;
    }
    public void setOldpassword(String oldpassword) {
        this.oldpassword = oldpassword;
    }
    @Transient
    public String getUserDepartmentName() {
        return userDepartmentName;
    }
    public void setUserDepartmentName(String userDepartmentName) {
        this.userDepartmentName = userDepartmentName;
    }
    public int getOrderNum() {
        return orderNum;
    }
    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }
    public String getRoleId() {
        return roleId;
    }
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
    public String getDepartmentId() {
        return departmentId;
    }
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
    @Transient
    public String getRoleName() {
        return roleName;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    @Transient
    public String getDepartmentName() {
        return departmentName;
    }
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    @Transient
    public Set<Role> getRoles() {
        return roles;
    }
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    public Long getUid() {
        return uid;
    }
    public void setUid(Long uid) {
        this.uid = uid;
    }
    public String getUserAccount() {
        return userAccount;
    }
    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }
    public String getUserName() { return userName; }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    //@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    public Date getRegisterDate() {
        return registerDate;
    }
    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }
    public Long getLocked() {
        return locked;
    }
    public void setLocked(Long locked) {
        this.locked = locked;
    }
    public String getUserPassword() {
        return userPassword;
    }
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
    public String getSalt() {
        return salt;
    }
    public void setSalt(String salt) {
        this.salt = salt;
    }
    public Integer getType() {
        return type;
    }
    public void setType(Integer type) {
        this.type = type;
    }
    @Transient
    public String getNewPassword() {
        return newPassword;
    }
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", userAccount='" + userAccount + '\'' +
                ", userName='" + userName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", registerDate=" + registerDate +
                ", locked=" + locked +
                ", userPassword='" + userPassword + '\'' +
                ", salt='" + salt + '\'' +
                ", roleId='" + roleId + '\'' +
                ", departmentId='" + departmentId + '\'' +
                ", orderNum=" + orderNum +
                ", type=" + type +
                ", newPassword='" + newPassword + '\'' +
                ", roleName='" + roleName + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", userDepartmentName='" + userDepartmentName + '\'' +
                ", oldpassword='" + oldpassword + '\'' +
                ", roles=" + roles +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return orderNum == user.orderNum &&
                Objects.equals(uid, user.uid) &&
                Objects.equals(userAccount, user.userAccount) &&
                Objects.equals(userName, user.userName) &&
                Objects.equals(phone, user.phone) &&
                Objects.equals(email, user.email) &&
                Objects.equals(registerDate, user.registerDate) &&
                Objects.equals(locked, user.locked) &&
                Objects.equals(userPassword, user.userPassword) &&
                Objects.equals(salt, user.salt) &&
                Objects.equals(roleId, user.roleId) &&
                Objects.equals(departmentId, user.departmentId) &&
                Objects.equals(type, user.type) &&
                Objects.equals(newPassword, user.newPassword) &&
                Objects.equals(roleName, user.roleName) &&
                Objects.equals(departmentName, user.departmentName) &&
                Objects.equals(userDepartmentName, user.userDepartmentName) &&
                Objects.equals(oldpassword, user.oldpassword) &&
                Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, userAccount, userName, phone, email, registerDate, locked, userPassword, salt, roleId, departmentId, orderNum, type, newPassword, roleName, departmentName, userDepartmentName, oldpassword, roles);
    }
/*
    @Null	限制只能为null
    @NotNull	限制必须不为null
    @AssertFalse	限制必须为false
    @AssertTrue	限制必须为true
    @DecimalMax(value)	限制必须为一个不大于指定值的数字
    @DecimalMin(value)	限制必须为一个不小于指定值的数字
    @Digits(integer,fraction)	限制必须为一个小数，且整数部分的位数不能超过integer，小数部分的位数不能超过fraction
    @Future	限制必须是一个将来的日期
    @Max(value)	限制必须为一个不大于指定值的数字
    @Min(value)	限制必须为一个不小于指定值的数字
    @Past	限制必须是一个过去的日期
    @Pattern(value)	限制必须符合指定的正则表达式
    @Size(max,min)	限制字符长度必须在min到max之间
    @Past	验证注解的元素值（日期类型）比当前时间早
    @NotEmpty	验证注解的元素值不为null且不为空（字符串长度不为0、集合大小不为0）
    @NotBlank	验证注解的元素值不为空（不为null、去除首位空格后长度为0），不同于@NotEmpty，@NotBlank只应用于字符串且在比较时会去除字符串的空格
    @Email	验证注解的元素值是Email，也可以通过正则表达式和flag指定自定义的email格式
    Hiberate Validator还支持以下注解

    注解	功能说明
    @Email	被注解的元素必须是电子邮箱地址
    @Length	被注释的字符串大小必须在指定的范围内
    @NotEmpty	注释的字符串必须非空
    @Range	注释的元素必须在合适的范围内
*/
}
