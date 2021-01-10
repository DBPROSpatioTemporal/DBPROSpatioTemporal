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

    /* TODO: just a place holder. Make sure it is a MATCH request
        See what type of Queries there are so you can categorize them through String splitting
     */
    public List<String> request(String cypherQuery) {
        try (Session session = driver.session()) {
            List<String> stations = session.readTransaction(tx -> {
                return tx.run(cypherQuery).stream()           // Here is the Transaction Query
                        .map(record -> record.get("stationNames").asString())
                        .collect(Collectors.toList());
            });

            return stations;
        }

    }

    // TODO: same as other function but with CREATE(?)
    public String send(String cypherQuery) {
        return null;
    }

    public void closeDriver() {
        driver.close();
    }
}
