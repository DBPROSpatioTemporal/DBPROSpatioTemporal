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
        UserWeatherEvaluation uwe = new UserWeatherEvaluation(user, weather);

        System.out.println("Is 1 Hour Forecast good: " + uwe.isForecastWeatherGood(1));

    }

    @Test
    public void AgeGroupYouthForeCastWeather() throws IOException {
        User user = new User(20, true, true, true, true);
        WeatherInfo weather = new OpenWeatherMap().requestWeather(52.503056, 13.468889);
        UserWeatherEvaluation uwe = new UserWeatherEvaluation(user, weather);

        System.out.println("Is 1 Hour Forecast good: " + uwe.isForecastWeatherGood(1));

    }

}
