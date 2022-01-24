 # CountDownLatch,CyclicBarrier,Semaphore
  ----------
> CountDownLatch是一个计数器，countDown方法使计数量-1，调用await的方法的线程会阻塞
> 计数量=0时才继续执行（即等待其他线程执行countDown方法）；
> 
> cyclicBarrier和CountDownlatch相反，是让线程执行到某个位置时阻塞，并且计数器+1，
> 直到计数器=n时候所有线程继续执行（即所有线程到达同步点）；
> 
> Semaphore可以用来控制并发数量，可以代表共享资源的数量；
