package JUC.FutureUseCase;

import java.util.concurrent.*;

public class FutureThreadTest {
    static class KitchenWare{

    }

    static class FoodMaterial{

    }

    static void cooking(KitchenWare kitchenWare,FoodMaterial foodMaterial){
        if(kitchenWare==null||foodMaterial==null){
            System.out.println("cooking fail");
        }
        else {
            System.out.println("cooking");
        }
    }

    public static void main(String[] args) throws InterruptedException, TimeoutException, ExecutionException {
        long start = System.currentTimeMillis();
        //创建一个callable任务
        Callable<KitchenWare> callable = new Callable<KitchenWare>() {
            @Override
            public KitchenWare call() throws Exception {
                System.out.println("step1");
                try{
                    Thread.sleep(5000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                System.out.println("step2");
                return new KitchenWare();
            }
        };
        //创建FutureTask，可以传入两个参数，一个是callable任务，一个是结果
        FutureTask<KitchenWare> task = new FutureTask<>(callable);
        //启动一个线程去执行任务，并会将结果封装在futuretask中
        new Thread(task).start();

        FoodMaterial fm = new FoodMaterial();
        Thread.sleep(2000);
        System.out.println("step3");
        if(!task.isDone()){//判断子任务是否执行完成
            System.out.println("step2 not done");
        }
        //阻塞的等待子任务执行完成，超过等待时间会报错
        KitchenWare kc = task.get(10, TimeUnit.SECONDS);
        cooking(kc,fm);
        System.out.println("step4");
        long end = System.currentTimeMillis();
        System.out.println(end-start);//~5s
    }
}
