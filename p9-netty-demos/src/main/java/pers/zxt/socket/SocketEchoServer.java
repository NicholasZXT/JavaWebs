package pers.zxt.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;


/**
 * java socket 服务器练习.
 * 1. Java Socket 编程中，服务器端的监听socket需要使用 java.net.ServerSocket，java.net.Socket 只能用于建立连接传输数据。
 */
public class SocketEchoServer {
    public static void main(String[] args) throws IOException{
        int port = 8088;
        InetAddress address = InetAddress.getByName("localhost");
        System.out.println("address: " + address);
        int max_worker = 10;
        int worker_num = 1;

        //ServerSocket serverSocket = new ServerSocket(port);
        // 第二个参数 backlog 设置TCP或UDP连接队列的最大长度，即服务器端同时最多可以打开的连接数量
        try(ServerSocket serverSocket = new ServerSocket(port, max_worker, address)){
            System.out.println(SocketEchoServer.class.getSimpleName() + " is listening on: " + serverSocket.getLocalSocketAddress());
            serverSocket.setSoTimeout(1000*30);  // 超时单位：毫秒
            while(true){
                // accept() 方法会阻塞，等待客户端连接，
                // 客户端建立连接成功后，返回一个和客户端 socket 对应的 Socket 对象。
                Socket client = serverSocket.accept();
                // 使用单独的一个线程处理该客户端的连接
                Worker worker = new Worker(client, "Worker-" + worker_num);
                worker.start();
                worker_num += 1;
                if (worker_num > max_worker){
                    System.out.println(SocketEchoServer.class.getSimpleName() + " STOP listening ...");
                    break;
                }
            }
        } catch (SocketTimeoutException e){
            System.out.println("ServerSocket Timeout ...");
            e.printStackTrace();
        } catch (IOException e){
            System.out.println("ServerSocket IOException ...");
            e.printStackTrace();
        }finally {
            System.out.println(SocketEchoServer.class.getSimpleName() + " STOP listening ...");
        }
    }

    /**
     * Worker线程用于处理单个客户端的连接
     */
    static class Worker extends Thread{
        public Socket socket;
        public String name;

        public Worker(Socket socket, String workerName){
            this.socket = socket;
            this.name = workerName;
            setName(workerName);
        }

        @Override
        public void run() {
            System.out.println(name + " receive connection from client: " + socket.getRemoteSocketAddress());
            try{
                // 从 Socket 对象中获取 输入/输出流 对象
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();

                int len;
                byte[] data = new byte[1024];
                // 读取客户端的消息
                //while ((len = inputStream.read(data)) != -1) {
                //    System.out.println(name + " received data: " + new String(data, 0, len));
                //}
                len = inputStream.read(data);
                if (len != -1){
                    System.out.println(name + " received data: " + new String(data, 0, len));
                }
                // 返回给客户端
                System.out.println(name + " send back data to client: " + socket.getRemoteSocketAddress());
                String echo_data = new String(data, 0, len) + ". Echo from " + name;

                // 调用 write() + flush() 方法，写入刷新返回数据
                outputStream.write(echo_data.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();

                // 关闭连接
                socket.close();
                System.out.println(name + " closed connection with client：" + socket.getRemoteSocketAddress());
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                System.out.println(name + " is done.");
            }

        }
    }

}


