package JUC.FutureUseCase;

import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.*;

public class FutureTest {

    private ExecutorService executorService = Executors.newFixedThreadPool(2);
    public Future<Integer> calculate(final Integer input,final Integer time){
        return executorService.submit(()->{
            Thread.sleep(time);
            return input*input;
        });
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        FutureTest futureTest = new FutureTest();

        Future<Integer> future1 = futureTest.calculate(10,1000);
        Future<Integer> future2 = futureTest.calculate(25,3000);

        while (!(future1.isDone()&&future2.isDone())){
            //这里可以填充业务逻辑，需要 future1以future2结果的可以先跳过
            //future1和future2的结果可以异步的获取
            //最简单的举例，future1或者future2的结果需要远程rpc调用，这里面会产生延迟，如果同步会拖慢整个业务流程
            System.out.println(
                    String.format(
                            "future1 is %s and future2 is %s",
                            future1.isDone() ? "done" : "not done",
                            future2.isDone() ? "done" : "not done"
                    )
            );
            Thread.sleep(500);
        }

        Integer result1 = future1.get();
        try {
            Integer result2 = future2.get(1,TimeUnit.SECONDS);//超过等待时间将会报错
            System.out.println(result1);
            System.out.println(result2);
        }
        finally {
            futureTest.executorService.shutdown();
        }

    }
}
