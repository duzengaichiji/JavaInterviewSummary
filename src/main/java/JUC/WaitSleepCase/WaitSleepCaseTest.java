package JUC.WaitSleepCase;

import java.util.concurrent.TimeUnit;

class WaitSleepCaseSource{
    private static int num = 10;

    public void method(){
        synchronized (this){
            System.out.println(Thread.currentThread().getName()+" oh, yes♂sir");
        }
    }

    public void sleepMethod() throws InterruptedException {
        synchronized (this){
            System.out.println(Thread.currentThread().getName()+" get this object");
            TimeUnit.SECONDS.sleep(5);
        }
    }

    public void waitMethod() throws InterruptedException {
        synchronized (this){
            System.out.println(Thread.currentThread().getName()+" give up this object");
            this.wait();
        }
    }

    public void notifyMethod(){
        synchronized (this){
            //System.out.println(Thread.currentThread().getName()+" get this object");
            this.notify();
        }
    }
}

public class WaitSleepCaseTest {
    public static void main(String[] args) {
        WaitSleepCaseSource waitSleepCaseSource = new WaitSleepCaseSource();
        //等待t1线程结束睡眠（5s)之后，t2的方法才会执行，说明对象锁仍然被t1占据；
//        new Thread(() -> {
//            try {
//                waitSleepCaseSource.sleepMethod();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }, "t1").start();
//        new Thread(() -> {
//            waitSleepCaseSource.method();
//                    }, "t2").start();

        //t3调用wait，放弃了对象锁，t4获取之后立即执行，然后t3仍然阻塞，等待着其他线程唤醒它，所以t3的method（）不会调用
        //waitSleepCaseSource.notifyMethod();取消这行的注释，则唤醒t3继续执行，t3的method()会被调用
        new Thread(() -> {
            try {
                waitSleepCaseSource.waitMethod();
                waitSleepCaseSource.method();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t3").start();
        new Thread(() -> {
            waitSleepCaseSource.method();
            waitSleepCaseSource.notifyMethod();
                    }, "t4").start();
    }
}
