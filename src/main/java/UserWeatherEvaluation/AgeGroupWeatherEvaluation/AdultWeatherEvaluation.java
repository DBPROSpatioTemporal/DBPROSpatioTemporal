package UserWeatherEvaluation.AgeGroupWeatherEvaluation;

import OpenWeatherMap.WeatherInfo.WeatherTime;

/**
 * Weather Evaluation form the Age group Adult (Age: 25-64)
 * Extends the abstract class AgeGroupType
 */
public class AdultWeatherEvaluation extends AgeGroupType{
    private int minAge = 25;
    private int maxAge = 64;

    @Override
    public boolean isAgeTempGood(WeatherTime weather) {
        return isBetween(weather.getTemp(), -15, 40);
    }

    @Override
    public boolean isAgeWindSpeedGood(WeatherTime weather) {
        return (weather.getWind_speed() < 20.5);
    }

    @Override
    protected boolean isThunderstormWeatherGood(int WeatherID) {
        return false;
    }

    @Override
    protected boolean isDrizzleWeatherGood(int WeatherID) {
        return true;
    }

    @Override
    protected boolean isRainWeatherGood(int WeatherID) {
        return !isBetween(WeatherID, 504, 511);
    }

    @Override
    protected boolean isSnowWeatherGood(int WeatherID) {
        return !(WeatherID == 602 || WeatherID == 622);
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
