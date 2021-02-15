package UserWeatherEvaluation.AgeGroupWeatherEvaluation;

import OpenWeatherMap.WeatherInfo.WeatherTime;

/**
 * Weather Evaluation form the Age group Children (Age: 0-14)
 * Extends the abstract class AgeGroupType
 */
public class ChildrenWeatherEvaluation extends AgeGroupType{
    private int minAge = 0;
    private int maxAge = 14;

    @Override
    public boolean isAgeGroupType(int age) {
        return minAge <= age && age <= maxAge;
    }

    @Override
    public boolean isAgeTempGood(WeatherTime weather) {
        return isBetween(weather.getTemp(), -5, 35);
    }

    @Override
    public boolean isAgeWindSpeedGood(WeatherTime weather) {
        return (weather.getWind_speed() < 12);
    }


    @Override
    protected boolean isThunderstormWeatherGood(int WeatherID) {
        return false;
    }

    @Override
    protected boolean isDrizzleWeatherGood(int WeatherID) {
        return !isBetween(WeatherID, 310, 321);
    }

    @Override
    protected boolean isRainWeatherGood(int WeatherID) {
        return !isBetween(WeatherID, 502, 531);
    }

    @Override
    protected boolean isSnowWeatherGood(int WeatherID) {
        return !isBetween(WeatherID, 602, 622);
    }

    @Override
    protected boolean isAtmosphereWeatherGood(int WeatherID) {
        return !isBetween(WeatherID, 741, 781);
    }

    @Override
    protected boolean isClearWeatherGood(int WeatherID) {
        return true;
    }

    @Override
    protected boolean isCloudsWeatherGood(int WeatherID) {
        return true;
    }

}
