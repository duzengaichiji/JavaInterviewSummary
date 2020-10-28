package DesignPattern.SingleTon;

/**
 * @ClassName SingleTonLazy
 * @Author nhx
 * @Date 2020/10/28 20:49
 **/
public class SingleTonLazy {
    private SingleTonLazy(){}

    private static SingleTonLazy instance = null;
    //会有并发问题
    public static SingleTonLazy getInstance(){
        //检测到instance为空再调用
        if(instance==null){
            instance = new SingleTonLazy();
        }
        return instance;
    }
}
