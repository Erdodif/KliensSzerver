package hu.petrik.feladat4;

import hu.petrik.feladat4.weather.Forecast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ClientServer implements Runnable {
    private Socket connection;
    private DataInputStream fromClient;
    private DataOutputStream toClient;
    private InetAddress client;
    private HashMap<String, Forecast> forecasts;

    public ClientServer(Socket connection, HashMap<String, Forecast> forecasts) throws IOException {
        this.connection = connection;
        this.client = connection.getInetAddress();
        this.fromClient = new DataInputStream(connection.getInputStream());
        this.toClient = new DataOutputStream(connection.getOutputStream());
        this.forecasts = forecasts;
    }

    @Override
    public void run() {
        try {
            handleMenu();
            System.out.println("Kapcsolat létrejött: " + connection.getInetAddress().getHostName());
        } catch (SocketException e) {
            System.out.println("Kapcsolat lezárult: " + connection.getInetAddress().getHostName());
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public void handleMenu() throws IOException {
        int menu;
        do {
            menu = fromClient.readInt();
            switch (menu) {
                case 1:
                    StringBuilder boby = new StringBuilder();
                    for (Forecast forecast : forecasts.values()) {
                        boby.append(forecast);
                    }
                    toClient.writeUTF(boby.toString());
                    break;
                case 2:
                    String province = capitalize(getInput("Kérem adja meg a megyét!"));
                    try {
                        Forecast forecast = forecasts.get(province);
                        toClient.writeUTF(forecast.toString());
                    } catch (Exception e) {
                        toClient.writeUTF("Megye nem található!");
                    }
                    break;
                case 3:
                    boolean minToday = Objects.equals(capitalize(getInput("Mai legyen? (igen)")), "Igen");
                    Forecast minForecast = forecasts.values().stream().findFirst().get();
                    for (Forecast forecast : forecasts.values()) {
                        if ((minToday &&
                                forecast.getToday().getMin() < minForecast.getToday().getMin()) ||
                                (!minToday &&
                                        forecast.getTomorrow().getMin() < minForecast.getTomorrow().getMin())) {
                            minForecast = forecast;
                        }
                    }
                    toClient.writeUTF("A " + (minToday ? "mai" : "holnapi") + " leghidegebb megye: " + minForecast.getProvince());
                    break;
                case 4:
                    boolean maxToday = Objects.equals(capitalize(getInput("Mai legyen? (igen)")), "Igen");
                    Forecast maxForecast = forecasts.values().stream().findFirst().get();
                    for (Forecast forecast : forecasts.values()) {
                        if ((maxToday &&
                                forecast.getToday().getMax() > maxForecast.getToday().getMax()) ||
                                (!maxToday &&
                                        forecast.getTomorrow().getMax() < maxForecast.getTomorrow().getMax())) {
                            maxForecast = forecast;
                        }
                    }
                    toClient.writeUTF("A " + (maxToday ? "mai" : "holnapi") + " legmelegebb megye: " + maxForecast.getProvince());
                    break;
                case 5:
                    String weather = capitalize(getInput("Kérem adja meg az előrejelzést!"));
                    boolean weatherToday = Objects.equals(capitalize(getInput("Mai legyen? (igen)")), "Igen");
                    boby = new StringBuilder();
                    try {
                        for(Forecast forecast: forecasts.values()){
                            if( (weatherToday && forecast.getToday().getWeather().equals(weather))||
                                    (!weatherToday && forecast.getTomorrow().getWeather().equals(weather))){
                                boby.append(forecast);
                            }
                        }
                        toClient.writeUTF(boby.toString());
                    } catch (Exception e) {
                        toClient.writeUTF("Megye a megadott előrejelézéssel nem található!");
                    }
                    break;
                case 6:
                    toClient.writeUTF("Viszont látásra.");
                    throw new SocketException("Folyamat lezárult");
                default:
                    toClient.writeUTF("Hibás menüpont");
                    break;
            }
        } while (menu != 15);

    }

    public String capitalize(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }

    public String getInput(String content) throws IOException {
        toClient.writeUTF("INPUTREQ");
        toClient.writeUTF(content);
        return fromClient.readUTF();
    }
}
