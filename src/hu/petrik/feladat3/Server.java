package hu.petrik.feladat3;

import hu.petrik.feladat3.indianok.Indian;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static ArrayList<Indian> indians;

    public static void main(String[] args) {
        System.out.println("Szerver Indítása...");
        initializeIndians();
        ExecutorService exe = Executors.newCachedThreadPool();
        ServerSocket socket;
        try {
            socket = new ServerSocket(9999);
            System.out.println("Szerver Elindult...");
            while (true) {
                try {
                    Socket connection = socket.accept();
                    ClientServer cs = new ClientServer(connection, indians);
                    exe.submit(cs);
                } catch (IOException e) {
                    System.err.println(e);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initializeIndians() {
        indians = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("indianok.txt"));
            String sor = br.readLine();
            while (sor != null) {
                indians.add(new Indian(sor));
                sor = br.readLine();
            }
        } catch (IOException e) {
            System.err.println("Iniciálás sikertelen!");
            System.err.println(e.getMessage());
        }
    }

}
