package hu.petrik.feladat4.weather;

public class Forecast {
    private String province;
    private WeatherODay today;
    private WeatherODay tomorrow;

    public Forecast(String sor) {
        String[] data = sor.split("\\t+", -1);
        this.province = data[0].trim();
        this.today = new WeatherODay(data[1].trim(), data[2].trim());
        this.tomorrow = new WeatherODay(data[3].trim(), data[4].trim());
    }

    public String getProvince() {
        return province;
    }

    public WeatherODay getToday() {
        return today;
    }

    public WeatherODay getTomorrow() {
        return tomorrow;
    }

    @Override
    public String toString() {
        return "weather in " + province + ":" +
                "\n\ttoday: " + today +
                "\n\ttomorrow: " + tomorrow +'\n';
    }
}
