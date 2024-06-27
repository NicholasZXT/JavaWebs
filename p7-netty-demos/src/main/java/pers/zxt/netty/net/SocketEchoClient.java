package pers.zxt.netty.net;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.text.SimpleDateFormat;

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
}

class Client extends Thread{
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
        }
        System.out.println(name + " is done.");
    }
}
