import net.aksingh.owmjapis.api.APIException;
import net.aksingh.owmjapis.core.OWM;
import net.aksingh.owmjapis.model.CurrentWeather;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;

import java.util.List;
import java.util.stream.Collectors;

public class App {

    /*
            Connecting to local neo4j server that is running on port 7687 and querying data from it.
            1. Create a NEW local server and run it
            2. Connect to it via the browser via http://localhost:7474/browser/
            3. Since it's the first running, put in the username (default is "neo4j") and create a new password (make it "password")
            4. Our Server is empty. Here we test with the movie DB. So create the default movie DB. The query is provided to yo by the server.
            5. After creating the the nodes/relations your DB is no longer empty. You can run this App and you should get results back
         */
    private static void neo4jTest() {
        Driver driver = GraphDatabase.driver("neo4j://localhost:7687", AuthTokens.basic("neo4j", "password"));

        try (Session session = driver.session()) {
            List<String> movies = session.readTransaction(tx -> {
                return tx.run("MATCH (n:Movie) return n.title as movieTitle").stream()
                        .map(record -> record.get("movieTitle").asString())
                        .collect(Collectors.toList());
            });

            List<String> actors = session.readTransaction(tx -> {
                return tx.run("MATCH (n:Person) return n.name as actorName").stream()
                        .map(record -> record.get("actorName").asString())
                        .collect(Collectors.toList());
            });



            System.out.println(movies);
            System.out.println(actors);


        }
        driver.close();
    }



    /*
            OpenWeatherApi with my own Api Key.
            Implemented a dependency to use OpenWeatherApi in Java called OWM JAPIs https://github.com/iamashks/OWM-JAPIs
     */
    private static void owmTest() throws APIException{

        // declaring object of "OWM" class
        OWM owm = new OWM("81339d3bbeade1e0c69042233be22deb"); // My API Key; Other should be able to use it too
        // using the metric system
        owm.setUnit(OWM.Unit.METRIC);
        // getting current weather data for the "Berlin" city
        CurrentWeather cwd = owm.currentWeatherByCityName("Berlin");

        System.out.println(cwd.toString());
        System.out.println(cwd.hasRainData());

        // checking data retrieval was successful or not
        if (cwd.hasRespCode() && cwd.getRespCode() == 200) {

            // checking if city name is available
            if (cwd.hasCityName()) {
                //printing city name from the retrieved data
                System.out.println("City: " + cwd.getCityName());
            }

            // checking if max. temp. and min. temp. is available
            if (cwd.hasMainData() && cwd.getMainData().hasTempMax() && cwd.getMainData().hasTempMin()) {
                // printing the max./min. temperature
                System.out.println("Temperature: " + cwd.getMainData().getTempMax()
                        + "/" + cwd.getMainData().getTempMin() + "\'C");
            }


        }
    }


    public static void main(String[] args)  {

        // neo4jTest();
        try {
            owmTest();
        }catch (Exception ApiException) {

        }


    }
}
