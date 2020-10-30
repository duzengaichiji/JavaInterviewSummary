package DesignPattern.AOP;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibInterceptor implements MethodInterceptor {

    private Object delegate;

    public CglibInterceptor(Object delegate) {
        this.delegate = delegate;
    }

    public CglibInterceptor() {
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        if(method.getName().equals("method")){
            System.out.println("call method by cglib proxy");
        }
        return null;
    }
}
