package com.itdy.hqsm.security.myshiro.mapper;


import com.itdy.hqsm.security.myshiro.entity.Department;
import com.itdy.hqsm.security.myshiro.entity.UserDepartment;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;


import java.util.List;

/**
 * @ClassName:
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2019/10/13
 * @Author Administrator
 * 部门列表
 *
 */
@Mapper
public interface DepartmentMapper   extends BaseMapper<Department> {


    //查找部门列表
    List<Department> findDepartMentList(Department department);
    //删除部门
    void del(Department department);
    //修改部门
    void update(Department department);
    //新增部门
    void add(Department department);
    // 更新 用户-部门表
    void update_user_department(UserDepartment user_department);
    // 保存 用户-部门表
    void save_user_department(UserDepartment user_department);
    //删除用户-部门表
    void del_user_departmentt(UserDepartment user_department);

}
