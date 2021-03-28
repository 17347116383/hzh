package com.itdy.hqsm.aop;

/**
 *
 *  测试 jdk  代理 与 cglib
 *
 */
public class ClientCeiShi {


   /* public static void main(String[] args) {

       // UserManager userManager = (UserManager) new CGLibProxy()
      //          .createProxyObject(new UserManagerImpl());
      //  userManager.delUser("tom");
      //  System.out.println("-----------CGLibProxy-------------");
      //  userManager.addUser("tom", "root");

       *//* System.out.println("-----------JDKProxy-------------");
        JDKProxy jdkPrpxy = new JDKProxy();
        UserManager userManagerJDK = (UserManager) jdkPrpxy
                .newProxy(new UserManagerImpl());
        userManagerJDK.addUser("tom", "root");*//*
    }
*/
}
