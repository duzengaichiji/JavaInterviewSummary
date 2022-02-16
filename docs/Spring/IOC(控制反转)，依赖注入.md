# 控制反转，依赖注入
----------
> 事实上，控制反转不是一种具体方法，而是描述一种现象，
> 在spring里面指的就是，原本由编码人员手动编码控制的对象创建，对象依赖管理等过程，
> 变成由spring框架去执行，而编码人员只需要关注业务逻辑本身；
>
----------
> 
> 而依赖注入概括为一句话就是：
> 
> 不通过new（）的方式在**类内部**创建依赖类对象，而是将依赖类对象在外部创建好之后，通过构造函数，
> 函数参数等方式传递（注入）给类使用；
> 
> spring解决循环依赖的过程；
> 
> 通常描述的是属性的循环依赖（属性中互相包含对方），两个或以上的bean互相持有对方，形成一个环；
> 
> 首先，spring中单例 bean对象的生命周期，大致分为4步：
> 
> 1. 执行createBeanInstance方法（会调用bean对象的构造方法/工厂方法）对对象进行实例化，
> 得到的是刚完成实例化，还未**进行初始化**（填充成员变量）的单例对象；
> 
> 2. 执行populateBean方法，对对象进行赋值（autowired的依赖注入，及属性set方法都在这一步执行）
> 
> 3. 执行initialBean方法进行最后的初始化，比如执行postConstruct注解的init方法；
> 
> 4. 对象的销毁（单例对象是随着容器的销毁而销毁）
> 
> 引入spring的三级缓存概念：singletonObjects,earlySingleObjects,singletonFactories；
> 
> 之所以能解决循环依赖，就是因为这个缓存；
> 
> 以teacher和student两个对象为例，两个对象相互包含；
> 
> 1 teacher先被加载，createBeanInstance之后，先通过addSingletonFactory方法
> 加入到三级缓存singletonFactories中；
> 
> 2 执行populatebean方法， 发现需要注入student对象，于是去执行getbean方法；
> 
> 3 getbean -> dogetbean，发现容器中没有student对象，于是返回去执行studet对象的加载过程
> 
> 4 执行到student的populatebean时，发现需要注入teacher对象，在缓存中找到了teacher对象，
> 将它的引用注入到自己的属性中；
> 
> 5 完成student的初始化过程，并加入singletonObjects缓存池中；
> 
> 6 回溯到teacher的populateBean过程，从singletonObjects中取得student对象，完成初始化；
> 
> 
> 然而，spring只能解决属性依赖，无法解决构造器依赖（即在构造方法中就相互依赖的两个对象）；
> 其原因就是无法先完成创建对象并放入缓存中的这个过程；