package DesignPattern.Decorator;

import org.junit.Test;

/**
 * @ClassName DecoratorTest
 * @Author nhx
 * @Date 2020/10/30 20:13
 **/
public class DecoratorTest {
    @Test
    public void test1(){
        sChild sChild = new sChild();
        sChildWrapper sChildWrapper = new sChildWrapper(sChild);
        sChildWrapper.eat();
        sChildWrapper.readBook();
    }
}
