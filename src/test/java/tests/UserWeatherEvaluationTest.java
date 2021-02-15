package tests;
import OpenWeatherMap.OpenWeatherMap;
import OpenWeatherMap.WeatherInfo.WeatherInfo;
import User.User;
import UserWeatherEvaluation.UserWeatherEvaluation;
import org.junit.Test;

import java.io.IOException;

public class UserWeatherEvaluationTest {


    @Test
    public void AgeGroupChildrenForeCastWeather() throws IOException {
        User user = new User(12, true, true, true, true);
        WeatherInfo weather = new OpenWeatherMap().requestWeather(52.503056, 13.468889);
        weather.getHourly(1).setWind_speed(10);
        UserWeatherEvaluation uwe = new UserWeatherEvaluation(user, weather);

        System.out.println(uwe.isForecastWeatherGood(1));

    }

    @Test
    public void AgeGroupYouthForeCastWeather() throws IOException {
        User user = new User(20, true, true, false, false);
        WeatherInfo weather = new OpenWeatherMap().requestWeather(52.503056, 13.468889);
        //Setting Id for test
        weather.getHourly(1).getWeather().get(0).setMain("Drizzle");
        weather.getHourly(1).getWeather().get(0).setId(301);

        // Setting wind speed for test
        weather.getHourly(1).setWind_speed(15);

        UserWeatherEvaluation uwe = new UserWeatherEvaluation(user, weather);

        System.out.println(uwe.isForecastWeatherGood(1));

    }

}
