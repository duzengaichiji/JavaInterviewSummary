package JUC.FutureUseCase;

public class NormalThreadTest {
    static class KitchenWare{

    }

    static class FoodMaterial{

    }


    static void cooking(KitchenWare kitchenWare,FoodMaterial foodMateria){
        if(kitchenWare==null||foodMateria==null){
            System.out.println("cooking fail");
        }
        else {
            System.out.println("cooking");
        }
    }
    static class ShoppingThread extends Thread{
        private KitchenWare kitchenWare;
        public void run(){
            System.out.println("step1");
            try {
                Thread.sleep(5000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println("step2");
            kitchenWare = new KitchenWare();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        ShoppingThread shoppingThread = new ShoppingThread();
        shoppingThread.start();//启动一个新的线程去执行子任务
        //阻塞等待子任务执行完成，因为父任务需要子任务的执行结果
        //显然这里如果不等待子任务执行完成，则会获取到空的kitchenWare，导致cooking fail
        shoppingThread.join();
        KitchenWare kitchenWare = shoppingThread.kitchenWare;

        FoodMaterial fm = new FoodMaterial();
        Thread.sleep(2000);
        System.out.println("step3");
        cooking(kitchenWare,fm);
        System.out.println("step4");
        long end = System.currentTimeMillis();
        System.out.println(end-start);//~7s
    }
}
