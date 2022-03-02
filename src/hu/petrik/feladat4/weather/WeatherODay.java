package hu.petrik.feladat4.weather;

public class WeatherODay {
    private String weather;
    private int min;
    private int max;

    public WeatherODay(String weather, int min, int max) {
        this.weather = weather;
        this.min = min;
        this.max = max;
    }

    public WeatherODay(String weather, String minMax) {
        this.weather = weather;
        String[] temp = minMax.split("/");
        this.min = Integer.parseInt(temp[0]);
        this.max = Integer.parseInt(temp[1]);
    }

    public String getWeather() {
        return weather;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    @Override
    public String toString() {
        return  weather + ' ' + ", " + min + "/" + max ;
    }
}
