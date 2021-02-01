package OpenWeatherMap.WeatherInfo;

/**
 * Has the WeatherTime fields and additional fields
 */
public class CurrentWeather extends WeatherTime {
    private int sunrise;
    private int sunset;

    public CurrentWeather(){

    }

    @Override
    public String toString() {
        return "CurrentWeather{" +
                "dt=" + getDt() +
                ", sunrise=" + sunrise +
                ", sunset=" + sunset +
                ", temp=" + getTemp() +
                ", feels_like=" + getFeels_like() +
                ", pressure=" + getPressure() +
                ", humidity=" + getHumidity() +
                ", dew_point=" + getDew_point() +
                ", uvi=" + getUvi() +
                ", clouds=" + getClouds() +
                ", visibility=" + getVisibility() +
                ", wind_speed=" + getWind_speed() +
                ", wind_deg=" + getWind_deg() +
                ", weather=" + getWeather() +
                '}';
    }

    public int getSunrise() {
        return sunrise;
    }

    public void setSunrise(int sunrise) {
        this.sunrise = sunrise;
    }

    public int getSunset() {
        return sunset;
    }

    public void setSunset(int sunset) {
        this.sunset = sunset;
    }

}
