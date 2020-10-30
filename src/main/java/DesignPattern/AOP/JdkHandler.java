package DesignPattern.AOP;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class JdkHandler implements InvocationHandler {
    //被代理的对象
    private Object delegate;

    public JdkHandler(Object delegate) {
        this.delegate = delegate;
    }



    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //填写代理逻辑
        if(method.getName().equals("method")){
            System.out.println("call method from jdk invocation");
        }
        return null;
    }
}
