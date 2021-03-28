package com.itdy.hqsm.security.myshiro.controller;

import com.alibaba.fastjson.JSON;


//import com.itdy.hqsm.security.myshiro.config.MyUsernamePasswordToken;
import com.itdy.hqsm.security.myshiro.config.MyUsernamePasswordToken;
import com.itdy.hqsm.security.myshiro.entity.Permission;
import com.itdy.hqsm.security.myshiro.entity.User;
import com.itdy.hqsm.security.myshiro.mapper.UserMapper;
import com.itdy.hqsm.security.myshiro.service.PermissionService;
import com.itdy.hqsm.security.myshiro.service.UserService;

import com.itdy.hqsm.security.myshiro.utils.Constant;
import com.itdy.hqsm.security.myshiro.utils.ShiroUserUtils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
/*import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;*/


 /**
 * @ClassName:用户登陆
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2019/10/13
 * @Author Administrator
 */

@Controller
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;
    @Autowired
    private PermissionService permissonService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;


     /**
      * 登录
      * @return
      */
    @RequestMapping(value = "/")
    public String login() {
        Subject subject = SecurityUtils.getSubject();
        boolean authenticated = subject.isAuthenticated();
       if (authenticated) {
            return "redirect:index";
        }
        return "login/login";
    }

     /**
      *  登录接口
      * @param model
      * @param user
      * @return
      */
    @RequestMapping(value = "/login")
    public String index(Model model, User user) {
        Subject subject = SecurityUtils.getSubject();
        try {
           // User userInfo = userService.getOne(new QueryWrapper<User>().lambda().eq(User::getUserAccount, user.getUserAccount()));
            User userInfo =userService.getSalt(user.getUserAccount());
            if(userInfo==null){
                return "redirect:";
            }
            MyUsernamePasswordToken usernamePasswordToken = new MyUsernamePasswordToken(user.getUserAccount(), user.getUserPassword(),1,userInfo.getSalt());
            //使用Shiro内置的“记住我”功能
            //usernamePasswordToken.setRememberMe(true);
            //完成登录
            subject.login(usernamePasswordToken);
            model.addAttribute("message", "0");
        } catch (AuthenticationException e) {
            String error = e.getClass().toString();
            if (error.contains("UnknownAccountException")) {
                model.addAttribute("message", "1");
            }
            if (error.contains("IncorrectCredentialsException")) {
                model.addAttribute("message", "2");
            }
            if (error.contains("DisabledAccountException")) {
                model.addAttribute("message", "3");
            }
            if (error.contains("AuthenticationException")) {
                model.addAttribute("message", "4");
            }
            log.error(error + ">>>>>>>>>>>>登录出错");
        }
        return "redirect:index";
    }

    /**
     * 登陆成功页面跳转
     * @return
     */
    @RequestMapping(value = "/index")
    public String index() {
        User shiroUser = ShiroUserUtils.getShiroUser();
        String userAccount=shiroUser.getUserAccount();
        Map<String, Object> vars = new HashMap<>();
        List<Permission> mentList = permissonService.selectMentLists();
        User user=userMapper.findUser(userAccount);
        vars.put("user",user);
        vars.put("mentlist",mentList);
        thymeleafViewResolver.setStaticVariables(vars);
        log.debug(shiroUser.getUserName() + "登录成功");
        return "index/index";
    }



    @RequestMapping(value = "/bootm.do")
    public String bootm() {
        return "index/bootm";
    }


     /**
      * 退出
      * @param model
      * @return
      */
    @GetMapping(value = "/logout")
    public String logout(Model model) {
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.getSession() != null) {
            User shiroUser = ShiroUserUtils.getShiroUser();
            if (shiroUser != null) {
                currentUser.logout();

                log.debug(shiroUser.getUserName() + ">>>>>>>>>>>>退出登录");
            }
        }
        return "redirect:";
    }


    //国际化切换
    @RequestMapping(value = "/changeLang")
    @ResponseBody
    public String il8nChange(String lang, HttpServletRequest request) {

        if ("zh_cn".equals(lang)) {
            Locale locale = new Locale("zh", "CN");
            request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
            request.getSession().setAttribute("lan", "zh_CN");
        } else if ("en_us".equals(lang)) {
            Locale locale = new Locale("en", "US");
            request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
            request.getSession().setAttribute("lan", "en_US");
        } else {
            request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, LocaleContextHolder.getLocale());
            request.getSession().setAttribute("lan", "zh_CN");
        }
        return JSON.toJSONString("success");
    }


     /**
      *
      * @return
      */
    @RequestMapping(value = "/skin-config")
    @ResponseBody
    public String skin() {

        return "skin-config";
    }


}
