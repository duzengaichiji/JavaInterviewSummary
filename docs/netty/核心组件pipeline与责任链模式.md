# 核心组件pipeline与责任链模式

> 有关channelPipeline channelHandler channelHandlerContext的关系

## 1.三者的关系 ##

> 每当创建一个SserverSocket就创建了一个新的连接，就会对应一个Socket，对应的连接对方就是目标客户端；
> 而每创建一个新的Socket就会分配一个全新的channelPipeline；
> 每一个channelPipeline内部都会包含多个channelHandlerContext，它们连起来构成了**双向链表**，这些context用来包装我们调用addLast的时候添加的channelhandler；


----------
## 2.channelPipeline接口 ##

    public interface ChannelPipeline
        extends ChannelInboundInvoker, ChannelOutboundInvoker, Iterable<Entry<String, ChannelHandler>> {
      //对内部的链表节点（每个上面都封装了handler）进行插入和删除
      ChannelPipeline addFirst(String name, ChannelHandler handler);
      ChannelPipeline addAfter(String baseName, String name, ChannelHandler handler);
      ChannelPipeline addBefore(String baseName, String name, ChannelHandler handler);
      ChannelPipeline addLast(ChannelHandler... handlers);
      //返回channel即socket
      Channel channel();
      //返回某个handler对应的context
      ChannelHandlerContext context(ChannelHandler handler);
      ChannelPipeline remove(ChannelHandler handler);
      ChannelPipeline replace(ChannelHandler oldHandler, String newName, ChannelHandler newHandler);
    }

> 该接口继承了inBound，outBound，iterable接口，表明它能够调用数据出战和入站相关的方法，也能遍历其 内部的链表。


----------
## 3.channelHandler接口 ##

    public interface ChannelHandler {
        // 当把 ChannelHandler 添加到 pipeline 时被调用
        void handlerAdded(ChannelHandlerContext ctx) throws Exception;
        // 当从 pipeline 中移除时调用
        void handlerRemoved(ChannelHandlerContext ctx) throws Exception;
        // 当处理过程中在 pipeline 发生异常时调用
        @Deprecated
        void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception;
}

> 简单来说，channelHandler又被下层接口分为inboundHandler和outboundHandler，分别指的是入站handler和出站handler，每个handler会通过调用**channelHandlerContext.fireChannelRead**方法转发给其下一个handler。
> 
> 通常一个pipeline中有很多个handler，通常一个基本的handler至少包含如下几个处理程序：
1.协议解码器--将二进制数据转换为java对象。
2.协议编码器--将java对象转换为二进制对象。
3.业务逻辑处理器--执行业务逻辑，数据处理。
> 
> channelInboundHandler具体实现，主要用于入站（读）数据处理

    public interface ChannelInboundHandler extends ChannelHandler {
        //当前channel注册到eventloop上时调用
        void channelRegistered(ChannelHandlerContext ctx) throws Exception;
        //当前channel从eventloop取消时调用
        void channelUnregistered(ChannelHandlerContext ctx) throws Exception;
        //当前通道被激活（连接）时调用
        void channelActive(ChannelHandlerContext ctx) throws Exception;
        //当前通道不活跃时调用（生命周期结束）
        void channelInactive(ChannelHandlerContext ctx) throws Exception;
        //当前channel触发读取事件时调用
        void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception;
        //当前channel读取数据完成时调用
        void channelReadComplete(ChannelHandlerContext ctx) throws Exception;
        //用户事件触发时候调用？（超时控制)
        void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception;
        //channel的写状态变化时触发
        void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception;
        //发生错误时调用
        void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception;
}

> 与之对应的outboundHandler，主要用于处理出站（写）数据；

    public interface ChannelOutboundHandler extends ChannelHandler {
        //bind操作前触发
        void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception;
        //connect操作前触发
        void connect(
                ChannelHandlerContext ctx, SocketAddress remoteAddress,
                SocketAddress localAddress, ChannelPromise promise) throws Exception;
        //断开连接前触发
        void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception;
        ////。。。
        void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception;
        void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception;
        void read(ChannelHandlerContext ctx) throws Exception;
        void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception;
        void flush(ChannelHandlerContext ctx) throws Exception;
}


----------
## 4.channelHandlerContext接口 ##

> 前文中提到，每个handler会对应一个context，在channelHandler的具体实现类中，如果不做处理，默认会调用context的对应方法，比如read。

    public class ChannelDuplexHandler extends ChannelInboundHandlerAdapter implements ChannelOutboundHandler {

        public void bind(ChannelHandlerContext ctx, SocketAddress localAddress,
                         ChannelPromise promise) throws Exception {
            //调用context的对应方法
            ctx.bind(localAddress, promise);
        }
        public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress,
                            SocketAddress localAddress, ChannelPromise promise) throws Exception {
            ctx.connect(remoteAddress, localAddress, promise);
    }
    
    

> channelHanlderContext继承了channelInboundInvoker和channelOutboundInvoker；
看起来像动态代理模式，在出站和入站方法对应的位置拦截并进行增强；
另外，channelHandlerContext定义了一些用于获取其对应的channel，executor，handler。。等属性的方法；
可以认为，context就是封装了handler的一切的代理类；


----------
## 5.channelPipeline是如何调度handler的 ##

> 当一个请求进来时，会调用pipeline的相关方法；
入站事件会沿着"fire"方法流动(fireChannelActive，。。。（fire系列是channelHandlerContext的方法)），让后续方法接着处理；
对于入站事件，是从head开始向后流动，而出站事件是从tail开始向前流动，**所以要注意在pipeline中添加handler时的顺序**；

    private AbstractChannelHandlerContext findContextInbound() {
            AbstractChannelHandlerContext ctx = this;
            do {
                ctx = ctx.next;
            } while(!ctx.inbound);
            return ctx;
        }

    public ChannelHandlerContext fireChannelActive() {
        //findContextInbound会查找到链表中的下一个inboundHandler
        invokeChannelActive(this.findContextInbound());
        return this;
    }

    static void invokeChannelActive(final AbstractChannelHandlerContext next) {
        EventExecutor executor = next.executor();
        //调用invokeChannelActive(在下面）
        if (executor.inEventLoop()) {
            next.invokeChannelActive();
        } else {
            executor.execute(new Runnable() {
                public void run() {
                    next.invokeChannelActive();
                }
            });
        }

    }

    private void invokeChannelActive() {
        //注意，此时的this在上一层方法调用之后，已经是当前节点的下一个节点（就是上一层方法中的next），所以该方法内执行的是next节点的channelActive逻辑，即顺着pipeline流动；
        if (this.invokeHandler()) {
            try {
                ((ChannelInboundHandler)this.handler()).channelActive(this);
            } catch (Throwable var2) {
                this.notifyHandlerException(var2);
            }
        } else {
            this.fireChannelActive();
        }

    }

