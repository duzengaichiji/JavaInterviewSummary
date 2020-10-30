package DesignPattern.Decorator;

/**
 * @ClassName sChildWrapper
 * @Author nhx
 * @Date 2020/10/30 20:06
 **/
public class sChildWrapper implements Child,Student{
    private sChild child;
    public sChildWrapper(sChild child){
        this.child = child;
    }
    @Override
    public void eat() {
        System.out.println("自己做饭");
        child.eat();
        System.out.println("吃完了");
    }

    @Override
    public void readBook() {
        System.out.println("读书");
        child.readBook();
    }
}
