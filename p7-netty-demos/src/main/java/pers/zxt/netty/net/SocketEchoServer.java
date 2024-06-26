package pers.zxt.netty.net;


//import java.net.Inet4Address;
import java.io.InputStream;
import java.net.*;
import java.io.IOException;

public class SocketEchoServer {
    public static void main(String[] args) throws IOException{
        int port = 8088;
        InetAddress address = InetAddress.getByName("localhost");
        System.out.println("address: " + address);

        //ServerSocket serverSocket = new ServerSocket(port);
        // 第二个参数 backlog 设置TCP或UDP连接队列的最大长度，即服务器端同时最多可以打开的连接数量
        try(ServerSocket serverSocket = new ServerSocket(port, 10, address)){
            System.out.println("port: " + serverSocket.getLocalPort());
            serverSocket.setSoTimeout(1000*30);  // 超时单位：毫秒
            while(true){
                Socket client = serverSocket.accept();
                System.out.println("远程主机地址：" + client.getRemoteSocketAddress());
                Worker worker = new Worker(client);
                worker.start();
            }
        } catch (SocketTimeoutException e){
            System.out.println("ServerSocket timeout ...");
            e.printStackTrace();
        } catch (IOException e){
            System.out.println("ServerSocket IOException ...");
            e.printStackTrace();
        }
    }
}

class Worker extends Thread{
    public Socket socket;

    public Worker(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try{
            int len;
            byte[] data = new byte[1024];
            InputStream inputStream = socket.getInputStream();
            while ((len = inputStream.read(data)) != -1){
                System.out.println(new String(data, 0, len));
            }
            socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
