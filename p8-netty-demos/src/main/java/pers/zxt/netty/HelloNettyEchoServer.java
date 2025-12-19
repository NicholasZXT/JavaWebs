package pers.zxt.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
//import io.netty.channel.*;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;         // 这是接口
import io.netty.channel.DefaultChannelPipeline;  // 这是默认Pipeline实现类
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
//import io.netty.util.concurrent.Future;
//import io.netty.util.concurrent.Promise;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;   // 接口，它有如下默认实现类
import io.netty.buffer.DefaultByteBufHolder;
import io.netty.buffer.ByteBufAllocator;   // 接口，它有如下两个实现类
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.buffer.Unpooled;      // 工具类
import io.netty.buffer.ByteBufUtil;   // 工具类

public class HelloNettyEchoServer {

    public static void main(String[] args) throws Exception {
        int port = 8080; // 默认端口
        new HelloNettyEchoServer(port).start();
    }

    private final int port;

    public HelloNettyEchoServer(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        // 1. 创建两个 EventLoopGroup
        // bossGroup: 用于接收客户端连接
        EventLoopGroup bossGroup = new NioEventLoopGroup(1); // 通常一个线程就够了
        // workerGroup: 用于处理已连接的 SocketChannel 的 I/O 操作
        EventLoopGroup workerGroup = new NioEventLoopGroup(); // 使用默认线程数 (通常是 CPU 核心数 * 2)

        try {
            // 2. 创建 ServerBootstrap 并配置
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap
                // 2.1 配置 EventLoopGroup
                .group(bossGroup, workerGroup) // 设置两个 EventLoopGroup
                // 也可以只使用一个 EventLoopGroup
                //.group(bossGroup)
                // 2.2 配置 Channel，指定 Channel 类型
                .channel(NioServerSocketChannel.class)
                // 2.3 添加 ChannelHandler，配置服务器自身（即 ServerSocketChannel）的 ChannelPipeline。
                // 它处理的是与接受新连接相关的事件，客户端的 Bootstrap 没有此方法。
                .handler(
                    new ChannelInitializer<ServerSocketChannel>() {
                        @Override
                        protected void initChannel(ServerSocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                             // 这里添加的 Handler 只对 ServerSocketChannel 生效
                             // 用于处理 ACCEPT 事件
                             //p.addLast(new LoggingHandler(LogLevel.INFO)); // 记录连接日志
                             // ... 可以在这里添加其他只关心连接事件的处理器
                        }
                    }
                )
                // 2.4 添加 ChannelHandler 来处理 I/O 事件和数据。
                // 配置的是每一个新创建的客户端连接（即 SocketChannel）的 ChannelPipeline。
                // 它处理的是与已建立连接的读写通信相关的事件。
                .childHandler(
                 new ChannelInitializer<SocketChannel>() { // 为每个新连接的 SocketChannel 初始化 Pipeline
                     @Override
                     protected void initChannel(SocketChannel ch) throws Exception {
                         // 此处 SocketChannel 不是 java.nio.channels.SocketChannel，而是 io.netty.channel.socket.SocketChannel
                         ChannelPipeline pipeline = ch.pipeline();

                         // 这里添加的 Handler 对每一个客户端连接都生效

                         // 添加解码器：将接收到的字节流解码为字符串
                         pipeline.addLast(new StringDecoder());
                         // 添加编码器：将要发送的字符串编码为字节流
                         pipeline.addLast(new StringEncoder());

                         // 添加自定义的业务处理器 - Echo 逻辑在这里
                         pipeline.addLast(new EchoServerHandler());
                     }
                })
                // 可选设置：可以设置一些 TCP 参数
                .option(ChannelOption.SO_BACKLOG, 128) // 连接请求队列的最大长度
                .childOption(ChannelOption.SO_KEEPALIVE, true); // 启用 TCP Keep-Alive

            // 3. 绑定端口并开始接收连接
            ChannelFuture f = serverBootstrap.bind(port).sync();

            System.out.println("Echo Server started on port " + port);

            // 4. 等待服务器 socket 关闭 (这个方法是阻塞的)
            // 也就是说，应用程序会一直运行，直到服务器被关闭
            f.channel().closeFuture().sync();
        } finally {
            // 5. 优雅地关闭 EventLoopGroup，释放资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    /**
     * 自定义的 ChannelInboundHandler 处理消息，业务逻辑就写在这里。
     * - 处理响应消息时需要实现 ChannelInboundHandler 接口，一般继承 ChannelInboundHandlerAdapter 实现类并重写其中的方法
     * - 处理请求消息时需要实现 ChannelOutboundHandler 接口，一般继承 ChannelOutboundHandlerAdapter 实现类并重写其中的方法
     */
    public static class EchoServerHandler extends ChannelInboundHandlerAdapter {
        /**
         * 当从客户端读取到数据时调用
         */
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            // msg 的类型取决于 Pipeline 中前一个 Handler 的输出
            // 因为我们添加了 StringDecoder，所以 msg 就是 String
            String receivedMessage = (String) msg;
            System.out.println("Received from client: " + receivedMessage);

            // Echo 回去 - 直接写回相同的消息
            // Netty 会自动通过 StringEncoder 编码并发送
            ctx.writeAndFlush(receivedMessage);
            // 注意：这里我们没有调用 ctx.close()，所以连接保持打开，等待下一条消息
        }

        /**
         * 当发生异常时调用
         */
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            System.err.println("Exception in server: " + cause.getMessage());
            cause.printStackTrace();
            // 发生异常时，通常关闭连接
            ctx.close();
        }

        /**
         * 当客户端断开连接时调用
         */
        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("Client disconnected: " + ctx.channel().remoteAddress());
            super.channelInactive(ctx);
        }
    }

}
