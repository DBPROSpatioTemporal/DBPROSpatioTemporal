import Neo4J.neo4jAPI;
import OpenWeatherMap.OpenWeatherMap;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;


public class Neo4JTest {

    private OpenWeatherMap openWeatherMap = new OpenWeatherMap();
    private neo4jAPI neo4jAPI = new neo4jAPI(openWeatherMap);

    @Test
    public void getLatitudeAndLongitude() {
        double[] coords = neo4jAPI.getLatitudeAndLongitude("Ostkreuz");
        System.out.println("Latitude: " + coords[0] + " Longitude: " + coords[1]);
        assertEquals(52.503056, coords[0], 0);
        assertEquals(13.468889, coords[1], 0);
    }

    @Test
    public void getStations() {
        Map<Integer,String> stationsAndIds = new HashMap<Integer,String>();
        stationsAndIds = neo4jAPI.getStations();
        System.out.println(stationsAndIds);

    }
}
