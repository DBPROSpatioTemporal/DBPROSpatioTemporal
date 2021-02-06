import org.junit.Test;
import OpenWeatherMap.OpenWeatherMap;
import OpenWeatherMap.WeatherInfo.WeatherInfo;

import java.io.IOException;

public class WeatherTest {
    private OpenWeatherMap owmnew = new OpenWeatherMap();

    @Test
    public void requestWeather() throws IOException {
        WeatherInfo weather= owmnew.requestWeather(52.503056, 13.468889);
        System.out.println(weather.toString());
        System.out.println(weather.getHourly(10).getWeather().toString());
        System.out.println(weather.getCurrent().toString());
        System.out.println(weather.getHourly(1).toString());
        System.out.println(weather.getHourly(2).toString());
        System.out.println(weather.getHourly(3).toString());
        System.out.println(weather.getHourly(48).toString());
    }


}
