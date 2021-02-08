package tests;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.neo4j.ogm.session.Session;
import Neo4J.Neo4jSessionFactory;
import DomainModel.Station;


public class SessionFactoryTest {
	
	Neo4jSessionFactory sessionFactory = Neo4jSessionFactory.getInstance();
 
    @Test
    public void sendComplexCypher() {
    	String startStation ="Neukoelln";
    	String endStation = "Ostkreuz";
    	char singleQuotes = '\'';
    	char doubleQuotes = '"';
    	
    	int i = 0;
    	String wheelChairQuery = "";
    	switch (i) {
    	case 0: 
    		wheelChairQuery = "WHERE  s.rollstuhl = "+doubleQuotes+ "yes" +doubleQuotes;
    	}
    	
    	String query =   
    			"MATCH (start:Station{name: " + singleQuotes + startStation + singleQuotes + "}), (end:Station{name: "+singleQuotes+endStation+singleQuotes+"})"+
    			"CALL gds.alpha.kShortestPaths.stream({" +
    			  "nodeQuery: "+singleQuotes+ "MATCH (s:Station) "+ wheelChairQuery+" RETURN id(s) as id"+singleQuotes+","+
    			  "relationshipQuery: "+singleQuotes+ "MATCH (s: Station)-[r:FERNBAHN|SBAHN|UBAHN]-(t: Station) RETURN id(s) as source, id(t) as target, r.fahrzeit as cost"+singleQuotes+","+
    			  "startNode: start,"+
    			  "endNode: end,"+
    			  "k: 10,"+
    			  "relationshipWeightProperty: "+singleQuotes+"cost"+singleQuotes+","+
    			  "validateRelationships: false"+
    			"})"+
    			"YIELD index, nodeIds, costs "+
    			"RETURN [node IN gds.util.asNodes(nodeIds) | node] AS places,"+
    			       "costs,"+
    			       "reduce(acc = 0.0, cost IN costs | acc + cost) AS totalCost";
    	Session session = sessionFactory.getNeo4jSession();
    	Iterable<Map<String,Object>> results = session.query(query,Collections.emptyMap());
    	Iterator<Map<String, Object>> it = results.iterator();
    	Map<String,Object> bestPath= null;
    	while(it.hasNext()) {
    		Map<String, Object> result = it.next();
    		ArrayList<Station> stationsOnPath = (ArrayList<Station>) result.get("places");
    		Double[] costs = (Double[]) result.get("costs");
    		System.out.println(costs[0]);
    		
    		
    	}
    	
    	System.out.println("done");
    }


}
