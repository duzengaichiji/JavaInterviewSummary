# String,StringBuffer,StringBuilder

相关代码：javaBasic.StringStringbuilderStringbuffer

 - 1. 三者联系与区别

> String是一个不可变的类型（字符串常量），即String的底层是一个final修饰的byte/char数组，因此，实际上每一次对String类型进行字符串操作（拼接，截取，相加 等）都会产生新的String对象；
> 
> 而StringBuilder是一个可变类型（字符串变量），可以直接改变其对应的字符数组，用该对象做字符串拼接是不会产生新的对象的，不会造成空间浪费；
> 
> StringBuffer与StringBuilder的不同之处在于，它是线程安全的，其内部的方法都是由synchronized锁定的；
> 
> 另外，String可以赋予null，而其他二者不可以；
> 
> 事实上，String用操作符+完成字符串拼接时候，是创建了两个StrinBuilder对象，然后进行拼接，并最后使用toString方法完成的，实际上多创建了对象；
而使用StringBuilder的append方法进行拼接效率要高的多；

 - 2.String与字符串常量池

> 直接声明的字面量（比如String sb = "Str"）是存放在字符串常量池中的，
另一种将字符串放入常量池中的方法是显示的调用intern方法；
>
> 由于字符串常量池实际上是一个固定大小的HashTable，**所以其中不会有相同的字符串**；
> 

 - 3.相关题目举例

> s1和s2的值相等是显然的；
>
> s1==s2返回True是因为两者都是字面量声明的，所以同时指向了字符串常量池中的"abc"字符串；

    String s1 = "a"+"b"+"c";
    String s2 = "abc";
    System.out.println(s1==s2);//True
    System.out.println(s1.equals(s2));//True

> s1,s2,s三个字符串的定义使得常量池中存在"javaEE","hadoop","javaEEhadoop"三个字符串；
>
> 由于s3，s4是由一个变量和一个字面量拼接而成的，实际拼接过程中形成了新的String对象，并存放在**堆**中，所以s!=s3,s3!=s4，即实际上堆中应有两个值为"javaEEhadoop"的字符串，一个由s3指向，一个由s4指向；

    String s1 = "javaEE";
    String s2 = "hadoop";
    String s = "javaEEhadoop";
    String s3 = s1+"hadoop";
    String s4 = "javaEE"+s2;
    System.out.println(s==s3);//false
    System.out.println(s3==s4);//false

> 与上一题不同的地方在于，这里的s5是由s4调用intern后返回的，所以指向了常量池中的字符串"javaEEhadoop",即与s指向的是相同的对象；
>
> **intern方法不会改变调用者的（这里是s4)引用，但是会返回新的引用;**
>
> 因此，这里s4指向的仍然是堆里面的字符串对象，所以和s5当然不相等;

    String s1 = "javaEE";
    String s2 = "hadoop";
    String s = "javaEEhadoop";
    String s3 = s1+"hadoop";
    String s4 = "javaEE"+s2;
    String s5 = s4.intern();
    System.out.println(s==s5);//true
    System.out.println(s3==s5);//False
    System.out.println(s3==s4);//False
    System.out.println(s4==s5);//False

> 关于intern方法；
>
> 如果常量池中已经有了该字符串，则不会放入，并会返回当前常量池中这个字符串对象的引用；
>
> 如果当前常量池中没有，则是复制当前**调用者的对象引用**，将该引用放入常量池，并返回该引用；
>
> 总之，intern方法并不会改变其调用者的引用；
> 
> 对比两段代码的内存消耗可知，在大量使用相同字符串的情况下，直接使用常量池中的对象比用字符串拼接不停创建新的对象要更省资源；
>
> 同理，在大量创建暂时的字符串对象时并需要不断改变的情况下，使用Stringbuilder也比不停进行字符串拼接来的高效（实际上每次进行字符串"+"号拼接，用的正是StringBuilder来进行，最后再toString转成String对象返回）；

    String s = "a";
    for (int i = 0; i < 10086; i++) {
        String s1 = s+"b";
    }
    System.out.println((runtime.totalMemory()-runtime.freeMemory())+"B");//4028656B

    String s = "a";
    String t = s+"b";
    for (int i=0;i<10086;i++){
        String s1 = t.intern();
    }
    System.out.println((runtime.totalMemory()-runtime.freeMemory())+"B");//3145728B

> 另一个问题是关于String s = new String("a")会创建几个对象；
>
> 答案是两个，一个是常量池中的"a"，另外，由于显示的调用了String类的构造方法，所以会在堆中创建字符串"a"；
> 
> 该段代码中，由于s指向堆中的"s1"，而s1指向常量池中的"s1"，所以不同；
>
> s3调用intern后常量池中原本没有"s1s2"字符串，所以常量池中**会存放s3的引用的备份**，**即常量池中有一个指向堆中"s1s2"对象的引用，该引用和s3是一样的**，因此，以字面量方式声明s4时，会指向常量池中那个引用，就和s3一样了；

    String s = new String("s1");
    s.intern();
    String s1 = "s1";
    System.out.println(s==s1);//false
    String s3 = new String("s1")+new String("s2");
    s3.intern();
    String s4 = "s1s2";
    System.out.println(s3==s4);//true处输入代码