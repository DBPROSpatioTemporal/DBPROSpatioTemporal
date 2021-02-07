package UserWeatherEvaluation;

import OpenWeatherMap.WeatherInfo.CurrentWeather;
import OpenWeatherMap.WeatherInfo.HourlyWeather;
import OpenWeatherMap.WeatherInfo.WeatherInfo;
import OpenWeatherMap.WeatherInfo.WeatherTime;
import User.User;
import UserWeatherEvaluation.AgeGroupWeatherEvaluation.*;

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
     * checks if the current weather condition is good for the User
     * https://openweathermap.org/weather-conditions
     * @return true if weather is good and false otherwise.
     */
    public boolean isCurrentWeatherGood() {
        // get current Weather of weatherInfo.class
        CurrentWeather currentWeather = weatherInfo.getCurrent();
        // Get The age Group Type
        AgeGroupType ageGroupType = getAgeGroupType(user.getAge());
        // Check if Weather is good for that Age Group Type
        boolean isWeatherGood = isWeatherGood(currentWeather, ageGroupType);

        // All of the Options have to be good for the Current Weather to be good;
        return (isWeatherGood);
    }

    /**
     * checks if the forecast weather condition is good for the User
     * Gets as parameter the hour forecast to be looked at
     * @param forecastHour
     * @return true if weather is good and false otherwise.
     */
    public boolean isForecastWeatherGood(int forecastHour) {
        // get hourly Weather Forecast by index
        HourlyWeather hourlyWeather = weatherInfo.getHourly(forecastHour);
        // Get The age Group Type
        AgeGroupType ageGroupType = getAgeGroupType(user.getAge());
        // Check if Weather is good for that Age Group Type
        boolean isWeatherGood = isWeatherGood(hourlyWeather, ageGroupType);

        // All of the Options have to be good for the Current Weather to be good;
        return (isWeatherGood);
    }

    /**
     * With the age of the User it is being determined if he is a Child, Youth, Adult, Senior
     * returns a class that is specific for that Age Group
     * @param age of the user
     * @return An AgeGroupType class that is specific for the age of the User
     */
    private AgeGroupType getAgeGroupType(int age) {
        if (age < 0) {

            System.out.println("not valid age");
            return null;

        } else if (isBetween(age, 0, 14)) { // Age Group 0-14: Children

            System.out.println("Age Group: Children");
            // Temperature appropriate for Children is between 0 and 30 degrees
            return new ChildrenWeatherEvaluation();

        } else if (isBetween(age, 15, 24)) { // Age Group 15-24: Youth

            System.out.println("Age Group: Youth");
            // Temperature appropriate for Youth is between -10 and 40 degrees
            return new YouthWeatherEvaluation();

        } else if (isBetween(age, 25, 64)) { // Age Group 25-64: Adults

            System.out.println("Age Group: Adults");
            // Temperature appropriate for Adults is between -10 and 40 degrees
            return new AdultWeatherEvaluation();

        } else {

            System.out.println("Age Group: Seniors"); // Age Group >= 65: Seniors
            // Temperature appropriate for Adults is between -10 and 40 degrees
            return new SeniorWeatherEvaluation();

        }
    }

    /**
     * Test if the Weather Conditions (Temperature, Weather, Wind Speed) is good for a given Age Group
     * (Children, Youth, Adult, Senior)
     * returns true if the all the conditions are good.
     * @param weather CurrentWeather or HourlyWeather
     * @param ageGroupType One of the four Age Group Types (Children, Youth, Adult, Senior)
     * @return true if the Weather is good for a User within a age group
     */
    private boolean isWeatherGood(WeatherTime weather, AgeGroupType ageGroupType) {
        // Is Temperature good for the Users age
        boolean isAgeTempGood = ageGroupType.isAgeTempGood(weather);
        // Is Weather good for the User age
        boolean isAgeWeatherGood = ageGroupType.isAgeWeatherGood(weather);
        // Is Wind speed fine for the User age
        boolean isWindSpeedGood = ageGroupType.isAgeWindSpeedGood(weather); // TODO: to implement returns always true

        // All of the Options have to be good for the Current Weather to be good;
        return (isAgeTempGood && isAgeWeatherGood && isWindSpeedGood);
    }


    private boolean isBetween(double x, double lower, double upper) {
        return lower <= x && x <= upper;
    }

    // just a test function
    public boolean isOldEnough() {
        return user.getAge() > 50;
    }
}
