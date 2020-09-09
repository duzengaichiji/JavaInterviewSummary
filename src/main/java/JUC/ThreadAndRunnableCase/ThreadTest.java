package JUC.ThreadAndRunnableCase;

class MyThread extends Thread{
    private String name;

    public MyThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName()+"+"+name+" 运行了 "+i);
        }
    }
}

class MyRunnable implements Runnable{
    private int ticket = 10;

    @Override
    public void run() {
        for (int i = 0; i < 50; i++) {
            if(ticket>0){
                System.out.println(Thread.currentThread().getName() + "+sell ticket:" + ticket--);
            }
        }
    }
}

class MyTicketThread extends Thread{
    private int ticket = 10;

    @Override
    public void run() {
        for (int i = 0; i < 50; i++) {
            if (ticket > 0) {
                System.out.println(Thread.currentThread().getName() + "+sell ticket:" + ticket--);
            }
        }
    }
}

public class ThreadTest{
    public void testThread(){
        MyThread mt_A = new MyThread("threadA");
        MyThread mt_B = new MyThread("threadB");
        System.out.println(Thread.currentThread().getName());
        mt_A.start();
        mt_B.start();
        //mt_B.run();//调用run方法相当于调用实例方法，不会启动新线程，执行者是main线程
    }

    public void testRunnable(){
        MyRunnable my = new MyRunnable();
        //启动三个新的线程，其逻辑都由my对象定义
        //三个线程会共享my对象的ticket变量
        new Thread(my).start();
        new Thread(my).start();
        new Thread(my).start();
    }

    public void testThread2(){
        //三个线程将各自拥有一份ticket变量，相互独立
        new MyTicketThread().start();
        new MyTicketThread().start();
        new MyTicketThread().start();
    }

    public static void main(String[] args) {
        ThreadTest tt = new ThreadTest();
        //tt.testThread();
        //tt.testRunnable();
        tt.testThread2();
    }
}
