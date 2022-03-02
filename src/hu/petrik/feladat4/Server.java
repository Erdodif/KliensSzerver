package hu.petrik.feladat4;

import hu.petrik.feladat4.weather.Forecast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static HashMap<String, Forecast> forecasts;

    public static void setForecastsFromFile(String fileName){
        forecasts = new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            reader.readLine();
            String line = reader.readLine();
            while (line != null){
                Forecast forecast = new Forecast(line);
                forecasts.put(forecast.getProvince(),forecast);
                line = reader.readLine();
            }
        }
        catch (FileNotFoundException e){
            System.err.println("File not found");
        }
        catch (IOException e){
            System.err.println(e);
        }
    }

    public static void main(String[] args){
        setForecastsFromFile("weather.txt");
        System.out.println("Szerver Indítása...");
        ExecutorService exe = Executors.newCachedThreadPool();
        ServerSocket socket;
        try {
            socket = new ServerSocket(9999);
            System.out.println("Szerver Elindult...");
            while (true) {
                try {
                    Socket connection = socket.accept();
                    ClientServer clientServer = new ClientServer(connection,forecasts);
                    exe.submit(clientServer);
                } catch (IOException e) {
                    System.err.println(e);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
