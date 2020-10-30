package DesignPattern.Decorator;

/**
 * @ClassName sChild
 * @Author nhx
 * @Date 2020/10/30 20:04
 **/
public class sChild implements Child,Student{
    @Override
    public void eat() {
        System.out.println("eating shit!!");
    }

    @Override
    public void readBook() {
        System.out.println("study!!!");
    }
}
