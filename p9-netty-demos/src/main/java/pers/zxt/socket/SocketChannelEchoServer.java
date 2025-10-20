package pers.zxt.socket;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;


/**
 * 基于Java NIO 实现的 Socket 服务器
 */
public class SocketChannelEchoServer {

    public static void main(String[] args) {
        SocketChannelEchoServer server = new SocketChannelEchoServer();
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final int PORT = 8080;
    private ServerSocketChannel serverChannel;
    private Selector selector;

    public void start() throws IOException {
        // 1. 打开 ServerSocketChannel 并配置为非阻塞
        serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false); // 必须设置为非阻塞

        // 2. 绑定到指定端口
        InetSocketAddress address = new InetSocketAddress(PORT);
        serverChannel.bind(address);
        System.out.println("Echo Server started on port " + PORT);

        // 3. 打开 Selector
        selector = Selector.open();

        // 4. 将 ServerSocketChannel 注册到 Selector 上，监听 OP_ACCEPT 事件（接受新连接）
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        // 5. 事件循环
        while (true) {
            try {
                // 阻塞等待，直到有至少一个通道准备好进行 I/O 操作
                // 参数是超时时间（毫秒），0 表示无限等待
                int readyChannels = selector.select();
                if (readyChannels == 0) continue; // 没有就绪的通道，继续循环

                // 获取就绪的 SelectionKey 集合
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

                // 遍历就绪的键
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();

                    // 处理不同的事件类型
                    try {
                        if (key.isAcceptable()) {
                            // 接受新的客户端连接
                            handleAccept(key);
                        } else if (key.isReadable()) {
                            // 读取客户端发送的数据
                            handleRead(key);
                        } else if (key.isWritable()) {
                            // 向客户端写回数据（Echo）
                            handleWrite(key);
                        }
                    } catch (IOException e) {
                        System.err.println("Error handling key: " + e.getMessage());
                        // 发生错误，关闭通道并取消键
                        key.cancel();
                        closeChannel(key);
                    }

                    // 必须从集合中移除已处理的 key，否则下次 select 还会返回它 --------------- KEY
                    keyIterator.remove();
                }
            } catch (IOException e) {
                System.err.println("Selector error: " + e.getMessage());
                break;
            }
        }
    }

    /**
     * 处理 ACCEPT 事件：接受新连接
     */
    private void handleAccept(SelectionKey key) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel clientChannel = serverChannel.accept(); // 接受连接
        if (clientChannel != null) {
            clientChannel.configureBlocking(false); // 设置为非阻塞
            System.out.println("New client connected: " + clientChannel.getRemoteAddress());

            // 将新的客户端通道注册到 Selector，监听 READ 事件
            // 可以在这里附加一个缓冲区对象，用于存储该连接的读取数据
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            clientChannel.register(selector, SelectionKey.OP_READ, buffer);
        }
    }

    /**
     * 处理 READ 事件：读取客户端数据
     */
    private void handleRead(SelectionKey key) throws IOException {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = (ByteBuffer) key.attachment(); // 获取之前附加的缓冲区

        buffer.clear(); // 清空缓冲区，准备接收新数据
        int bytesRead = clientChannel.read(buffer);

        if (bytesRead == -1) {
            // 客户端正常关闭连接
            System.out.println("Client disconnected: " + clientChannel.getRemoteAddress());
            closeChannel(key);
            return;
        }

        if (bytesRead > 0) {
            // 有数据可读
            buffer.flip(); // 切换为读模式
            String receivedMessage = new String(buffer.array(), 0, buffer.limit(), "UTF-8").trim();
            System.out.println("Received from client: " + receivedMessage);

            // 准备 Echo 回去，将缓冲区数据翻转后直接用于写操作
            buffer.flip(); // 现在 buffer 包含要发送的数据

            // 将通道的关注事件改为 OP_WRITE，并重新注册（或修改）
            // 注意：这里我们先取消之前的 READ 注册，再注册 WRITE
            // 实际应用中可能需要更复杂的逻辑来避免频繁注册/取消
            key.interestOps(SelectionKey.OP_WRITE);
            // 或者使用 key.attach(buffer) 保持引用，然后 key.interestOps(SelectionKey.OP_WRITE);
        }
        // 如果 bytesRead == 0，表示没有数据可读，但连接可能仍然有效，不做任何操作，等待下一次 read
    }

    /**
     * 处理 WRITE 事件：向客户端发送数据
     */
    private void handleWrite(SelectionKey key) throws IOException {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = (ByteBuffer) key.attachment();

        // 将缓冲区中的数据写入通道
        int bytesWritten = clientChannel.write(buffer);

        if (!buffer.hasRemaining()) {
            // 数据全部写完
            System.out.println("Echoed message back to client.");
            // 写完后，重新关注 READ 事件，等待客户端的下一条消息
            key.interestOps(SelectionKey.OP_READ);
        } else {
            // 数据没有完全写出，通常是因为底层网络缓冲区满了
            // buffer 中剩余的数据会在下一次 WRITE 就绪时继续发送
            // 我们保持 OP_WRITE 关注，直到数据全部写出
            // （在这个简单例子中，因为数据量小，通常一次就能写完）
        }
    }

    /**
     * 安全地关闭通道和取消键
     */
    private void closeChannel(SelectionKey key) {
        Channel channel = key.channel();
        try {
            if (channel instanceof SocketChannel) {
                ((SocketChannel) channel).close();
            }
        } catch (IOException e) {
            System.err.println("Error closing channel: " + e.getMessage());
        } finally {
            key.cancel(); // 取消 SelectionKey
        }
    }

}
