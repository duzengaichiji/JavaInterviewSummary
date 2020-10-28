package DesignPattern.SingleTon;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * @ClassName SingleTonTest
 * @Author nhx
 * @Date 2020/10/28 20:44
 **/
public class SingleTonTest {
    private CountDownLatch countDownLatch = new CountDownLatch(10);
    @Test
    public void testHungry() throws InterruptedException {
        /**
             * @Author nhx
             * @Description 饿汉模式
             * @Date 20:45 2020/10/28
             * @Param []
             * @return void
             **/
        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                SingleTonHungry singleTonHungry = SingleTonHungry.getInstance();
                //10个都一样
                System.out.println(Thread.currentThread().getName()+" "+singleTonHungry);
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }
        //防止主线程先退出
        countDownLatch.await();
    }

    @Test
    public void testLazy() throws InterruptedException {
        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                SingleTonLazy singleTonLazy = SingleTonLazy.getInstance();
                //可能会有多个对象被创建，可以多测几次
                System.out.println(Thread.currentThread().getName()+" "+singleTonLazy);
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }
        countDownLatch.await();
    }

    @Test
    public void testDoublecheck() throws InterruptedException {
        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                SingleTonDoublecheck singleTonDoublecheck = SingleTonDoublecheck.getInstance();
                System.out.println(Thread.currentThread().getName()+" "+singleTonDoublecheck);
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }
        countDownLatch.await();
    }
}
