package UserWeatherEvaluation.AgeGroupWeatherEvaluation;

import OpenWeatherMap.WeatherInfo.WeatherTime;

/**
 * Weather Evaluation form the Age group Senior (Age: >= 65)
 * Extends the abstract class AgeGroupType
 */
public class SeniorWeatherEvaluation extends AgeGroupType{
    private int minAge = 65;
    private int maxAge = 100;



    @Override
    public boolean isAgeTempGood(WeatherTime weather) {
        return isBetween(weather.getTemp(), -5, 40);
    }

    @Override
    public boolean isAgeWindSpeedGood(WeatherTime weather) {
        return (weather.getWind_speed() < 14);
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
