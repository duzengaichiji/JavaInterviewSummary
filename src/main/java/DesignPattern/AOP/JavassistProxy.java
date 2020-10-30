package DesignPattern.AOP;

import javassist.*;

public class JavassistProxy {
    public static void main(String[] args) throws CannotCompileException, IllegalAccessException, InstantiationException, NotFoundException {
        //一般获取默认的
        ClassPool mPool = ClassPool.getDefault();
        //创建一个空的类，名字为***
        CtClass mctc = mPool.makeClass(AopApi.class.getName()+"JavaassistProxy");
        //这个不加会出问题，或者加了会出问题
        mPool.insertClassPath(new ClassClassPath(AopApi.class));
        /*
        由于javassisi是基于字节码生成类的，所以接下来要为这个类配置各种属性，相当复杂。。
         */
        //为其加入接口
        mctc.addInterface(mPool.get(AopApi.class.getName()));
        //加入构造器。。
        mctc.addConstructor(CtNewConstructor.defaultConstructor(mctc));
        //加入方法
        mctc.addMethod(CtNewMethod.make(
                        "public void method(){ System.out.print(\"call method by javassist proxy\") ; }", mctc));
        //获取这个Class对象
        Class<?> pc = mctc.toClass();
//      //调用class对象的构造器获取代理对象
        AopApi bytecodeProxy = (AopApi) pc.newInstance();

        System.out.println(bytecodeProxy.getClass());
        System.out.println(bytecodeProxy.getClass().getInterfaces()[0]);
        bytecodeProxy.method();
    }
}
