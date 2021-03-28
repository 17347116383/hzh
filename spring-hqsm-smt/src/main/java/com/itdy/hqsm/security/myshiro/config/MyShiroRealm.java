package com.itdy.hqsm.security.myshiro.config;

import com.itdy.hqsm.security.myshiro.entity.Permission;
import com.itdy.hqsm.security.myshiro.entity.Role;
import com.itdy.hqsm.security.myshiro.entity.User;

import com.itdy.hqsm.security.myshiro.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import java.lang.reflect.Array;
import java.util.*;

/**
 * @ClassName:
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2019/9/21
 * @Author Administrator
 */

public class MyShiroRealm extends AuthorizingRealm {


    private static org.slf4j.Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);
    //如果项目中用到了事物，@Autowired注解会使事物失效，可以自己用get方法获取值
    @Autowired
    private UserService userService;


    /**
     * 认证信息.(身份验证) : Authentication 是用来验证用户身份
     * @param authcToken
     * @return
     * @throws AuthenticationException
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        logger.info("----------------Shiro认证----------------------");
        //获取用户输入的Token
        MyUsernamePasswordToken token = (MyUsernamePasswordToken) authcToken;
        String name = token.getUsername();
        User user = new User();
        //user.setUserName(name);
        user.setUserAccount(name);
        // 从数据库获取对应用户名密码的用户
        /*throw new UnknownAccountException();*/
        User users = userService.findOnlyUserList(user).get(0);
        logger.info("---------------- 执行 Shiro 凭证认证 ----------------------");
        User gr = new User();
        if (isNotEmpty(userService.findByUserName(users.getUserAccount()))) {
            gr = userService.findByUserName(users.getUserAccount());
        }
        if (!isEmpty(gr)) {
            users.setRoles(gr.getRoles());
        }
        //放入shiro.调用CredentialsMatcher检验密码
        //SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(users, users.getUserPassword(),ByteSource.Util.bytes(name), getName());
        return new SimpleAuthenticationInfo(users, users.getUserPassword(),this.getClass().getName());

    }

    /**
     *  授权
     * @param principals
     * @return
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info("---------------- 执行 Shiro 权限获取 ---------------------");
        Object principal = principals.getPrimaryPrincipal();
        User user= (User) principal;
        List<String> permissions = new ArrayList<>();
        Set<Role> roles = user.getRoles();
        if (roles.size() > 0) {
           for (Role role : roles) {
               Set<Permission> modules = role.getPermissions();
               if (modules.size() > 0) {
                   for (Permission module : modules) {
                       if (module.getPercode() != null && module.getPercode() != "") {
                           permissions.add(module.getPercode());
                       }
                   }
               }
           }
       }
       SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
       //将权限放入shiro中
       simpleAuthorizationInfo.addStringPermissions(permissions);
       //获取资源路径
       //simpleAuthorizationInfo.addStringPermissions();
       //基于对象的权限的集合。
       // simpleAuthorizationInfo.addObjectPermission();
       return simpleAuthorizationInfo;
    }

    /**
     * 判断对象是否为空或null
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) return true;
        else if (obj instanceof CharSequence) return ((CharSequence) obj).length() == 0;
        else if (obj instanceof Collection) return ((Collection) obj).isEmpty();
        else if (obj instanceof Map) return ((Map) obj).isEmpty();
        else if (obj.getClass().isArray()) return Array.getLength(obj) == 0;

        return false;
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

}



