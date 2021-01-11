package weather;
import net.aksingh.owmjapis.api.APIException;

public class App {

    public static void main(String[] args) throws APIException {
        // neo4j and OpenWeatherMaps
        neo4jAPI neo4j = new neo4jAPI();
        owmAPI owm = new owmAPI();

        String stationName = "Ostkreuz";

        // get the coords of the Station (stationName) from neo4j
        double[] stationCoords = neo4j.getLatitudeAndLongitude(stationName);
        // Get the weather of the coords form OpenWeatherMaps and save results in WeatherInfo Object
        WeatherInfo currentWeather = owm.requestCurrentWeather(stationCoords[0], stationCoords[1]);

        // print weather info
        System.out.println(currentWeather.getMainInfo());
        System.out.println(currentWeather.getMoreInfo());

        // Save current Weather in neo4j form the WeatherInfo Object
        neo4j.setCurrentWeather(currentWeather.getMainInfo());

        neo4j.closeDriver();
    }
}