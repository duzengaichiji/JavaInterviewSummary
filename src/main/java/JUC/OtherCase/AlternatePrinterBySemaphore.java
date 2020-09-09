package JUC.OtherCase;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

class Printer1{
    private static Semaphore odd;
    private static Semaphore even;

    private int num1 = 1;
    private int num2 = 2;

    static {
        odd = new Semaphore(1);
        even = new Semaphore(1);
    }

    public void printOdd() throws InterruptedException {
        while (true) {
            odd.acquire();
            System.out.println(Thread.currentThread().getName() + " " + num1);
            num1 += 2;
            even.release();
            TimeUnit.SECONDS.sleep(1);
        }
    }

    public void printEven() throws InterruptedException {
        while (true) {
            even.acquire();
            System.out.println(Thread.currentThread().getName() + " " + num2);
            num2 += 2;
            odd.release();
            TimeUnit.SECONDS.sleep(1);
        }
    }
}

public class AlternatePrinterBySemaphore {
    public static void main(String[] args) {
        Printer1 printer1 = new Printer1();
        new Thread(()->{
            try {
                printer1.printOdd();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()->{
            try {
                printer1.printEven();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
