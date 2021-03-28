package com.itdy.hqsm.security.myshiro.mapper;



import com.itdy.hqsm.security.myshiro.entity.Department;
import com.itdy.hqsm.security.myshiro.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.BaseMapper;
import java.util.List;
import java.util.Set;

/**
 * @ClassName:
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2019/10/13
 * @Author Administrator
 */
@Mapper
public interface UserMapper   extends BaseMapper<User> {

    //根据用户账号查询用户信息
    User findByUserName(String userName);
    //查找用户表
    List<User> findUserList(User user);

    //
    User  getSalt (String userAccount);

    //更新用户
    void update(User user);
    //查找用户表
    List<User> findOnlyUserList(User user);
    //保存用户
    void save(User user);
    //删除用户
    void del(User user);
   //根据用户id查询用户名称
    User findName(String userId);
    //查询出一级菜单
    List<Department> findNames();
    //查询出父级菜单的用户
    List<Department> findNameOne(String dept_id);
    //中间表关联信息
   //List<User_Department> findNameTwo (String dept_id);
    //查询出用户
    List<User> findNameTwo(String dept_id);
    //查找详情
    User findUser(@Param(value = "userAccount") String userAccount);
    //修改角色表
    void updates(String userName, String email, String phone, Integer uid);
    //根据部门编号查询部门名称
    Department queryDeptName(String deptId);


   /* @Select("SELECT sr.role_desc FROM sys_user_role sur LEFT JOIN sys_role sr ON sr.id = sur.role_id \n" +
            "LEFT JOIN sys_user su ON sur.user_id = su.id WHERE su.id = #{userId}")
    Set<String> findRoleNameByUserId(@Param("userId") int userId);*/

}
