package DesignPattern.AOP;

import java.lang.reflect.Proxy;

public class JdkProxy {
    public static void main(String[] args) {
        //通过Proxy生成代理对象，代理对象会调用innovationHandler的invoke方法
        AopApi aopApiProxy = (AopApi) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                new Class[]{AopApi.class},new JdkHandler(new AopApiImpl()));
        System.out.println(aopApiProxy.getClass());
        aopApiProxy.method();
    }
}
