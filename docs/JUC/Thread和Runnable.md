# Thread和Runnable

相关代码：JUC.ThreadAndRunnableCase

## 1.联系与区别 ##

> 二者都可以用来创建线程；
>
> Thread类实现了Runnable接口，添加了定义线程需要的其他属性，其业务逻辑由内部的Runnable对象决定；
>
> 实现runnable接口的类需要放进Thread对象中执行；
>
> start方法是Thread的方法,是线程启动的**入口方法**；
>
> Runnable接口的run方法，该方法定义了线程**需要执行的逻辑**；
>
> 同一个Runnable实现对象用于创建多个Thread，其内部的变量会共享，而创建多个Thread的实现对象，其变量是不会共享的；

## 2. start方法和run方法 ##

> 每一次start都会启动一个新的线程，因为start的是Thread的方法，所以必然由Thread对象调用，线程启动后，会自动执行run方法里面定义的逻辑；
单纯的调用run方法不会启动新的线程，而是由当前线程去执行里面的逻辑；

    //该方法被synchronized锁定，因此重复执行会因为线程状态不对而报错
    public synchronized void start() {       
        if (threadStatus != 0) //如果当前线程不处于就绪状态，启动时就报错
            throw new IllegalThreadStateException();
        group.add(this);
        boolean started = false;
        try {
            //启动线程，之后会执行run方法中的业务逻辑
            start0();
            started = true;
        } finally {
            try {
                if (!started) {
                    group.threadStartFailed(this);
                }
            } catch (Throwable ignore) {
            }
        }
    }
    private native void start0();



