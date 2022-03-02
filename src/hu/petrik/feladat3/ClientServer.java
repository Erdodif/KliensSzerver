package hu.petrik.feladat3;

import hu.petrik.feladat3.indianok.Indian;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class ClientServer implements Runnable {
    ArrayList<Indian> indians;
    Socket connection;
    DataInputStream fromClient;
    DataOutputStream toClient;
    InetAddress client;

    public ClientServer(Socket connection, ArrayList<Indian> indians) throws IOException {
        this.connection = connection;
        this.indians = indians;
        client = connection.getInetAddress();
        fromClient = new DataInputStream(connection.getInputStream());
        toClient = new DataOutputStream(connection.getOutputStream());
    }

    @Override
    public void run() {
        try {
            System.out.println("Kapcsolat létrejött: " + connection.getInetAddress().getHostName());
            int menu;
            do {
                menu = fromClient.readInt();
                switch (menu) {
                    case 1:
                        StringBuilder boby = new StringBuilder();
                        boby.append("Indiánok:").append('\n');
                        for (Indian indian : indians) {
                            boby.append(indian.toString()).append('\n');
                        }
                        toClient.writeUTF(boby.toString());
                        break;
                    case 2:
                        toClient.writeUTF(indians.size() + " indián van a nyilvántartásban.");
                        break;
                    case 3:
                        toClient.writeUTF(
                                indians.stream()
                                        .flatMap(indian -> indian.getInventory().stream())
                                        .distinct()
                                        .count() + " tárgytípus található a nyilvántartásban.");
                        break;
                    case 4:
                        String tribe = getInput("Adja meg a keresett törzset:");
                        long count = indians.stream().filter(indian -> indian.getTribe().equals(tribe)).count();
                        if (count == 0) {
                            toClient.writeUTF("A törzsnek vagy nincsenek tagjai, vagy hibásan lett megadva!");
                            break;
                        }
                        toClient.writeUTF("A törzs létszáma: " + count);
                        break;
                    case 5:
                        toClient.writeUTF("");
                        break;
                    case 6:
                        toClient.writeUTF("");
                        break;
                    case 7:
                        toClient.writeUTF("");
                        break;
                    case 8:
                        toClient.writeUTF("");
                        break;
                    case 9:
                        toClient.writeUTF("");
                        break;
                    case 10:
                        toClient.writeUTF("");
                        break;
                    case 11:
                        toClient.writeUTF("");
                        break;
                    case 12:
                        toClient.writeUTF("");
                        break;
                    case 13:
                        toClient.writeUTF("");
                        break;
                    case 14:
                        toClient.writeUTF("");
                        break;
                    case 15:
                        toClient.writeUTF("Viszont látásra.");
                        throw new SocketException("Folyamat lezárult");
                    default:
                        toClient.writeUTF("Hibás menüpont");
                        break;
                }
            } while (menu != 15);
        } catch (SocketException e) {
            System.out.println("Kapcsolat lezárult: " + connection.getInetAddress().getHostName());
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public String getInput(String content) throws IOException {
        toClient.writeUTF("INPUTREQ");
        toClient.writeUTF(content);
        return fromClient.readUTF();
    }
}
