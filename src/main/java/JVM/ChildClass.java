package JVM;

/**
 * @ClassName ChildClass
 * @Author nhx
 * @Date 2020/11/3 20:59
 **/
public class ChildClass extends FatherClass{
    static {
        System.out.println("子类被加载");
    }
}
