package DesignPattern.Decorator;

/**
 * @ClassName Parent
 * @Author nhx
 * @Date 2020/10/30 20:04
 **/
public class Parent implements Child{

    private Child child;

    public Parent(Child child){
        this.child = child;
    }

    @Override
    public void eat() {
        System.out.println("喂饭");
        child.eat();
        System.out.println("吃完了");
    }
}
