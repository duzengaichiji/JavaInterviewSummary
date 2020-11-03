package JVM;

import org.junit.Test;

/**
 * @ClassName classLoaderTest
 * @Author nhx
 * @Date 2020/11/3 21:01
 **/
public class classLoaderTest {
    @Test
    public void classLoaderTest1(){
        System.out.println(ChildClass.value);
    }

    @Test
    public void classLoaderTest2(){
        FatherClass fc[] = new FatherClass[10];
        for (int i = 0; i < 10; i++) {
            fc[i] = new FatherClass();
        }
    }

    @Test
    public void classLoaderTest3(){
        System.out.println(FatherClass.value);
    }
}
