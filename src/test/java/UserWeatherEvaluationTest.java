import OpenWeatherMap.OpenWeatherMap;
import OpenWeatherMap.WeatherInfo.WeatherInfo;
import User.User;
import UserWeatherEvaluation.UserWeatherEvaluation;
import org.junit.Test;

import java.io.IOException;

public class UserWeatherEvaluationTest {

    @Test
    public void UserWeatherEvaluation() throws IOException {
        User user = new User(50, true, true, true, true);
        WeatherInfo weather = new OpenWeatherMap().requestWeather(52.503056, 13.468889);

        UserWeatherEvaluation uwe = new UserWeatherEvaluation(user, weather);
        System.out.println(uwe.isCurrentWeatherGood());
        System.out.println("User age: " + user.getAge() +" is Old Enough: " + uwe.isOldEnough());
        user.setAge(51);
        System.out.println("User age: " + user.getAge() +" is Old Enough: " + uwe.isOldEnough()); // Address is correctly copied with Call-by-Value (behaves just like Call-by-Reference but it just copies the Value of the Address so Call-by-Value it is)
    }

    @Test
    public void AgeGroupChildrenForeCastWeather() throws IOException {
        User user = new User(12, true, true, true, true);
        WeatherInfo weather = new OpenWeatherMap().requestWeather(52.503056, 13.468889);
        UserWeatherEvaluation uwe = new UserWeatherEvaluation(user, weather);

        System.out.println(uwe.isForecastWeatherGood(1));

    }

    @Test
    public void AgeGroupYouthForeCastWeather() throws IOException {
        User user = new User(20, true, true, true, true);
        WeatherInfo weather = new OpenWeatherMap().requestWeather(52.503056, 13.468889);
        UserWeatherEvaluation uwe = new UserWeatherEvaluation(user, weather);

        System.out.println(uwe.isForecastWeatherGood(1));

    }



//    @Test
//    public void AgeGroupChildrenCurrentWeather() throws IOException {
//        User user = new User(12, true, true, true, true);
//        WeatherInfo weather = new OpenWeatherMap().requestWeather(52.503056, 13.468889);
//        UserWeatherEvaluation uwe = new UserWeatherEvaluation(user, weather);
//
//        System.out.println(uwe.isAgeTempGood(weather.getCurrent()));
//    }
//
//    @Test
//    public void AgeGroupYouthCurrentWeather() throws IOException {
//        User user = new User(20, true, true, true, true);
//        WeatherInfo weather = new OpenWeatherMap().requestWeather(52.503056, 13.468889);
//        UserWeatherEvaluation uwe = new UserWeatherEvaluation(user, weather);
//
//        System.out.println(uwe.isAgeTempGood(weather.getCurrent()));
//    }
//
//    @Test
//    public void AgeGroupYouthForecastWeather() throws IOException {
//        User user = new User(20, true, true, true, true);
//        WeatherInfo weather = new OpenWeatherMap().requestWeather(52.503056, 13.468889);
//        UserWeatherEvaluation uwe = new UserWeatherEvaluation(user, weather);
//
//        System.out.println(uwe.isAgeTempGood(weather.getHourly(1)));
//    }
}
