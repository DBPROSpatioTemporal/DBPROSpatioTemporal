package OpenWeatherMap.WeatherInfo;

import java.util.List;

/**
 * This class has all the Weather Information.
 * It has all the data that was fetched from OpenWeatherMaps API request.
 * It also contains a CurrentWeather Object and a List of HourlyWeather
 * It contains 48 hours of forecast Weather data in a List
 * getHourly() getter has been changed to return the requested HourlyWeather class immediately
 * instead of returning the whole List<HourlyWeather>.
 * This can be done by using getHourly(1) -> getHourly(48)
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

    /**
     * Getter changed to return the requested HourlyWeather instead of the whole List of weather forecast
     * Hours available are form 1 to 48
     * @param forecastHour the hour that we want to access (Forecast Hours 1-48)
     * @return HourlyWeather Class w
     */
    public HourlyWeather getHourly(int forecastHour) {
        return hourly.get(forecastHour - 1);
    }

    public void setHourly(List<HourlyWeather> hourly) {
        this.hourly = hourly;
    }

}
