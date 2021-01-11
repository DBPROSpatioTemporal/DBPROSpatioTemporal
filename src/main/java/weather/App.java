package weather;
import net.aksingh.owmjapis.api.APIException;

public class App {

    public static void main(String[] args) throws APIException {
        // neo4j
       // neo4jAPI neo4j = new neo4jAPI();
       // System.out.println(neo4j.request("MATCH (n) RETURN n.name as stationNames"));

        // OpenWeatherMaps
        owmAPI owm = new owmAPI();
        owm.requestWeather("Berlin");


       // neo4j.closeDriver();
    }
}
