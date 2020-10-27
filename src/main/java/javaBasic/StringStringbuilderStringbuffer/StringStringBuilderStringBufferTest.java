package javaBasic.StringStringbuilderStringbuffer;

/**
 * @ClassName StringStringBuilderStringBufferTest
 * @Author nhx
 * @Date 2020/10/27 21:02
 **/
public class StringStringBuilderStringBufferTest {
    private final Runtime runtime = Runtime.getRuntime();
    public void test0(){
        /**
             * @Author nhx
             * @Description
             * @Date 21:05 2020/10/27
             * @Param []
             * @return 
             **/
        String s1 = "a"+"b"+"c";
        String s2 = "abc";
        System.out.println(s1==s2);//True
        System.out.println(s1.equals(s2));//True
    }

    public void test1(){
        String s1 = "javaEE";
        String s2 = "hadoop";
        String s = "javaEEhadoop";

        String s3 = s1+"hadoop";
        String s4 = "javaEE"+s2;

        System.out.println(s==s3);//false
        System.out.println(s3==s4);//false
    }

    public void test2(){
        String s1 = "javaEE";
        String s2 = "hadoop";
        String s = "javaEEhadoop";
        String s3 = s1+"hadoop";
        String s4 = "javaEE"+s2;
        String s5 = s4.intern();
        System.out.println(s==s5);//true
        System.out.println(s3==s5);//False
        System.out.println(s3==s4);//False
        System.out.println(s4==s5);//False
    }


    public void test3(){
        /**
             * @Author nhx
             * @Description String 拼接方式的内存占用
             * @Date 21:26 2020/10/27
             * @Param []
             * @return void
             **/
        String s = "a";
        for (int i = 0; i < 10086; i++) {
            String s1 = s+"b";
        }
        System.out.println((runtime.totalMemory()-runtime.freeMemory())+"B");//4028656B
    }

    public void test4(){
        String s = "a";
        String t = s+"b";
        for (int i=0;i<10086;i++){
            String s1 = t.intern();
        }
        System.out.println((runtime.totalMemory()-runtime.freeMemory())+"B");//3145728B
    }


    public void test5(){
        String s = "a";
        for (int i = 0; i < 10086; i++) {
            s = s+i;
        }
        System.out.println((runtime.totalMemory()-runtime.freeMemory())+"B");//86479056B
    }

    public void test6(){
        StringBuilder stringBuilder = new StringBuilder("a");
        for (int i = 0; i < 10086; i++) {
            stringBuilder.append(i);
        }
        System.out.println((runtime.totalMemory()-runtime.freeMemory())+"B");//3145728B
    }


    public void test7(){
        String s = new String("s1");
        s.intern();
        String s1 = "s1";
        System.out.println(s==s1);//false
        String s3 = new String("s1")+new String("s2");
        s3.intern();
        String s4 = "s1s2";
        System.out.println(s3==s4);//true
    }

    public static void main(String[] args) {
        StringStringBuilderStringBufferTest sssTest = new StringStringBuilderStringBufferTest();
        //sssTest.test0();
        //sssTest.test1();
        //sssTest.test2();
        //sssTest.test3();
        //sssTest.test4();
        //sssTest.test5();
        //sssTest.test6();
        sssTest.test7();
    }
}
