package weather;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;

import java.util.List;
import java.util.stream.Collectors;


 /*
               Connecting to local neo4j server that is running on port 7687 and querying data from it.
               1. Create a NEW local server and run it
               2. Connect to it via the browser via http://localhost:7474/browser/
               3. Since it's the first running, put in the username (default is "neo4j") and create a new password (make it "password")
               4. Our Server is empty. Here we test with the movie DB. So create the default movie DB. The query is provided to yo by the server.
               5. After creating the the nodes/relations your DB is no longer empty. You can run this App and you should get results back
            */

public class neo4jAPI {

    private static final String URI_NEO4J = "neo4j://localhost:7687";
    private static final String DEFAULT_USERNAME = "neo4j";
    private static final String PASSWORD = "password";      // NOT SECURE!! needs to be more secure

    private Driver driver = null;

    public neo4jAPI() {
       this.driver = GraphDatabase.driver(URI_NEO4J, AuthTokens.basic(DEFAULT_USERNAME, PASSWORD));
    }


    // TODO: save current weather properties in neo4j
    public String setCurrentWeather(String weather) {

        return null;
    }

    // Here you get the Coordinates (Latitude and Longitude) form a specific Station in the neo4j Database
    // TODO: make into ONE Transaction (maybe as MAP with StationName and coords)

    public double[] getLatitudeAndLongitude(String stationName) {
        double[] geoCoordinates = new double[2];
        String cypherQueryLatitude = "MATCH (Station {name: '" + stationName + "'}) RETURN Station.breitengrad AS Lat";
        String cypherQueryLongitude = "MATCH (Station {name: '" + stationName + "'}) RETURN Station.laengengrad AS Long";

        try (Session session = driver.session()) {
            List<String> Latitude = session.readTransaction(tx -> {
                return tx.run(cypherQueryLatitude).stream()           // Here is the Latitude Transaction Query
                        .map(record -> record.get("Lat").asString())
                        .collect(Collectors.toList());
            });

            List<String> Longitude = session.readTransaction(tx -> {
                return tx.run(cypherQueryLongitude).stream()           // Here is the Longitude Transaction Query
                        .map(record -> record.get("Long").asString())
                        .collect(Collectors.toList());


            });

            geoCoordinates[0] = Double.parseDouble(Latitude.get(0));
            geoCoordinates[1] = Double.parseDouble(Longitude.get(0));
            printGeoCoordinates(stationName, geoCoordinates);
            return geoCoordinates;
        }


    }

    // Printing the geolocation Coordinates for the requested Station
    private void printGeoCoordinates(String stationName, double[] geoCoordinates) {
        System.out.println(
                "The coordinates for " + stationName + " are: \n" +
                "Latitude: " + geoCoordinates[0] + "\n" +
                "Longitude: " + geoCoordinates[1] + "\n"
        );
    }

    public void closeDriver() {
        driver.close();
    }
}
