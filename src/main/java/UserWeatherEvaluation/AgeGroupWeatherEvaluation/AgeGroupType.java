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

    public abstract boolean isAgeTempGood(WeatherTime weather);

    public abstract boolean isAgeWindSpeedGood(WeatherTime weather);

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

    protected abstract boolean isThunderstormWeatherGood(int WeatherID);

    protected abstract boolean isDrizzleWeatherGood(int WeatherID);

    protected abstract boolean isRainWeatherGood(int WeatherID);

    protected abstract boolean isSnowWeatherGood(int WeatherID);

    protected abstract boolean isAtmosphereWeatherGood(int WeatherID);

    protected abstract boolean isClearWeatherGood(int WeatherID);

    protected abstract boolean isCloudsWeatherGood(int WeatherID);

    public boolean isBetween(double x, double lower, double upper) {
        return lower <= x && x <= upper;
    }
}
