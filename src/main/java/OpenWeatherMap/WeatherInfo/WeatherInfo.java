package OpenWeatherMap.WeatherInfo;

import java.util.List;

/**
 * This class has all the Weather Information.
 * It has all the data that was fetched from OpenWeatherMaps API request.
 * It also contains a CurrentWeather Object and a List of HourlyWeather
 * It contains 48 hours of forecast Weather data in a List
 * Since it's a list the first hour starts at index 0
 */
/*
 TODO: Change getHourly() into a method that takes the wanted hour to be forecasted in the parameter.
 so instead of writing getHourly().get(9) you can write getHourly(10)
 Also make it so you request hour starting by 1 and not 0 like an array
 This means requesting forecast: getHourly(1) -> getHourly(48)
 */
public class WeatherInfo {
    private double lat;
    private double  lon;
    private String  timezone;
    private int  timezone_offset;
    CurrentWeather current;
    List<HourlyWeather> hourly;

    public WeatherInfo() {

    }

    @Override
    public String toString() {
        return "WeatherInfo{" +
                "lat=" + lat +
                ", lon=" + lon +
                ", timezone='" + timezone + '\'' +
                ", timezone_offset=" + timezone_offset +
                ", current=" + current +
                ", hourly=" + hourly +
                '}';
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public int getTimezone_offset() {
        return timezone_offset;
    }

    public void setTimezone_offset(int timezone_offset) {
        this.timezone_offset = timezone_offset;
    }

    public CurrentWeather getCurrent() {
        return current;
    }

    public void setCurrent(CurrentWeather current) {
        this.current = current;
    }

    public List<HourlyWeather> getHourly() {
        return hourly;
    }

    public void setHourly(List<HourlyWeather> hourly) {
        this.hourly = hourly;
    }

}
