package com.itdy.hqsm.security.myshiro.service;


import com.itdy.hqsm.security.myshiro.entity.DesktopSet;

import java.util.List;

/**
 * @ProjectName: hqsm-parent
 * @Package: com.itdy.hqsm.security.myshiro.service
 * @ClassName: DepartmentService
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/10/18 20:06
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public interface DesktopSetService {


    //修改角色
    public void update(DesktopSet dept);


    public void savePer(DesktopSet dept) ;



    //-------------------------------------------------
    //查询角色权限表
    List<DesktopSet> find_desktop_list(DesktopSet desktopSet);
    //List<Desktop> findDesktop(Desktop desktop);
    //
    List<DesktopSet> findDesktop(DesktopSet desktopSet);
    //
    void delPer(DesktopSet dept);
    //新增
    void save(DesktopSet desktopSet);

}
