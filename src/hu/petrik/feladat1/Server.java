package hu.petrik.feladat1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        System.out.println("Szerver Indítása...");
        try {
            ServerSocket socket = new ServerSocket(9999);
            Socket connection = socket.accept();
            System.out.println("Szerver Elindult...");
            DataInputStream fromClient = new DataInputStream(connection.getInputStream());
            DataOutputStream toClient = new DataOutputStream(connection.getOutputStream());
            InetAddress client = connection.getInetAddress();
            System.out.println("Client name:\t" + client.getHostName());
            System.out.println("Client host:\t" + client.getHostAddress());
            while (true) {
                int sugar = fromClient.readInt();
                double kerulet = 2 * sugar * Math.PI;
                toClient.writeDouble(kerulet);
                double terulet = Math.pow(sugar,2) * Math.PI;
                toClient.writeDouble(terulet);
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

}
