# FuTure接口

相关代码：JUC.FutureUseCase

**1.定义**
--------

> Future的本质是一个异步计算的结果，并且它提供了一些发方法来让调用者检测异步过程是否已经完成，或者取得异步计算的结果。
>
> Future接口是异步处理长时间运行方法的理想选择，可以在等待Future封装的任务完成时执行一些其他事情。
>
> 主要配合callable接口使用，用于计算密集型任务，远程方法调用（远程web服务）。

**2.FutureTask实现细节**
------------------

> FutureTask中定义了如下几种状态

    private volatile int state;//volatile修饰保证对各线程的可见性
    private static final int NEW          = 0;//创建
    private static final int COMPLETING   = 1;//任务执行中
    private static final int NORMAL       = 2;//任务正常结束
    private static final int EXCEPTIONAL  = 3;//任务异常结束
    private static final int CANCELLED    = 4;//任务取消
    private static final int INTERRUPTING = 5;//任务正在中断
    private static final int INTERRUPTED  = 6;//任务中断


----------

> 核心方法get有两种实现，主要用于获取当前任务的状态
>
> 另一种可以指定等待时间，超过等待时间将会抛出异常

    public V get() throws InterruptedException, ExecutionException {
        int s = state;//获取当前状态
        if (s <= COMPLETING)//如果是未完成
            s = awaitDone(false, 0L);//完成该任务
        return report(s);
    }

> report很简单，仅仅是根据当前任务状态，返回执行结果或者报错

    private V report(int s) throws ExecutionException {
            Object x = outcome;
            if (s == NORMAL)
                return (V)x;
            if (s >= CANCELLED)
                throw new CancellationException();
            throw new ExecutionException((Throwable)x);
        }

----------

> awaitDone的具体执行流程

    private int awaitDone(boolean timed, long nanos)
        throws InterruptedException {
        long startTime = 0L;    // Special value 0L means not yet parked
        //这里q其实代表一个等待队列的头节点，这个等待队列正是等待该future完成的线程
        WaitNode q = null;
        boolean queued = false;
        for (;;) {
            int s = state;
            if (s > COMPLETING) {//如果当前任务已经执行完成或者中断，返回当前状态
                if (q != null)
                    q.thread = null;
                return s;
            }
            else if (s == COMPLETING)
                // We may have already promised (via isDone) that we are done
                // so never return empty-handed or throw InterruptedException
                //任务还未完成，让线程就绪，继续执行
                Thread.yield();
            else if (Thread.interrupted()) {
            //如果当前线程被中断，移除等待队列中所有等待该future完成的线程
                removeWaiter(q);
                throw new InterruptedException();
            }
            //到这里只剩下New状态了
            else if (q == null) {
                //如果当前等待队列为空，插入waitNode
                if (timed && nanos <= 0L)
                    return s;
                q = new WaitNode();
            }
            else if (!queued)
                queued = WAITERS.weakCompareAndSet(this, q.next = waiters, q);
            else if (timed) {
                final long parkNanos;
                if (startTime == 0L) { // first time
                    startTime = System.nanoTime();
                    if (startTime == 0L)
                        startTime = 1L;
                    parkNanos = nanos;
                } else {
                    long elapsed = System.nanoTime() - startTime;
                    if (elapsed >= nanos) {
                        removeWaiter(q);
                        return state;
                    }
                    parkNanos = nanos - elapsed;
                }
                // nanoTime may be slow; recheck before parking
                if (state < COMPLETING)
                    LockSupport.parkNanos(this, parkNanos);
            }
            else
                LockSupport.park(this);
        }
    }

> cancel的执行流程

    public boolean cancel(boolean mayInterruptIfRunning) {
        //如果当前状态不是NEW或者CAS设置cancel状态失败的时候，直接返回cancel失败
        if (!(state == NEW && STATE.compareAndSet
              (this, NEW, mayInterruptIfRunning ? INTERRUPTING : CANCELLED)))
            return false;
        try {    // in case call to interrupt throws exception
            //如果状态修改成功，则根据mayInterruptIfRunning变量中断线程
            if (mayInterruptIfRunning) {
                try {
                    Thread t = runner;
                    if (t != null)
                        t.interrupt();
                } finally { // final state
                    STATE.setRelease(this, INTERRUPTED);
                }
            }
        } finally {
            //这个方法会移除等待队列上的所有线程，因为当前任务已经中断了
            finishCompletion();
        }
        return true;
    }

> run方法的执行流程

    public void run() {
        //如果当前任务不是刚刚创建而是在别的状态下，直接返回；
        if (state != NEW ||
            !RUNNER.compareAndSet(this, null, Thread.currentThread()))
            return;
        try {
            //没什么好说的，执行callable任务，设置结果对象
            Callable<V> c = callable;
            if (c != null && state == NEW) {
                V result;
                boolean ran;
                try {
                    result = c.call();
                    ran = true;
                } catch (Throwable ex) {
                    result = null;
                    ran = false;
                    setException(ex);
                }
                if (ran)
                    set(result);
            }
        } finally {
            //runner是volatile修饰的Thread对象
            // runner must be non-null until state is settled to
            // prevent concurrent calls to run()
            runner = null;
            // state must be re-read after nulling runner to prevent
            // leaked interrupts
            int s = state;
            if (s >= INTERRUPTING)
                handlePossibleCancellationInterrupt(s);
        }
    }