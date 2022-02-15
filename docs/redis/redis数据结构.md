# redis
----------

- 特性：
> 1.存放在内存，纯内存数据库
> 
> 2.用epoll进行非阻塞IO，保证IO的多路复用；
> 
> 3.单线程处理，避免了线程的频繁切换；
> 
- 持久化
> 
> 
- 数据类型
>
> 1.String
> 
> String类型包含三种内部编码格式：
> 
> int：8个字节的长整形
> 
> Embstr: <= 39个字节的字符串
> 
> raw: > 39个字节的字符串
> 
> 一个key对应一个value，一个redis String value最多能有512M的大小；
> 
> 通常用来作简单的kv对，比如：微博粉丝数量，快速计数（redis有针对int型进行快速+1，-1的api），
> 共享session（将用户的session串直接存在redis中，避免每次都进行IO去获取）等
> 

> 2.Hash类型
>
> ziplist压缩列表：当哈希类型元素个数和值小于配置值时会使用ziplist进行数据压缩；
> 
> hashTable：当超出ziplist的限制条件时会恢复为hashtable类型，以保证读写的复杂度；
![cdn](../../imgs/redis_data_1.png "redis_data")
> 
> 该数据结构特别使用用来存放自定义的数据对象；

>
> 3.List
>
> 同样分为zipList,LinkedList；
> 
> 可以用来实现 消息队列， 用rpush+lpop的命令组合，就能将linkedlist类型作为消息队列使用；
![cdn](../../imgs/redis_data_3.png "redis_data")

>
> 4.set
> 
> 类比java里面hashMap和hashSet的关系，这个也可以对比上面的hashTable；

>
> 5.Zset
> 
> 比较值得一提的是这个sorted set类型；
> 
> 它是一种自带排序的set类型，对其传入key时要同时传入score值，redis会依据这个score值对key进行排序；
> 
> 其内部结构是 跳跃表：
> 
> 它是一种多层的链表结构；
![cdn](../../imgs/jump_table.png "jump_table")
> 
> 如图所示，如果查找7，会先查红色那条线（即间隔为2的线），查到11时确定范围在11这个节点的前方；
> 
> 此时缩小查找范围，在间隔为1的链表中查；
> 
> 即整体将链表分为不同间隔，通过大的间隔缩小查找范围，而后通过小的间隔确定查找对象；
> 
> 这种类似二分的查找方式，能将复杂度降低到O(logn)；
> 
![cdn](../../imgs/jump_table_insert.png "jump_table")
>
> 跳表的插入过程比较复杂，先通过上面的查找过程，确定插入位置，然后逐层判断，是否要在该层对应间隔的链表下插入这个节点；
> 通常难以保证每个节点都在每一层链表有连接；
>