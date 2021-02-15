package OpenWeatherMap.WeatherInfo;

/**
 * Has the WeatherTime fields and additional fields
 */
public class HourlyWeather extends WeatherTime {
    private double pop;

    public HourlyWeather() {

    }

    /**
     * toString method
     * @return String with all field variables
     */
    @Override
    public String toString() {
        return "HourlyWeather{" +
                "dt=" + getDt() +
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
                ", pop=" + pop +
                '}';
    }

    /**
     * SETTERS AND GETTERS
     */

    public double getPop() {
        return pop;
    }

    public void setPop(int pop) {
        this.pop = pop;
    }
}
