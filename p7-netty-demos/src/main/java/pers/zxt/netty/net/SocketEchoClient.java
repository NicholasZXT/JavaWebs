package pers.zxt.netty.net;

import java.net.InetAddress;
import java.net.Socket;
import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class SocketEchoClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 8088;
        InetAddress address = InetAddress.getByName("localhost");
        System.out.println("address: " + address);
        for(int i=0; i < 5; i++){
            Client client = new Client(address, port, "Hello Server, this is client " + i);
            client.start();
            Thread.sleep(1000*2);
        }
    }
}

class Client extends Thread{
    InetAddress address;
    int port;
    String message;

    public Client (InetAddress address, int port, String message){
        this.address = address;
        this.port = port;
        this.message = message;
    }

    @Override
    public void run() {
        try(Socket socket = new Socket(address, port)){
            System.out.println();
            String msg = new Date() + "-" + message;
            socket.getOutputStream().write(msg.getBytes(StandardCharsets.UTF_8));
        }catch (IOException e){
            System.out.println();
        }
    }
}
