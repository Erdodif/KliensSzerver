package hu.petrik.feladat2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        try {
            Socket connection = new Socket("localhost", 9999);
            DataInputStream fromServer = new DataInputStream(connection.getInputStream());
            DataOutputStream toServer = new DataOutputStream(connection.getOutputStream());
            Scanner sc = new Scanner(System.in);
            System.out.println("Kérem a téglalap egyik oldalát:");
            System.out.print("\t");
            int a = sc.nextInt();
            System.out.println("Kérem a téglalap egyik oldalát:");
            System.out.print("\t");
            int b = sc.nextInt();
            toServer.writeInt(a);
            toServer.flush();
            toServer.writeInt(b);
            toServer.flush();
            int menu;
            do {
                System.out.println("*Menü*\n1 - Terület\n2 - Kerület\n3 - Négyzet-e\n4 - Átló mérete\n5 - Kilépés\nválasztás:");
                menu = sc.nextInt();
                toServer.writeInt(menu);
                toServer.flush();
                String eredmeny = fromServer.readUTF();
                System.out.println("Szerver válasza:\n\t"+ eredmeny);
            } while (menu != 5);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

}
