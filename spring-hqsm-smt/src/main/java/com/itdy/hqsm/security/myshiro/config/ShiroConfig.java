package com.itdy.hqsm.security.myshiro.config;


import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;


import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;








/**
 * @ClassName:
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2019/9/21
 * @Author Administrator
 */

@Configuration
public class ShiroConfig  {


    /**
     *
     * @return
     */
    @Bean(name="securityManager")
    public SecurityManager securityManager( @Qualifier(value = "myShiroRealm") MyShiroRealm myShiroRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(myShiroRealm);
        // 自定义缓存实现 使用redis
        securityManager.setCacheManager(cacheManager());
        // 自定义session管理 使用redis
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }


    @Bean
    public ShiroFilterFactoryBean shrioFilter( @Qualifier(value = "securityManager") SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
       shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 没有登陆的用户只能访问登陆页面
        //----shiroFilterFactoryBean.setLoginUrl("/auth/login");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/shiro/index");

        //自定义拦截器
        Map<String, Filter> filtersMap = new LinkedHashMap<String, Filter>();
        //限制同一帐号同时在线的个数。
        //filtersMap.put("kickout", kickoutSessionControlFilter());
        //shiroFilterFactoryBean.setFilters(filtersMap);
        // 权限控制map.
        Map<String, String> linkMap = new LinkedHashMap<String, String>();

        linkMap.put("/bootstrap/**", "anon");
       /* linkMap.put("/commmJs/**", "anon");
        linkMap.put("/js/**", "anon");
        linkMap.put("/layui/**", "anon");
        linkMap.put("/fonticon-picker/**", "anon");
        linkMap.put("/payproperties/**", "anon");
        linkMap.put("/process-editor/**", "anon");
        linkMap.put("/web-upload/**", "anon");
        linkMap.put("/ztree/**","anon");
        linkMap.put("/stencilset.json","anon");
        linkMap.put("/music/**","anon");*/
        //linkMap.put("/shiro/logins", "anon");
        //无需认证可以登陆 （方法）
        linkMap.put("/text","anon");           //测试页面
        linkMap.put("/login","anon");
        linkMap.put("/userRegister","anon");   //注册页面（方法）
        linkMap.put("/logout","anon");         //退出登录/shiro/logins
        linkMap.put("/shiro/logins","anon");   //登陆页面（方法）
        linkMap.put("/mongo/**","anon");   //mongodb 测试
        //linkMap.put("/kafka/**","anon");   //kafka 测试
        linkMap.put("/con/**","anon");
       // linkMap.put("/shiro/add","anon");
        linkMap.put("/swagger-ui.html", "anon");
        linkMap.put("/swagger/**", "anon");
        linkMap.put("/swagger-resources/**", "anon");
        linkMap.put("/v2/**", "anon");
        linkMap.put("/webjars/**", "anon");
        linkMap.put("/configuration/**", "anon");

        linkMap.put("/shiro/logout", "logout");
        linkMap.put("/shiro/kickout", "anon");
       // linkMap.put("/**", "authc,kickout");

        //必须认证才可以访问（方法）
        linkMap.put("/**","authc");
        linkMap.put("/*", "authc");//表示需要认证才可以访问
        //该资源必须得到权限才可以访问 （方法）
        linkMap.put("/shiro/add","perms[user:add]");
        linkMap.put("/shiro/add","perms[user:update]");
        //登陆页跳（方法）
        shiroFilterFactoryBean.setLoginUrl("/");
        //登陆成功页
        //shiroFilterFactoryBean.setSuccessUrl("/index/index");
        //未设置权限
        // shiroFilterFactoryBean.setUnauthorizedUrl("/shiro/unAuth");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(linkMap);
        //shiroFilterFactoryBean.setSecurityManager(securityManager);
        return shiroFilterFactoryBean;
    }
         /*
    	  anon		org.apache.shiro.web.filter.authc.AnonymousFilter
   		  authc		org.apache.shiro.web.filter.authc.FormAuthenticationFilter
    	  authcBasic	org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter
    	  perms		org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter
		  port		org.apache.shiro.web.filter.authz.PortFilter
		  rest		org.apache.shiro.web.filter.authz.HttpMethodPermissionFilter
		  roles		org.apache.shiro.web.filter.authz.RolesAuthorizationFilter
		  ssl			org.apache.shiro.web.filter.authz.SslFilter
		  user		org.apache.shiro.web.filter.authc.UserFilter
		  @RequiresAuthentication
          表示当前 Subject 已经通过 login 进行了身份验证；即 Subject.isAuthenticated() 返回 true。
          @RequiresUser
          表示当前 Subject 已经身份验证或者通过记住我登录的。
          @RequiresGuest
          表示当前 Subject 没有身份验证或通过记住我登录过，即是游客身份。
          @RequiresRoles(value={“admin”, “user”}, logical= Logical.AND)
          表示当前 Subject 需要角色 admin 和 user。
          @RequiresPermissions (value={“user:a”, “user:b”}, logical= Logical.OR)
          表示当前 Subject 需要权限 user：a 或 user：b。
          */



    /**
     *  thymeleaf结合shiro 配置
     * @return    ShiroDialect
     */
    @Bean
    public ShiroDialect getShiroDialect() {
        return new  ShiroDialect();
    }


    /**
     * 凭证匹配器
     * 由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     * @return
     */
   /* @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("SHA-1");//散列算法:这里使用sha-1算法;
        hashedCredentialsMatcher.setHashIterations(1024);//散列的次数，比如散列两次；
        //hashedCredentialsMatcher.doCredentialsMatch("","")
        // hashedCredentialsMatcher.getHashIterations();
        // hashedCredentialsMatcher.setStoredCredentialsHexEncoded();
        return hashedCredentialsMatcher;
    }*/


    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    /**
     * 配置shiro redisManager
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost("localhost");
        redisManager.setPort(6379);
        redisManager.setTimeout(18000);
        redisManager.setExpire(18000);// 配置缓存过期时间
        redisManager.setTimeout(0);
        // redisManager.setPassword(password);
        return redisManager;
    }

    /**
     * Session Manager
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        return sessionManager;
    }

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    /**
     * 限制同一账号登录同时登录人数控制
     *
     * @return
     */
    /*  @Bean
    public MyAccessControlFilter kickoutSessionControlFilter() {
        MyAccessControlFilter kickoutSessionControlFilter = new MyAccessControlFilter();
        kickoutSessionControlFilter.setCacheManager(cacheManager());
        kickoutSessionControlFilter.setSessionManager(sessionManager());
        kickoutSessionControlFilter.setKickoutAfter(false);
        kickoutSessionControlFilter.setMaxSession(1);
       // kickoutSessionControlFilter.setKickoutUrl("/shiro/kickout");
        return kickoutSessionControlFilter;
    }*/


    /***
     * 授权所用配置
     *
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    /***
     * 使授权注解起作用不如不想配置可以在pom文件中加入
     * <dependency>
     *<groupId>org.springframework.boot</groupId>
     *<artifactId>spring-boot-starter-aop</artifactId>
     *</dependency>
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * Shiro生命周期处理器
     *
     */
    @Bean
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


    /**
     * 配置自定义的密码比较器
     */
    @Bean(name="mySimpleCredentialsMatcher")
    public MySimpleCredentialsMatcher credentialsMatcher() {
        return new MySimpleCredentialsMatcher();
    }

    /*身份认证realm; (这个需要自己写，账号密码校验；权限等)
    *配置自定义的权限登录器
    */
    @Bean(name = "myShiroRealm")
    public MyShiroRealm authRealm(@Qualifier("mySimpleCredentialsMatcher") MySimpleCredentialsMatcher matcher) {
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        myShiroRealm.setCredentialsMatcher(matcher);
        return myShiroRealm;
    }



    /**
     *
     * @return
     */
    /*@Bean
    public SimpleCookie cookie() {
        // cookie的name,对应的默认是 JSESSIONID
        SimpleCookie cookie = new SimpleCookie("SHARE_JSESSIONID");
        cookie.setHttpOnly(true);
        //  path为 / 用于多个系统共享 JSESSIONID
        cookie.setPath("/");
        return cookie;
    }*/

    /**
     * 手动加载
     * @param
     * @throws BeansException
     */
  /* public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
       MyShiroRealm userRealm = (MyShiroRealm) applicationContext.getBean(MyShiroRealm.class);
        DefaultWebSecurityManager defaultWebSecurityManager = (DefaultWebSecurityManager) applicationContext.getBean(SecurityManager.class);
        defaultWebSecurityManager.setRealm(userRealm);
    }*/


    /*
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
     */
   /* @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }*/


    /**
     * Session ID 生成器
     * create
     * @return JavaUuidSessionIdGenerator
     */
   /* @Bean
    public JavaUuidSessionIdGenerator sessionIdGenerator() {
        return new JavaUuidSessionIdGenerator();
    }*/

   /*//自己定义session
    public    DefaultWebSessionManager  getDefaultWebSessionManager(){
        return null;
    }*/



}
