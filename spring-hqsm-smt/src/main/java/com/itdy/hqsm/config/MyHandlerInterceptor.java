package com.itdy.hqsm.config;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itdy.hqsm.common.lang.DateUtils;
import com.itdy.hqsm.redis.RedisUtil;
//import com.itdy.hqsm.security.hqsm.entity.SysUser;
//import com.itdy.hqsm.security.hqsm.service.Impl.SysUserServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * 对请求controller 层进行拦截判断是否可以访问
 *  拦截器实现
 *
 */

public class MyHandlerInterceptor   extends HandlerInterceptorAdapter{

    @Autowired
    private RedisUtil redisUtil;


    //@Autowired
    //private SysloginuserMapper sysloginuserMapper;
    //@Autowired
    //private SysuserMapper sysuserMapper;
    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler)
            throws Exception {
        boolean isAccess = false;
        System.out.printf("-----------controllrt 之前调用------------------>\n");
        // 在请求处理之前进行调用（Controller方法调用之前）,返回true才会继续往下执行，返回false取消当前请求
        Subject subject = SecurityUtils.getSubject();
        boolean authenticated = subject.isAuthenticated();
      /*  SysUser sysUser =(SysUser) request.getUserPrincipal();
        if(sysUser!=null){
            Date updateDate = sysUser.getUpdateDate();
            long time = updateDate.getTime();
            long currentTime = System.currentTimeMillis() + 60 * 60 * 1000;
            long sysTime = System.currentTimeMillis();
            request.getSession().setAttribute("user", sysUser);
             request.getSession().setAttribute("token", value);
            isAccess =true;
        }*/
       // return isAccess;
        return true;
    }


    /**
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param modelAndView
     * @throws Exception
     */
     /* public void postHandle(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            Object o,
            ModelAndView modelAndView) throws Exception {

        System.out.println("------------请求处理之后进行调用，但是在视图被渲染之前--------------被调用");
      }*/


    /**
     * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行
     * （主要是用于进行资源清理工作）
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e
     * @throws Exception
     */
   /* public void afterCompletion(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            Object o,
            Exception e) throws Exception {
        System.out.println("-----请求结束----被调用\n");
    }*/

}
