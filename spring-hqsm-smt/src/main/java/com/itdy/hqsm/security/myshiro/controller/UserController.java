package com.itdy.hqsm.security.myshiro.controller;



import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itdy.hqsm.security.myshiro.entity.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.itdy.hqsm.security.myshiro.entity.Collections;
import com.itdy.hqsm.security.myshiro.mapper.CollectionsMapper;
import com.itdy.hqsm.security.myshiro.mapper.UserMapper;
import com.itdy.hqsm.security.myshiro.service.DepartmentService;
import com.itdy.hqsm.security.myshiro.service.Impl.UserServiceImpl;
import com.itdy.hqsm.security.myshiro.service.RoleService;
import com.itdy.hqsm.security.myshiro.service.UserService;
import com.itdy.hqsm.security.myshiro.utils.BaseResult;
import com.itdy.hqsm.security.myshiro.utils.Constant;
import com.itdy.hqsm.security.myshiro.utils.ShiroUserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName:
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2019/10/13
 * @Author Administrator
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Resource
    private ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CollectionsMapper collectionsMapper;
    @Autowired
    private UserServiceImpl userServiceImpl;


      /*PageHelper.startPage(pageNum, pageSize);
        List<CtmsTaskInfo> list=ctmsTaskInfoServiceImpl.selectByName();
        PageInfo<CtmsTaskInfo> result=new PageInfo<CtmsTaskInfo>(list);
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("success", true);
        map.put("data", result);*/

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);


    /**
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public String index() {

        return "sys/user/userManage";
    }

    @RequestMapping(value = "/updateUser")
    public String updateUser() {
        User shiroUser = ShiroUserUtils.getShiroUser();
        String userAccount=shiroUser.getUserAccount();
        Map<String, Object> vars = new HashMap<>();
        User userNmae=userMapper.findUser(userAccount);
        vars.put("user",userNmae);
        thymeleafViewResolver.setStaticVariables(vars);
        return "sys/user/updateUser";
    }

    //收藏标签
    @RequestMapping(value = "/findCollections")
    @ResponseBody
    public ModelAndView findCollections() {
        User shiroUser = ShiroUserUtils.getShiroUser();
        String userAccount=shiroUser.getUserAccount();
        Long uid=shiroUser.getUid();
        String uids=String.valueOf(uid);
        ModelAndView models = new ModelAndView();
//        Map<String, Object> vars = new HashMap<>();
        List<Collections> collectionList =  collectionsMapper.findCollections(uids);
//        vars.put("collectionList",collectionList);
        models.addObject("collectionList", collectionList);
//        return "sys/user/collections";
        models.setViewName("sys/user/collections");
        return models;
    }

//天气预报
    @RequestMapping(value = "/forecast")
    public String forecast() {

        return "sys/user/forecast";
    }


    //桌面设置
    @RequestMapping(value = "/desktop")
    public String desktop() {
        return "sys/user/desktop";
    }

    //快捷意见
    @RequestMapping(value = "/opinion")
    public String opinion() {
        System.out.println("123");
        return "sys/user/opinionSet";
    }

    //修改个人资料
    @RequiresPermissions(value = "sys:info")
    @RequestMapping(value = "/updates")
    @ResponseBody
    public BaseResult updates( String userName, String email, String phone, Integer uid) {

       // String  phones = String.valueOf(uid);
        User shiroUser = ShiroUserUtils.getShiroUser();
        String userAccount=shiroUser.getUserAccount();
        Map<String, Object> vars = new HashMap<>();
        userService.updates(userName,email,phone,uid);
        return BaseResult.ok();
    }

    //跳转到修改密码
    @RequestMapping(value = "/changePassword")
    public String changePassword( String oldpassword,String password,String passwordAgin,Integer uid) {
        User shiroUser = ShiroUserUtils.getShiroUser();
        String userAccount=shiroUser.getUserAccount();
        Map<String, Object> vars = new HashMap<>();
        User userNmae=userMapper.findUser(userAccount);
        vars.put("user",userNmae);
        return "sys/user/changePassword";
    }


    //修改密码

    @RequiresPermissions(value = "sys:changePassword")
    @RequestMapping(value = "/updatePassword")
    @ResponseBody
    public BaseResult updatePassword(String oldpassword,String password,Integer uid) {
        User shiroUser = ShiroUserUtils.getShiroUser();
        String userAccount=shiroUser.getUserAccount();
        User user=new User();
        //数据库实际的密码
        User userTemp=userMapper.findUser(userAccount);
        String oldpass=userTemp.getUserPassword();
        System.out.println("数据库实际的密码"+oldpass);
        user.setUserAccount(userAccount);
        user.setUserPassword(password);
        user.setUid(Long.valueOf(uid));
       //输入的新密码跟数据库的实际密码进行判断   如果一致修改成功  如果不一致修改失败
        String credentialsSalt = userTemp.getUserAccount() + userTemp.getSalt();
        String oldPassword = new SimpleHash(Constant.HASH_ALGORITHM, oldpassword,
                ByteSource.Util.bytes(credentialsSalt), Constant.HASH_INTERATIONS).toHex();
        if(oldPassword.equals(userTemp.getUserPassword())){
            System.out.println("成功");
            ShiroUserUtils.encryptPassword(user);
            userService.update(user);
            return BaseResult.ok();

        }
        return BaseResult.error("原密码输入错误");

//        if (ShiroUserUtils.checkPasswordByMeixiang(userTemp,oldpassword)){
//            userBiz.update(user);
//            System.out.println("修改成功");
//        }else {
//            return R.error("与原密码不一致");
//        }
//        Map<String, Object> vars = new HashMap<>();
//        userBiz.updates(userName,email,phone,uid);
//        return R.ok();
    }

    /**
     * 上级部门Id(管理员则为0)
     */
    @RequestMapping("/info")
    @ResponseBody
    //@RequiresPermissions("sys:dept:list")
    public Map<String, Long> info() {
        Map<String, Long> map = new HashMap<String, Long>();
        long deptId = 0;
        User shiroUser = ShiroUserUtils.getShiroUser();
        if (shiroUser.getUserAccount() != "admin") {
            /*
            List<SysDeptEntity> deptList = sysDeptService.queryList(new HashMap<String, Object>());
            Long parentId = null;
            for (SysDeptEntity sysDeptEntity : deptList) {
                if (parentId == null) {
                    parentId = sysDeptEntity.getParentId();
                    continue;
                }
                if (parentId > sysDeptEntity.getParentId().longValue()) {
                    parentId = sysDeptEntity.getParentId();
                }
            }
            deptId = parentId;*/
        }
        map.put("deptId", deptId);
        return map;
    }


    @RequestMapping("/list")
    @ResponseBody
    public List<User> list(User user) {
        List<User> userList = userService.findOnlyUserList(user);
        return userList;
    }

    /**
     * 验证登录账号是否存在
     */


    @RequestMapping("/checkUserAccount")
    @ResponseBody
    public Map<String, Boolean> checkUserAccount(User user) {
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        Boolean valid = true;
        List<User> userList = userService.findOnlyUserList(user);
        if (!CollectionUtils.isEmpty(userList)) {
            valid = false;
        }
        map.put("valid", valid);
        return map;
    }


    /**
     * 选择角色(添加、修改菜单)
     */
    @RequestMapping("/select")
    @ResponseBody
    public Map<String, List<Role>> select(Role role) {
        Map<String, List<Role>> map = new HashMap<String, List<Role>>();
        List<Role> roleList = roleService.findRoleList(role);
        User shiroUser = ShiroUserUtils.getShiroUser();
        //添加一级部门
        if (shiroUser.getUserAccount().equals("admin")) {
            Role root = new Role();
            root.setRid("0");
            root.setRname("全部角色");
            root.setParentId(-1L);
            root.setOpen(true);
            roleList.add(root);
        }
        map.put("roleList", roleList);
        return map;
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{uid}")
    @ResponseBody
    public BaseResult info(@PathVariable("uid") Long uid) {
        User user = new User();
        user.setUid(uid);
        try {
            user = userService.findOnlyUserList(user).get(0);
        } catch (Exception e) {
            log.info("/info/{deptId}: 查询为空:" + e.toString());
        }
        return BaseResult.ok().put("user", user);
    }


    /**
     * 保存
     */
    @RequestMapping("/save")
    @ResponseBody
    public BaseResult save(@RequestBody User user) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        ShiroUserUtils.encryptPassword(user);
        try {
            userServiceImpl.controllerSave(user);
            hashMap.put("code", "0");
            return BaseResult.ok();
        } catch (Exception e) {
            System.out.println(e.toString());
            //  status = false;
            hashMap.put("code", "500");
            hashMap.put("msg", "注册失败");
            return BaseResult.error("注册失败");
        }
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public BaseResult update(@RequestBody JSONObject obj) throws ParseException {
          com.alibaba.fastjson.JSONObject parseObject = JSON.parseObject(obj.toJSONString());
        Map <String,Object> map = (Map<String,Object>)JSONObject.parse(parseObject.toString());
        String registerDate =(String) map.get("registerDate");
        SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        Iterator<Map.Entry<String, Object>> entries = map.entrySet().iterator();
        while(entries.hasNext()){
            Map.Entry<String, Object> entry = entries.next();
            if(entry.getKey().equals("registerDate")){
                entry.setValue(format.parse(registerDate));
            }
        }
         User user=JSON.parseObject(JSON.toJSONString(map), User.class);
         /* User r = JSONObject.toJavaObject(parseObject,User.class);
         Object users = JSON.parseArray(parseObject.toString(), User.class);
          User ctmsTempCheckRule = JSON.parseObject(parseObject.toString(), User.class);
          Object TempCheckRule= (Object)parseObject.get("CtmsTempCheckRule");
          CtmsTempCheckRule ctmsTempCheckRule = JSON.parseObject(TempCheckRule.toString(), CtmsTempCheckRule.class);
          List<CtmsTempCheckRule> ctmsTempCheckRule = JSON.parseArray(TempCheckRule.toString(), CtmsTempCheckRule.class);
          String userName = (String) parseObject.get("userName");  */
        ShiroUserUtils.encryptPassword(user);
        userService.update(user);
        UserRole user_role = new UserRole();
        user_role.setSys_user_id(user.getUid() + "");
        user_role.setSys_role_id(user.getRoleId() + "");
        roleService.update_user_Role(user_role);
        UserDepartment user_department = new UserDepartment();
        user_department.setSys_user_id(user.getUid() + "");
        user_department.setSys_department_id(user.getDepartmentId());
        departmentService.update_user_department(user_department);
        return BaseResult.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/del")
    @ResponseBody
    public BaseResult del(User user) {
        try {
            userService.del(user);
            UserDepartment user_department = new UserDepartment();
            user_department.setSys_user_id(user.getUid() + "");
            departmentService.del_user_departmentt(user_department);
            UserRole user_role = new UserRole();
            user_role.setSys_user_id(user.getUid() + "");
            roleService.del_user_Role(user_role);
        } catch (Exception e) {
            log.info("/user/del: 用户删除失败:" + e.toString());
            return BaseResult.error("删除用户失败");
        }
        return BaseResult.ok();
    }

}
