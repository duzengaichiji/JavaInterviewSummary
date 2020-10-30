package DesignPattern.AOP;

import net.sf.cglib.proxy.Enhancer;

public class CglibProxy {
    public static void main(String[] args) {
        /*
        cglib有着和jdk差不多的易用性，以及远高于jdk动态代理的调用性能
         */
        //通过enhancer来创建代理对象
        Enhancer enhancer = new Enhancer();
        enhancer.setCallback(new CglibInterceptor(new AopApiImpl()));
        enhancer.setInterfaces(new Class[]{AopApi.class});
        AopApi aopApiProxy = (AopApi) enhancer.create();
        aopApiProxy.method();
        System.out.println(aopApiProxy.getClass());
    }
}
