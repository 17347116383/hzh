package com.itdy.hqsm.modes.controller;

import com.itdy.hqsm.common.collect.ListUtils;
import com.itdy.hqsm.common.collect.MapUtils;
import com.itdy.hqsm.common.collect.SetUtils;
import com.itdy.hqsm.common.lang.DateUtils;
import com.itdy.hqsm.common.mapper.JsonMapper;
//import com.itdy.hqsm.modes.service.UserService;
//import com.itdy.hqsm.modes.service.impl.UserServiceImpl;
import com.itdy.hqsm.redis.RedisUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.HashMap;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
//import com.itdy.hqsm.modes.entity.User;


//@CrossOrigin(origins = "http://domain2.com", maxAge = 3600)
//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/s")
public class RedisControllers {

    private static final Log LOG = LogFactory.getLog(RedisControllers.class);
    private final String s = DateUtils.formatDateTime(new Date());

    @Autowired
    private RedisUtil red;
    //@Autowired
   // private UserService userService;

    /**
     * SCHOOL_OF_GRADUATION
     * @param id
     * @return
     */
 /*    @RequestMapping(value = "/get")
    @ResponseBody
    public  User   GetUse(String  id ) {
       // List<User> u = userServiceImpl.getU();
        LOG.info("12333333333333333333q");
        return  new User("900000","123",19,s);
    }*/



    /**
     * 插入数据    /s/get1
     * @return
     */
   /* @RequestMapping(value = "/get1")
    @ResponseBody
    public String   sysUserIn(
            Model model,
            ModelAndView mv,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request,
            HttpServletResponse response) {
        //其实redirectAttributes是继承了 MODEL 两者都可以使用    ModelAndView 也可以
        List<User>  list = ListUtils.newArrayList();
        for (int i = 0; i < 5; i++) {
            User u = new User(UUID.randomUUID().toString().replaceAll("-", ""),
                    "names"+i,   88+i,  s );


            list.add(u);
        }
        int sysUserInsert = userService.SysUserInsert(list);
        //String json = JsonMapper.toJson(list);


        boolean lSet = red.lSet("5555", list);
        System.out.println("llllllll"+lSet);
        System.out.println("llllllll"+lSet);
        System.out.println("llllllll"+lSet);
        List<Object> lGet = red.lGet("5555",0,4);
        for (Iterator iterator = lGet.iterator(); iterator.hasNext();) {
            List object = (List) iterator.next();
            System.out.println("---------000>"+object.toString());
        }

        //model.addAttribute("list", list);
        // model.addAttribute(list);
        String json = JsonMapper.toJson(list);
        return  json;
    }*/




    /**
     *
     * @return
     */
   /* @RequestMapping(value = "/get2")
    @ResponseBody
    public int   sysUsers(
            Model model,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request,
            HttpServletResponse response) {
        //其实redirectAttributes是继承了 MODEL   两者都可以使用
        Set<User>  set = SetUtils.newHashSet();
        for (int i = 0; i < 5; i++) {
            User u = new User(UUID.randomUUID().toString().replaceAll("-", ""),
                    "names"+i,   88+i,  s );

            set.add(u);
        }
        int sysUserInsert = userService.SysUserInsert(set);
        long sSet = red.sSet("12",set);
        System.out.println("--------->"+sSet);
        //获取数据
        Set<Object> sGet = red.sGet("12");
        for (Iterator iterator = sGet.iterator(); iterator.hasNext();) {
            Object object = (Object) iterator.next();
            System.out.println("************"+object.toString());
        }
        return  sysUserInsert;
    }
*/


    /**
     *
     * @return
     */
   /* @RequestMapping(value = "/get3")
    @ResponseBody
    public HashMap<String, Object> sysUser(
            Model model,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request,
            HttpServletResponse response) {
        //其实redirectAttributes是继承了 MODEL   两者都可以使用
        HashMap<String, Object> newHashMap = MapUtils.newHashMap();

        for (int i = 0; i < 5; i++) {
            User u = new User(UUID.randomUUID().toString().replaceAll("-", ""),
                    "names"+i,   88+i,  s );

            newHashMap.put("u"+i, u);
        }

        boolean hmset = red.hmset("123", newHashMap);
        System.out.println("------>"+hmset);

        Map<Object, Object> hmget = red.hmget("123");
        for (Entry<Object, Object> entry : hmget.entrySet()) {
            //Map.entry<Integer,String> 映射项（键-值对）  有几个方法：用上面的名字entry
            //entry.getKey() ;entry.getValue(); entry.setValue();
            //map.entrySet()  返回此映射中包含的映射关系的 Set视图。
            System.out.println("key= " + entry.getKey() + " and value= "
                    + entry.getValue());
        }
        return  newHashMap;
    }
*/
}
