import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;

import java.util.List;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] args) {

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
}
