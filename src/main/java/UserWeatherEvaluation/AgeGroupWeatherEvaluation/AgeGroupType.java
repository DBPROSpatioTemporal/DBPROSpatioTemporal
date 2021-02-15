package UserWeatherEvaluation.AgeGroupWeatherEvaluation;

import OpenWeatherMap.WeatherInfo.WeatherTime;

/**
 * An abstract class that represents an Age Group Type
 * The different Age Group Types are Children, Youth, Adult and Senior
 * all of the Age Group Types extend this abstract class since every one interprets what Good Weather is differently
 */
public abstract class AgeGroupType {
    private int maxAge;
    private int minAge;

    public boolean isAgeGroupType(int age){
        return isBetween(age, minAge, maxAge);
    }

    /**
     * Checks if the Temperature for the Age Group Type is good
     * @param weather   Weather that contains Temperature information
     * @return true if Temperature for age group is good
     */
    public abstract boolean isAgeTempGood(WeatherTime weather);


    public abstract boolean isAgeWindSpeedGood(WeatherTime weather);

    /**
     * Checks if the Weather is good for that age group under specific Weather conditions
     * All weather conditions: https://openweathermap.org/weather-conditions
     * @param weather currentWeather or HourlyWeather
     * @return true if Weather is good for Age Group
     */
    public boolean isAgeWeatherGood(WeatherTime weather) {
        int weatherID = weather.getWeather().get(0).getId();
        return switch (weather.getWeather().get(0).getMain()) {
            case "Thunderstorm" -> isThunderstormWeatherGood(weatherID);
            case "Drizzle" -> isDrizzleWeatherGood(weatherID);
            case "Rain" -> isRainWeatherGood(weatherID);
            case "Snow" -> isSnowWeatherGood(weatherID);
            case "Atmosphere" -> isAtmosphereWeatherGood(weatherID);
            case "Clear" -> isClearWeatherGood(weatherID);
            case "Clouds" -> isCloudsWeatherGood(weatherID);
            default -> false;
        };
    }

    /**
     * Checks if the Thunderstorm conditions for the Age Group Type is good
     * It checks by testing the WeatherID https://openweathermap.org/weather-conditions
     * @param WeatherID of the Weather that OpenWeatherMaps gives
     * @return true if Weather Condition is good for Age Group
     */
    protected abstract boolean isThunderstormWeatherGood(int WeatherID);

    /**
     * Checks if the Drizzle conditions for the Age Group Type is good
     * It checks by testing the WeatherID https://openweathermap.org/weather-conditions
     * @param WeatherID of the Weather that OpenWeatherMaps gives
     * @return true if Weather Condition is good for Age Group
     */
    protected abstract boolean isDrizzleWeatherGood(int WeatherID);

    /**
     * Checks if the Rain conditions for the Age Group Type is good
     * It checks by testing the WeatherID https://openweathermap.org/weather-conditions
     * @param WeatherID of the Weather that OpenWeatherMaps gives
     * @return true if Weather Condition is good for Age Group
     */
    protected abstract boolean isRainWeatherGood(int WeatherID);

    /**
     * Checks if the Snow conditions for the Age Group Type is good
     * It checks by testing the WeatherID https://openweathermap.org/weather-conditions
     * @param WeatherID of the Weather that OpenWeatherMaps gives
     * @return true if Weather Condition is good for Age Group
     */
    protected abstract boolean isSnowWeatherGood(int WeatherID);

    /**
     * Checks if the Atmosphere conditions for the Age Group Type is good
     * It checks by testing the WeatherID https://openweathermap.org/weather-conditions
     * @param WeatherID of the Weather that OpenWeatherMaps gives
     * @return true if Weather Condition is good for Age Group
     */
    protected abstract boolean isAtmosphereWeatherGood(int WeatherID);

    /**
     * Checks if the Clear Weather conditions for the Age Group Type is good
     * It checks by testing the WeatherID https://openweathermap.org/weather-conditions
     * @param WeatherID of the Weather that OpenWeatherMaps gives
     * @return true if Weather Condition is good for Age Group
     */
    protected abstract boolean isClearWeatherGood(int WeatherID);

    /**
     * Checks if the Cloud conditions for the Age Group Type is good
     * It checks by testing the WeatherID https://openweathermap.org/weather-conditions
     * @param WeatherID of the Weather that OpenWeatherMaps gives
     * @return true if Weather Condition is good for Age Group
     */
    protected abstract boolean isCloudsWeatherGood(int WeatherID);

    /**
     * Helper function that returns true if x is between lower and upper bound
     * @param x     Number to test
     * @param lower bound
     * @param upper bound
     * @return      true if x is between lower and upper
     */
    public boolean isBetween(double x, double lower, double upper) {
        return lower <= x && x <= upper;
    }
}
