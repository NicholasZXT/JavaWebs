package pers.zxt.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Socket客户端练习
 * java.net.Socket 可以用于客户端和服务端。
 * 1. Java的 Socket 编程作为IO的一种，输入输出也被封装为了流，所以需要 java.io 里的流抽象。
 * 2. Java中底层使用 java.net.InetAddress 来作为IP地址的抽象，此类没有公共构造函数，只能通过一些静态方法来获取。
 */
public class SocketEchoClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 8088;
        InetAddress address = InetAddress.getByName("localhost");
        //System.out.println("address: " + address);
        for(int i=0; i < 5; i++){
            Client client = new Client(address, port, "Hello Server, this is client " + i, "Client-" + i);
            client.start();
            Thread.sleep(1000*2);
        }
    }

    /**
     * InetAddress 的使用
     */
    public static void inetAddressUsage() throws IOException {
        // InetAddress 没有公共构造方法，只能通过静态方法来获取对象
        InetAddress address = InetAddress.getByName("localhost");
        System.out.println("address: " + address);
        System.out.println("address: " + address.getHostAddress());
        System.out.println("address: " + address.getHostName());
        System.out.println("address: " + address.isLoopbackAddress());
        System.out.println("address: " + address.isMulticastAddress());
        System.out.println("address: " + address.isAnyLocalAddress());
    }

    /**
     * 使用单个线程来封装客户端
     */
    static class Client extends Thread{
        InetAddress address;
        int port;
        String message;
        String name;

        public Client (InetAddress address, int port, String message, String name){
            this.address = address;
            this.port = port;
            this.message = message;
            this.name = name;
            setName(name);
        }

        @Override
        public void run() {
            // 这里使用 try-with-resource 语句
            try(Socket socket = new Socket(address, port)){
                SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                int len;
                byte[] data = new byte[1024];
                String msg = "[" + ft.format(new Date()) + "]-[" + name + "]-" + message;

                // 向服务端发送消息
                System.out.println(name + " is sending message ...");
                OutputStream out = socket.getOutputStream();
                out.write(msg.getBytes(StandardCharsets.UTF_8));
                //socket.shutdownOutput();

                // 接收服务端返回的消息
                System.out.println(name + " waiting echo message ...");
                InputStream inputStream = socket.getInputStream();
                len = inputStream.read(data);
                if (len != -1){
                    System.out.println(name + " received data: " + new String(data, 0, len));
                }
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                System.out.println(name + " is done.");
            }
        }
    }
}


