package com.itdy.hqsm.aop;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 *
 *    JDK动态代理类    测试
 InvocationHandler是JDK动态代理的核心，生成的代理对象的方法调用都会委托到InvocationHandler.invoke()方法。
 而通过JdkDynamicAopProxy的签名可以看到这个类其实也实现了InvocationHandler，
 通过分析这个类中实现的invoke()方法来具体看下Spring AOP是如何织入切面的。

 * 实现InvocationHandler 
 * 使用Proxy.newProxyInstance产生代理对象
 * 被代理的对象必须要实现接口
 *CGLib 必须依赖于CGLib的类库，但是它需要类来实现任何接口代理的是指定的类生成一个子类，
 *覆盖其中的方法，是一种继承但是针对接口编程的环境下推荐使用JDK的代理
 *
 * JDK动态代理只能对实现了接口的类生成代理，而不能针对类
 实现AOP的技术，主要分为两大类：一是采用动态代理技术，利用截取消息的方式，对该消息进行装饰，
 以取代原有对象行为的执行；
 二是采用静态织入的方式，引入特定的语法创建“方面”，从而使得编译器可以在编译期间织入有关“方面”的代码。
 */
public class JDKProxy implements InvocationHandler {

    //需要代理的目标对象
    private Object targetObject;

    /**
     * //将目标对象传入进行代理
     * @param targetObject
     * @return
     */
    public Object newProxy(Object targetObject) {
        this.targetObject = targetObject;
        //返回代理对象
        return Proxy.newProxyInstance(targetObject.getClass().getClassLoader(),
                targetObject.getClass().getInterfaces(), this);
    }

    /**
     *    invoke方法
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        //一般我们进行逻辑处理的函数比如这个地方是模拟检查权限
        checkPopedom();
        // 设置方法的返回值
        Object ret = null;
        ret  = method.invoke(targetObject, args);       //调用invoke方法，ret存储该方法的返回值

        add();

        return ret;
    }

     /*
     模拟检查权限的例子
     *
     */
    private void checkPopedom() {
        System.out.println(".:检查权限  checkPopedom()!");
    }



    /**
     * 测试
     */
    private void add() {
        System.out.println("---------日志记录成功  --------------");
    }

}
