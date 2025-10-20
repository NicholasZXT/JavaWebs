package pers.zxt.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;


/**
 * 基于Java NIO 实现的 Socket 客户端
 */
public class SocketChannelEchoClient {

    public static void main(String[] args) throws IOException {
        SocketChannelEchoClient client = new SocketChannelEchoClient();
        client.start();
    }

    private static final String HOST = "localhost";
    private static final int PORT = 8080;

    public void start() throws IOException {
        SocketChannel socketChannel = null;
        BufferedReader userInputReader = null;
        try {
            // 1. 打开 SocketChannel
            socketChannel = SocketChannel.open();
            // 2. 设置为非阻塞（虽然对于客户端连接不是必须，但为了统一风格）
            socketChannel.configureBlocking(false);
            // 3. 连接到服务器
            SocketAddress address = new InetSocketAddress(HOST, PORT);
            boolean isConnected = socketChannel.connect(address);

            // 如果 connect 返回 false，说明连接操作正在进行中（非阻塞）
            // 需要调用 finishConnect() 来完成连接
            while (!isConnected && !socketChannel.finishConnect()) {
                // 可以在这里做其他事情，或者简单等待
                Thread.sleep(10);
            }

            System.out.println("Connected to server at " + HOST + ":" + PORT);
            System.out.println("Type messages (type 'quit' to exit):");

            // 必须通过 Buffer 来发送/读取数据
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            userInputReader = new BufferedReader(new InputStreamReader(System.in));
            String userInput;

            while ((userInput = userInputReader.readLine()) != null) {
                if ("quit".equalsIgnoreCase(userInput.trim())) {
                    break;
                }

                // 4. 发送消息到服务器
                byte[] messageBytes = userInput.getBytes("UTF-8");
                buffer.clear();
                buffer.put(messageBytes);
                buffer.flip(); // 切换为读模式，准备写入通道
                socketChannel.write(buffer);

                // 5. 读取服务器的 Echo 响应
                buffer.clear();
                int bytesRead = socketChannel.read(buffer);
                if (bytesRead > 0) {
                    buffer.flip();
                    String echoResponse = new String(buffer.array(), 0, buffer.limit(), "UTF-8").trim();
                    System.out.println("Echo from server: " + echoResponse);
                } else {
                    System.out.println("No response received from server.");
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (socketChannel != null && socketChannel.isOpen()) {
                try {
                    socketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (userInputReader != null) {
                try {
                    userInputReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
