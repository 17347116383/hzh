package com.itdy.hqsm.security.myshiro.service.Impl;

import com.itdy.hqsm.security.myshiro.entity.DesktopSet;

import com.itdy.hqsm.security.myshiro.mapper.DesktopSetMapper;
import com.itdy.hqsm.security.myshiro.service.DesktopSetService;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Sara
 * @since 2019-06-03
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DesktopSetServiceImpl implements DesktopSetService {


    @Autowired(required = true)
    private DesktopSetMapper desktopSetMapper;
    /*@Autowired(required = true)
      public void setDesktopSetMapper(DesktopSetMapper desktopSetMapper) {
        this.desktopSetMapper = desktopSetMapper;
    }*/

    //修改角色
    public void update(DesktopSet dept) {
       // desktopSetMapper.delPer(dept);
        ((DesktopSetServiceImpl) AopContext.currentProxy()).savePer(dept);
    }


    public void savePer(DesktopSet dept) {
        DesktopSet desktop = new DesktopSet();
       Integer uid=dept.getUserId();
      //  desktopSetMapper.delPer(dept);
//        ((DesktopServiceImpl) AopContext.currentProxy()).delPer(desktop, dept);
        List<Long> desktopList = dept.getDesktopList();
        desktop.setUserId(uid);
        for (Long de : desktopList) {
            desktop.setSysDivId(Integer.parseInt(de+""));
//            permission_role.setSys_permission_id(Integer.parseInt(pe + ""));
        //    desktopSetMapper.save(desktop);
        }
    }

    /**
     *
     * @param desktopSet
     * @return
     */
    public List<DesktopSet> find_desktop_list(DesktopSet desktopSet) {
      //  return desktopSetMapper.find_desktop_list(desktopSet);
        return null;
    }

    @Override
    public List<DesktopSet> findDesktop(DesktopSet desktopSet) {
       // return desktopSetMapper.findDesktop(desktopSet);
        return null;
    }

    /*@Override
    public List<DesktopSet> findDesktop(DesktopSet desktopSet) {
        desktopMapper.findDesktop(desktopSet.getId());
        return  null;
    }*/

    @Override
    public void delPer(DesktopSet dept) {

    }

    @Override
    public void save(DesktopSet desktopSet) {

    }


//    public void delPer(Desktop desktop, Desktop dept) {
//        permission_role.setSys_role_id(Integer.parseInt(role.getRid()));
//        permissonBiz.del_permission_role(permission_role);
//    }

}
