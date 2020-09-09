package JUC.OtherCase;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Printer{

    private int count = 1;
    private Lock lock = new ReentrantLock();
    Condition[] conditions = new Condition[2];

    public Printer(){
        conditions[0] = lock.newCondition();
        conditions[1] = lock.newCondition();
    }

    public void print(){
        while (true) {
            lock.lock();
            int temp = count % 2;
            if (temp == 0) {
                try {
                    System.out.println(Thread.currentThread().getName() + " " + count++);
                    conditions[0].signal();
                    conditions[1].await();
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            } else {
                try {
                    System.out.println(Thread.currentThread().getName() + " " + count++);
                    conditions[1].signal();
                    conditions[0].await();
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}

public class AlternatePrinterByCondition {

    public static void main(String[] args) {
        Printer printer = new Printer();
        new Thread(()->{printer.print();}).start();
        new Thread(()->{printer.print();}).start();
    }
}
