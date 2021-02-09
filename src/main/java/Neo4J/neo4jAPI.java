package Neo4J;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.neo4j.ogm.session.Session;

import DomainModel.Station;
import OpenWeatherMap.OpenWeatherMap;
import OpenWeatherMap.WeatherInfo.WeatherInfo;
import User.User;
import UserWeatherEvaluation.UserWeatherEvaluation;



 /*
               Connecting to local neo4j server that is running on port 7687 and querying data from it.
               1. Create a NEW local server and run it
               2. Connect to it via the browser via http://localhost:7474/browser/
               3. Since it's the first running, put in the username (default is "neo4j") and create a new password (make it "password")
               4. Our Server is empty. Here we test with the movie DB. So create the default movie DB. The query is provided to yo by the server.
               5. After creating the the nodes/relations your DB is no longer empty. You can run this App and you should get results back
            */

public class neo4jAPI {
	
  /**
   * use sessionFactory to create Sessions
   */
    private Neo4jSessionFactory sessionFactory = Neo4jSessionFactory.getInstance();
    private OpenWeatherMap owmAPI = new OpenWeatherMap();

     /**
     * Helper function to present user with all stations
     * @return
     */
    public Map<Integer,String> getStations() {
    	
    	 Session session = sessionFactory.getNeo4jSession();
    	 Map<Integer,String> stationsAndIds = new HashMap<Integer,String>();
		 Set<Station> stations = new HashSet<Station>();
		 String query = "Match (n) return n;";
		 session.query(query,Collections.emptyMap()).forEach(x->stations.add((Station) x.get("n")));
	        	
	        	int id= 0;
	        	for(Station s : stations) {
	        		stationsAndIds.put(id, s.getName());
	        		id++;
	        	}
	        	return stationsAndIds;
	 }
    
    /**Either returns the first path in bestPaths for which all stations either have good weather conditions or have a roof
     * or returns null, indicating no path is suitable
     * 
     * @param bestPaths
     * @param user
     * @return
     * @throws NumberFormatException
     * @throws IOException
     */
    private Map<String,Object> updateWeatherOnBestPaths(Iterable<Map<String,Object>> bestPaths, User user) throws NumberFormatException, IOException{
    	
    	Iterator<Map<String, Object>> it = bestPaths.iterator();
    	Map<String,Object> bestPath= null;
    	while(it.hasNext()) {
    		Map<String, Object> result = it.next();
    		ArrayList<Station> stationsOnPath = (ArrayList<Station>) result.get("places");
    		boolean goodPath = true;
    		for(int i=0; i<stationsOnPath.size();i++){
    			
    			Station s = stationsOnPath.get(i);
    			WeatherInfo weatherS = owmAPI.requestWeather(s.getBreitengrad(), s.getLaengengrad()); 
    			UserWeatherEvaluation eval = new UserWeatherEvaluation(user, weatherS);
    			boolean goodWeather = eval.isCurrentWeatherGood();
    			if(!goodWeather && !(s.getÜberdacht().equals("yes"))) {
    					goodPath = false;
        				break;
    				}
    		}
    		
    		if(goodPath) {
    			bestPath = result;
    			break;
    			}
    		}
    	
    	return bestPath;
    	
    }
    
    /**
     * builds cypher query and sends to Neo4j where route calculation is done using Yen's K-Shortest Paths
     * Returns a data structure filled with Map<String,Object>. Each map represents a path from start to end station and contains cost information (cummulative travel time)
     * @param startStation
     * @param endStation
     * @param hasWheelChair
     * @return
     */
    private Iterable<Map<String,Object>> buildAndSendCypherQuery (String startStation, String endStation, boolean hasWheelChair) {
    	char singleQuotes = '\'';
    	char doubleQuotes = '"';
    	//TODO: make hasWheelChair have several options
    	String wheelChairQuery = "";
    	if(hasWheelChair)
    		wheelChairQuery = "WHERE  s.rollstuhl = "+doubleQuotes+ "yes" +doubleQuotes;
    	
    
    	
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
    	Iterable<Map<String,Object>> result = session.query(query,Collections.emptyMap());
    	return result;
    }
    
    private String formatBestPath(Map<String,Object> bestPath){
    	if(bestPath == null) {
    		String info = "There was no path found :(";
    		return info;
    	}
    	ArrayList<Station> stationsOnPath = (ArrayList<Station>) bestPath.get("places");
		String info = stationsOnPath.get(0).getName();
		Double[] costs = (Double[]) bestPath.get("costs");
		Double totalCost = (Double) bestPath.get("totalCost");
		for(int i =1; i<stationsOnPath.size();i++) {
			String builder = "\n     |\n" +
							"   " + costs[i-1] + "\n" +
							 stationsOnPath.get(i).getName()+ "\n" +
							"     v";
			info = info.concat(builder);
		}
		
		String totalCostString ="\nTotal Time Traveled: " + totalCost;
		info = info.concat(totalCostString);
		return info;
		
    	
    }
    
    /**
     * acts as interface to rest of class
     * @param startStation
     * @param endStation
     * @param user
     */
    public String calculateRoute(String startStation, String endStation, User user) {
		
		Iterable<Map<String,Object>> bestPaths = buildAndSendCypherQuery(startStation,  endStation, user.hasWheelchair());
		Map<String,Object> bestPath= null;
		try {
		bestPath = updateWeatherOnBestPaths(bestPaths,user);
		} catch (NumberFormatException e) {
			
			e.printStackTrace();
			String info = "NumberFormat Exception in Weather API. Please try again";
			return info;
		} catch (IOException e) {
			
			e.printStackTrace();
			String info = "IO Exception in Weather API. Please try again";
			return info;
		}
		 return formatBestPath(bestPath);
		
		
		
	}
}
