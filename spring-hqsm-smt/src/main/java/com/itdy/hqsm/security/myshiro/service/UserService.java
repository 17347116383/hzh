package com.itdy.hqsm.security.myshiro.service;

import com.itdy.hqsm.security.myshiro.entity.User;

import org.springframework.transaction.annotation.Transactional;


import java.util.List;

/**
 * @ProjectName: hqsm-parent
 * @Package: com.itdy.hqsm.security.myshiro.service
 * @ClassName: UserService
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/10/18 20:54
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public interface UserService {

    @Transactional(rollbackFor = Exception.class)
    public void controllerSave(User user) ;

    //根据用户账号查询用户信息
    public User findByUserName(String userName);

    //查找用户表（包含角色和部门的权限）
    public List<User> findUserList(User user);

    //更新
    public void update(User user);

    //查找用户表
    public List<User> findOnlyUserList(User user);

    //保存用户
    public boolean save(User user) ;

    //删除用户
    public void del(User user) ;

    //修改
    public void updates(String userName,String email,String phone,Integer uid) ;

    //
    User  getSalt (String userAccount);
}
