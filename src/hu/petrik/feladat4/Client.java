package hu.petrik.feladat4;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args){
        try {
            Socket connection = new Socket("localhost",9999);
            DataInputStream fromServer = new DataInputStream(connection.getInputStream());
            DataOutputStream toServer = new DataOutputStream(connection.getOutputStream());
            int menu;
            do {
                menu = menuSelector();
                toServer.writeInt(menu);
                toServer.flush();
                String valasz = fromServer.readUTF();
                while (valasz.equals("INPUTREQ")){
                    valasz = fromServer.readUTF();
                    System.out.println(valasz);
                    toServer.writeUTF(sc.nextLine());
                    valasz = fromServer.readUTF();
                }
                System.out.println("Eredmény: "+valasz);
            } while (menu != 6);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
    public static int menuSelector(){
        System.out.println("*Menü*");
        System.out.println(" 1 - Előrejelzések listázása");
        System.out.println(" 2 - Megye kereső");
        System.out.println(" 3 - Leghidegebb megye");
        System.out.println(" 4 - Legmelegebb megye");
        System.out.println(" 5 - Előrejelzés kereső");
        System.out.println(" 6 - Kilépés");
        int menu;
        try {
            menu = Integer.parseInt(sc.nextLine());
        }
        catch (Exception e){
            menu = 0;
        }
        return menu;
    }
}
