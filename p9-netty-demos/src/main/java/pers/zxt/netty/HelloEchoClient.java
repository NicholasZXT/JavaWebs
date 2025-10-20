package pers.zxt.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HelloEchoClient {

    public static void main(String[] args) throws Exception {
        String host = "localhost"; // 默认主机
        int port = 8080;           // 默认端口
        new HelloEchoClient(host, port).start();
    }

    private final String host;
    private final int port;

    public HelloEchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception {
        // 1. 创建一个 EventLoopGroup (客户端通常只需要一个)
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            // 2. 创建 Bootstrap 并配置
            Bootstrap bootstrap = new Bootstrap();
            bootstrap
                // 2.1 配置 EventLoopGroup
                .group(group)
                // 2.2 配置 Channel，指定客户端使用的 Channel 类型
                .channel(NioSocketChannel.class)
                // 2.3 添加 ChannelHandler 来处理 I/O 事件和数据
                // 注意，客户端的 Bootstrap 没有 childHandler() 方法
                .handler(
                    new ChannelInitializer<SocketChannel>() {
                         @Override
                         protected void initChannel(SocketChannel ch) throws Exception {
                             ChannelPipeline pipeline = ch.pipeline();

                             // 添加解码器和编码器
                             pipeline.addLast(new StringDecoder());
                             pipeline.addLast(new StringEncoder());

                             // 添加自定义的客户端处理器
                             pipeline.addLast(new EchoClientHandler());
                         }
                });

            // 3. 连接到服务器
            ChannelFuture f = bootstrap.connect(host, port).sync();
            Channel channel = f.channel(); // 获取连接的 Channel

            System.out.println("Connected to server at " + host + ":" + port);
            System.out.println("Type messages (type 'quit' to exit):");

            // 4. 从控制台读取用户输入并发送
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String userInput;
            while ((userInput = in.readLine()) != null) {
                if ("quit".equalsIgnoreCase(userInput.trim())) {
                    break;
                }
                // 通过 Channel 发送消息
                // Netty 会自动通过 StringEncoder 编码
                channel.writeAndFlush(userInput + "\n"); // 加上换行符，StringDecoder 默认按行分割
            }

            // 5. 等待连接关闭
            f.channel().closeFuture().sync();
        } finally {
            // 6. 优雅关闭
            group.shutdownGracefully();
        }
    }

    /**
     * 客户端处理器，客户端逻辑写在这里。
     */
    public static class EchoClientHandler extends ChannelInboundHandlerAdapter {
        /**
         * 当从服务端接收到消息时调用
         */
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            String echoResponse = (String) msg;
            System.out.println("Echo from server: " + echoResponse.trim()); // trim() 去掉可能的换行符
        }

        /**
         * 当连接建立后调用
         */
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("Connection established with server.");
            super.channelActive(ctx);
        }

        /**
         * 当发生异常时调用
         */
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            System.err.println("Exception in client: " + cause.getMessage());
            cause.printStackTrace();
            ctx.close();
        }
    }

}


