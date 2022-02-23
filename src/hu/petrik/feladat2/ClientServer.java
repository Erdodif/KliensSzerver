package hu.petrik.feladat2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class ClientServer implements Runnable {

    Socket connection;

    public ClientServer(Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        try {
            System.out.println("Kapcsolat létrejött: " + connection.getInetAddress().getHostName());
            DataInputStream fromClient = new DataInputStream(connection.getInputStream());
            DataOutputStream toClient = new DataOutputStream(connection.getOutputStream());
            while (true) {
                int a = fromClient.readInt();
                int b = fromClient.readInt();
                int menu;
                do {
                    menu = fromClient.readInt();

                    String ki;
                    switch (menu) {
                        case 1:
                            ki = "Kerület: " + 2 * (a + b);
                            break;
                        case 2:
                            ki = "Terület: " + a * b;
                            break;
                        case 3:
                            ki = "A megadott alakzat " + ((a == b) ? "" : "nem ") + "négyzet.";
                            break;
                        case 4:
                            ki = "Az átló mérete: " + Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
                            break;
                        default:
                            ki = "Viszont látásra!";
                            break;
                    }
                    toClient.writeUTF(ki);
                    toClient.flush();
                } while (menu != 5);
            }
        } catch (SocketException e) {
            System.out.println("Kapcsolat lezárult: " + connection.getInetAddress().getHostName());
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
