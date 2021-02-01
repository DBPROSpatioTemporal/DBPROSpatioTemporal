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
        System.out.println(weather.getHourly().get(10).getWeather().toString());
        System.out.println(weather.getCurrent().toString());
        System.out.println(weather.getHourly().get(0).toString());
        System.out.println(weather.getHourly().get(1).toString());
        System.out.println(weather.getHourly().get(2).toString());
        System.out.println(weather.getHourly().get(47).toString());
    }

    @Test
    public void isWeatherGood() throws IOException {
        WeatherInfo weather = owmnew.requestWeather(52.503056, 13.468889);
        System.out.println(weather.getCurrent().isWeatherGood());
        System.out.println(weather.getHourly().get(46).isWeatherGood());
    }

}
