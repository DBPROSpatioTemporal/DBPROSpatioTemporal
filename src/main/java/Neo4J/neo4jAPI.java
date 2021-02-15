package Neo4J;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.neo4j.ogm.session.Session;

import ConnectionTypes.Connections;
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
     * or returns null, indicating no path is suitable.
     * Uses sum of travel time to determine if current Weather (travel time under 1h) or forecast weather (travel time over 1 h) is relevant
     * 
     * @param bestPaths
     * @param user
     * @return
     * @throws NumberFormatException
     * @throws IOException
     */
    private Map<String,Object> updateWeatherOnBestPaths(Iterable<Map<String,Object>> bestPaths, User user) throws NumberFormatException, IOException{
    	/*The structure of the Map<String,Object> is as follows:
    	 * key: places          value: ArrayList<Station>
    	 * ArrayList represents the stations used in order
    	 * 
    	 * key: costs			value: Double[] 
    	 * Example: value[0] represents travel time from first station to second station
    	 *
    	 * key: totalCost       value: Double
    	 * value equals the sum of costs
    	 * 
    	 * Each entry in the Map represents a path made up of all stations, costs as travel time from one station to the next and a sum of total travel time
    	 */
    	Iterator<Map<String, Object>> it = bestPaths.iterator();
    	Map<String,Object> bestPath= null;
    	
    	while(it.hasNext()) {
    		Map<String, Object> result = it.next();
    		ArrayList<Station> stationsOnPath = (ArrayList<Station>) result.get("places");
    		Double[] costs = (Double[]) result.get("costs");
    		//size miss match between stations and costs. 
    		//To overcome this and make it easier to use in same for loop we create a Container with a zero as a first entry
    		ArrayDeque<Double> queue = new ArrayDeque<Double>();
    		for(Double cost: costs) {
    			queue.add(cost);
    		}
    		queue.addFirst(0.0);
    		boolean goodPath = true;
    		//sum is used to determine if Forecast weather or current weather should be used
    		double travelTimeSum = 0;
    		/*		Iterates through each station on a path
    		 * 		Generates a WeatherInfo Object based on geocoordinates of a station and a User Object
    		 * 		This WeatherInfo Object is used to create a UserWeatherEvaluation Object eval
    		 * 		eval holds the logic for determining for a given User if weather is good or not 		
    		 */
    		for(int i=0; i<stationsOnPath.size();i++){
    			travelTimeSum+=queue.pollFirst();
    			Station s = stationsOnPath.get(i);
    			WeatherInfo weatherS = owmAPI.requestWeather(s.getBreitengrad(), s.getLaengengrad()); 
    			UserWeatherEvaluation eval = new UserWeatherEvaluation(user, weatherS);
    			int hoursTraveled = (int) (travelTimeSum / 60);
    			boolean goodWeather = true;
    			if(hoursTraveled == 0)
    				 goodWeather = eval.isCurrentWeatherGood();
    			else
    				goodWeather = eval.isForecastWeatherGood(hoursTraveled);
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
     * @param user
	 * @param con
     * @return
     */
    private Iterable<Map<String,Object>> buildAndSendCypherQuery (String startStation, String endStation, User user,Connections con) {
    	char singleQuotes = '\'';
    	char doubleQuotes = '"';
    	String wheelchairConditions = "";
    	String coveredConditions="";
    	/*
    	 * Depending on properties of the User, the User will have different needs regarding station attributes
    	 * The mapping of User attributes to necessary station attributes can be found as a table our final paper.
    	 * The if-clauses are ordered form least restrictive to most restrictive and build on each other
    	 * 
    	 * As a senior you have the tightest restrictions regarding the covered attribute of a station (MUST be entirely covered)
    	 * As a wheel chair User the station must be entirely wheel chair accessible ("s.rollstuhl="yes")
    	 */
    	if(user.hasLuggage()) {
    		coveredConditions= " NOT s.ueberdacht = "+ doubleQuotes +"no" +doubleQuotes;
    	}
    	if(user.hasStroller()) {
    		wheelchairConditions = " NOT s.rollstuhl = "+doubleQuotes+ "no" +doubleQuotes;
    		coveredConditions    = " NOT s.ueberdacht = "+ doubleQuotes +"no" +doubleQuotes;
    	}
    	
    	if(user.getAge()>64) {
    		coveredConditions =" s.ueberdacht = "+ doubleQuotes +"yes" +doubleQuotes;
    		
    	}
    	
    	if(user.hasWheelchair()) {
    		wheelchairConditions= " s.rollstuhl = "+doubleQuotes+ "yes" +doubleQuotes;
    	}
    	
    	String stationConditions = "";
    	if(!wheelchairConditions.isBlank() && !coveredConditions.isBlank()) {
    		stationConditions+=" WHERE" +wheelchairConditions+ " AND" +coveredConditions;
    	}
    	if(!wheelchairConditions.isBlank() && coveredConditions.isBlank()) {
    		stationConditions+=" WHERE" +wheelchairConditions;
    	}
    	if(wheelchairConditions.isBlank() && !coveredConditions.isBlank()) {
    		stationConditions+=" WHERE" +coveredConditions;
    	}
    	
    		
    	
    	/*Depending on input from User on what connections he/she wants to use, an appropriate connectionConditions String is built
    	 */
    	String connectionConditions = con.generateConnectionConditions();
    
    	
    	String query =   
    			"MATCH (start:Station{name: " + singleQuotes + startStation + singleQuotes + "}), (end:Station{name: "+singleQuotes+endStation+singleQuotes+"})"+
    			"CALL gds.alpha.kShortestPaths.stream({" +
    			  "nodeQuery: "+singleQuotes+ "MATCH (s:Station) "+ stationConditions+" RETURN id(s) as id"+singleQuotes+","+
    			  "relationshipQuery: "+singleQuotes+ "MATCH (s: Station)-[r:"+ connectionConditions +"]-(t: Station) RETURN id(s) as source, id(t) as target, r.fahrzeit as cost"+singleQuotes+","+
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
     * @param con 
     */
    public String calculateRoute(String startStation, String endStation, User user, Connections con) {
		
		Iterable<Map<String,Object>> bestPaths = buildAndSendCypherQuery(startStation,  endStation, user, con);
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
