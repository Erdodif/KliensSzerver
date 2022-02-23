package hu.petrik.feladat2;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public static void main(String[] args) {
        System.out.println("Szerver Indítása...");
        ExecutorService exe = Executors.newCachedThreadPool();
        try {
            ServerSocket socket = new ServerSocket(9999);
            while (true){
                Socket connection = socket.accept();
                InetAddress client = socket.getInetAddress();
                ClientServer clientServer = new ClientServer(connection);
                exe.submit(clientServer);
            }
        } catch(IOException e){
            System.err.println(e);
        }

    }
}
