package hu.petrik.feladat1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        try {
            Socket connection = new Socket("localhost",9999);
            DataInputStream fromServer = new DataInputStream(connection.getInputStream());
            DataOutputStream toServer = new DataOutputStream(connection.getOutputStream());
            Scanner sc = new Scanner(System.in);
            while (true){
                System.out.println("Kérem a kör sugatár:");
                System.out.print("\t");
                int sugar = sc.nextInt();
                toServer.writeInt(sugar);
                toServer.flush();
                double kerulet = fromServer.readDouble();
                System.out.printf("kerület a szerver szerint %.2f\n",kerulet);
                double terulet = fromServer.readDouble();
                System.out.printf("terület a szerver szerint %.2f\n",terulet);
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

}
