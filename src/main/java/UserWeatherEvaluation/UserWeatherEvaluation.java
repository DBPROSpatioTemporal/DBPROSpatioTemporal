package UserWeatherEvaluation;

import OpenWeatherMap.WeatherInfo.CurrentWeather;
import OpenWeatherMap.WeatherInfo.HourlyWeather;
import OpenWeatherMap.WeatherInfo.WeatherInfo;
import User.User;

/**
 * This class uses classes User.class and WeatherInfo.class to evaluate if the weather is good
 * (given a WeatherInfo.class) for a specific User (given a User.class).
 * This is done with the design pattern of Inversion of Control (IoC) and more specifically
 * through Dependency Injection (DI). Dependency's being User.class and WeatherInfo.class.
 * Both dependency classes are passed thorough the constructor.
 */
public class UserWeatherEvaluation {
    private User user;
    private WeatherInfo weatherInfo;

    public UserWeatherEvaluation(User user, WeatherInfo weatherInfo) {
        this.weatherInfo = weatherInfo;
        this.user = user;
    }

    
    /**
     * checks if the current weather condition is good.
     * Temperature must be between -10 and 40 degrees Celsius
     * https://openweathermap.org/weather-conditions
     * @return true if weather is good and false otherwise.
     */
    public boolean isCurrentWeatherGood() {
        // get current Weather of weatherInfo.class
        CurrentWeather currentWeather = weatherInfo.getCurrent();
        // Checks if the Temperature is between -10 and 40 degrees. If not returns false
        if (currentWeather.getTemp() < -10.0 || currentWeather.getTemp() > 40.0) {
            return false;
        }
        // Checks what the weather is like.
        // TODO: make it more specific
        if (currentWeather.getWeather().get(0).getMain().equals("Thunderstorm")  ||
                currentWeather.getWeather().get(0).getMain().equals("Drizzle")  ||
                currentWeather.getWeather().get(0).getMain().equals("Rain")  ||
                currentWeather.getWeather().get(0).getMain().equals("Snow") ) {
            return false;
        }
        return true;
    }

    public boolean isForecastWeatherGood(int forecastHour) {
        // get hourly Weather Forecast by index
        HourlyWeather hourlyWeather = weatherInfo.getHourly(forecastHour);

        return true;
    }

    // just a test function
    public boolean isOldEnough() {
        return user.getAge() > 50;
    }
}
