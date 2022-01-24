 # 线程池
 
----------
- 为什么要用线程池
>
> 1.手动创建线程，风险大，难控制，占据资源可能多，也没有自动回收和释放；
> 
> 2.过多的线程会导致频繁进行上下文且切换，这也有时间消耗；
> 

> 因此使用线程池将线程资源统一观猎；

- ThreadPoolExecutor的构造参数
> 
> 
    public ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue,
                              ThreadFactory threadFactory,
                              RejectedExecutionHandler handler)
>
> corePoolSize: 核心线程数量；
> 
> maximumPoolSize：最大线程数量；
> 
> keepAliveTime：生存时间；
> 
> TimeUnit：生产时间的单位;
> 
> blockingQueue：任务队列
> 
> ThreadFactory：线程工厂方法；
> 
> RejectedExecutionHandler：拒绝策略；
> 
> 
 
> 用一个银行柜台的例子可以很好的解释这些参数以及线程池的原理：
> 
> 通常，银行有几个（corePoolSize)窗口是常驻的，这些窗口处理着客户的需要，客户需要在窗口前排队（blockingQueue），
> 
> 而特别忙的时候（请求很多），银行会临时添加数个窗口（最多使得窗口数量达到maximumPoolSize），多出的窗口
> 
> 会使任务处理速度加快；而此时如果仍然忙不过来，导致等待的人超过了队列的最大长度，就会按照一定规则，
> 
> 拒绝一些客户的请求（RejectedExecutionHandler）；等请求数量渐渐减少，表明加班时间结束，那几个非常驻窗口，
> 
> 在空闲一段时间后会自行解散（keepAliveTime）；
> 
- blockingQueue
>
> 一般来说分为四种：
> 
> 1.synchronousQueue：没有容量，每次进行插入就会阻塞，直到弹出操作被执行；反之，弹出也是一样，要阻塞等待插入；
> 
> 2.ArrayBlockingQueu：有界的阻塞队列；
> 
> 3.linkedBlockingQueue: 无界队列，如果无限增长，则会造成资源耗尽；
> 
> 4.priorityBlockingQueue: 优先级队列，可以设置任务优先级，实现是用堆；
> 
- RejectedExecutionHandler
>
> 1.AbortPolicy：该策略会**直接抛出异常**， 阻止系统运行
> 
> 2.CallerRunsPolicy：将超出界限的任务**放在调用者线程运行**；
> 
> 3.DiscardOledestPolicy：丢弃任务队列中最老的一个任务，并尝试再次提交；
> 
> 4.DiacardPolicy：默默丢弃无法处理的任务，没有报警等行为（该策略适用于允许丢失任务的场合）
> 
> 