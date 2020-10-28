package DesignPattern.SingleTon;

/**
 * @ClassName SingleTonDoublecheck
 * @Author nhx
 * @Date 2020/10/28 20:53
 **/
public class SingleTonDoublecheck {
    private SingleTonDoublecheck(){}

    //这里不加volatile似乎也可以，但是加了volatile似乎可以减少进行第二重判断的次数，能提高效率？
    private static volatile SingleTonDoublecheck instance = null;

    public static SingleTonDoublecheck getInstance(){
        if(instance==null) {
            synchronized (SingleTonDoublecheck.class) {
                //可以屏蔽这一层判断试试
                if(instance==null){
                    instance = new SingleTonDoublecheck();
                }
            }
        }
        return instance;
    }
}
