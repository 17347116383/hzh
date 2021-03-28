package com.itdy.hqsm.security.myshiro.service.Impl;


import com.itdy.hqsm.security.myshiro.entity.User;
import com.itdy.hqsm.security.myshiro.entity.UserDepartment;
import com.itdy.hqsm.security.myshiro.entity.UserRole;
import com.itdy.hqsm.security.myshiro.mapper.UserMapper;
import com.itdy.hqsm.security.myshiro.service.DepartmentService;
import com.itdy.hqsm.security.myshiro.service.RoleService;
import com.itdy.hqsm.security.myshiro.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @ClassName:
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2019/10/13
 * @Author Administrator
 */
@Service
public class UserServiceImpl implements   UserService{

    private Logger log = LoggerFactory.getLogger(UserService.class);


    @Autowired(required=true)
    private RoleServiceImpl roleServiceImpl;

    @Autowired(required=true)
    private DepartmentServiceImpl departmentServiceImpl;

    @Autowired(required=true)
    private UserMapper userMapper;





    @Transactional(rollbackFor = Exception.class)
    public void  controllerSave(User user) {
        user.setRegisterDate(new Date());
        // userService.saveOrUpdate(user);
        if(user.getUid()==null){
            userMapper.save(user);
        }else{
            userMapper.update(user);
        }
        if (user.getRoleId() != null && !user.getRoleId().equals("0")) {
            UserRole user_role = new UserRole();
            user_role.setSys_user_id(user.getUid() + "");
            user_role.setSys_role_id(user.getRoleId() + "");
            roleServiceImpl.save_user_Role(user_role);
        }
        if (user.getDepartmentId() != null && !user.getDepartmentId().equals("0")) {
            UserDepartment user_department = new UserDepartment();
            user_department.setSys_user_id(user.getUid() + "");
            user_department.setSys_department_id(user.getDepartmentId());
            departmentServiceImpl.save_user_department(user_department);
        }
    }



    //根据用户账号查询用户信息
    public User findByUserName(String userName) {
        return userMapper.findByUserName(userName);
    }


    //查找用户表（包含角色和部门的权限）
    public List<User> findUserList(User user) {
        return userMapper.findUserList(user);
    }

    //修改
    public void update(User user) {
        userMapper.update(user);
    }


    //查找用户表
    public List<User> findOnlyUserList(User user) {
        return userMapper.findOnlyUserList(user);
    }


    public boolean save(User user) {
        return false;
    }

    //保存用户
    /*public boolean save(User user) {
    //  userMapper.save(user);
        return super.saveOrUpdate(user);
    }*/

    //删除用户
    public void del(User user) {
        //super.removeById(user.getUid());---------------------------
        //boolean b = super.deleteById(user.getUid());
        //  userMapper.del(user);
    }

    //修改
    public void updates(String userName,String email,String phone,Integer uid) {
        userMapper.updates(userName,email,phone,uid);
    }

    //
    public User getSalt(String userAccount) {
        return userMapper.getSalt(userAccount);
    }


}
