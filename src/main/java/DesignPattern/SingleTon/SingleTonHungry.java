package DesignPattern.SingleTon;

/**
 * @ClassName SingleTonHungry
 * @Author nhx
 * @Date 2020/10/28 20:39
 **/
public class SingleTonHungry {
    //私有化构造器，防止外部调用new
    private SingleTonHungry(){}
    //在类装载的过程中，直接赋予静态对象
    //好处是肯定不会发生线程安全问题了，坏处是可能会浪费空间（在该类没被用上的时候，仍然创建了该对象）
    private final static SingleTonHungry instance = new SingleTonHungry();

    public static SingleTonHungry getInstance(){
        return instance;
    }
}
