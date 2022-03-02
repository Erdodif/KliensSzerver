package hu.petrik.feladat3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            Socket connection = new Socket("localhost",9999);
            DataInputStream fromServer = new DataInputStream(connection.getInputStream());
            DataOutputStream toServer = new DataOutputStream(connection.getOutputStream());
            int menu;
            do {
                menu = menuValaszto();
                toServer.writeInt(menu);
                toServer.flush();
                String valasz = fromServer.readUTF();
                if (valasz.equals("INPUTREQ")){
                    valasz = fromServer.readUTF();
                    System.out.println(valasz);
                    toServer.writeUTF(sc.nextLine());
                    valasz = fromServer.readUTF();
                }
                System.out.println("Eredmény: "+valasz);
            } while (menu != 15);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public static int menuValaszto(){
        System.out.println("*Menü*");
        System.out.println(" 1 - Indiánok listázása");
        System.out.println(" 2 - Indiánok száma");
        System.out.println(" 3 - Különböző eszközök száma");
        System.out.println(" 4 - Adott törzs tagjainak száma");
        System.out.println(" 5 - Legnagyobb törzs");
        System.out.println(" 6 - Adott törzs férfi/nő aránya");
        System.out.println(" 7 - Minden törzs férfi/nő aránya");
        System.out.println(" 8 - Adott tárgyból a legtöbbel rendelkező törzs");
        System.out.println(" 9 - Adott törzs vének tanácsa");
        System.out.println("10 - Átlagos tulajdon arány");
        System.out.println("11 - Leggazdagabb gyerekekel rendelkező törzs");
        System.out.println("12 - Íjjas indiánok száma");
        System.out.println("13 - Törzsek leltárja");
        System.out.println("14 - Mezítlábas indiánok");
        System.out.println("15 - Kilépés");
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
