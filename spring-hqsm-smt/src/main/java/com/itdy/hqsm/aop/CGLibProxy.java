package com.itdy.hqsm.aop;
import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
/**
 *  CGLib  代理  测试
 cglib动态代理是利用asm开源包，对代理对象类的class文件加载进来，通过修改其字节码生成子类来处理。

 *
 * CGLIB是针对类实现代理，主要是对指定的类生成一个子类，覆盖其中的方法
 *    因为是继承，所以该类或方法最好不要声明成final
 */
public class CGLibProxy  implements MethodInterceptor{


    // CGLib需要代理的目标对象
    private Object targetObject;

    /**
     * 获取 返回代理对象
     * @param obj
     * @return
     */
    public Object createProxyObject(Object obj) {
        this.targetObject = obj;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(obj.getClass());
        enhancer.setCallback(this);
        Object proxyObj = enhancer.create();
        return proxyObj;
    }

    /**
     *
     * @param proxy
     * @param method
     * @param args
     * @param methodProxy
     * @return
     * @throws Throwable
     */
    public Object intercept(Object proxy, Method method, Object[] args,
                            MethodProxy methodProxy) throws Throwable {
        Object obj = null;
        //// 过滤方法
        if ("addUser".equals(method.getName())) {
            // 检查权限
            checkPopedom();
        }
        obj = method.invoke(targetObject, args);

        //if (obj!=null){
        add();
        //}
        return obj;
    }


    /**
     * 测试
     */
    private void checkPopedom() {
        System.out.println("---------检查权限  checkPopedom()!");
    }


    /**
     * 测试
     */
    private void add() {
        System.out.println("---------日志记录成功  --------------");
    }

}
