package Neo4J;
import OpenWeatherMap.OpenWeatherMap;
import OpenWeatherMap.WeatherInfo.WeatherInfo;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Query;
import org.neo4j.driver.Record;
import org.neo4j.driver.Session;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    private OpenWeatherMap owmAPI;

    private Driver driver = null;

    public neo4jAPI(OpenWeatherMap owmAPI) {
       this.driver = GraphDatabase.driver(URI_NEO4J, AuthTokens.basic(DEFAULT_USERNAME, PASSWORD));
       this.owmAPI = owmAPI;
    }


   /**
    * Function takes Station (represented as a String) and updates the weather status within neo4j for that station
    * @param station
    * @return
    */
    public void setCurrentWeather(String station) {
    	
		 // get the coords of the Station (stationName) from neo4j
        double[] stationCoords = getLatitudeAndLongitude(station);

        // Get the weather of the coords form OpenWeatherMaps and save results in weather.WeatherInfo Object
        WeatherInfo currentWeather;
		try {
			currentWeather = owmAPI.requestWeather(stationCoords[0], stationCoords[1]);
			 // print weather info
//	        System.out.println(currentWeather.getMainInfo());
//	        System.out.println(currentWeather.getMoreInfo());
//	        System.out.println(currentWeather.getTemperature());
//	        System.out.println(currentWeather.isWeatherGood());

	        // Get hourly Weather


	       //TODO:  Save current Weather in neo4j form the weather.WeatherInfo Object
	       //setCurrentWeather(currentWeather.getMainInfo());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        
    }
    
    /**
     * call function in weather API that gets predictive weather data
     * @param station
     */
    public void setFutureWeather(String station) {
    	
    	// upadte zukuenftigesWetter attribut to a one or zero
    	
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
    
    public Map<Integer,String> getStations() {
    	
    	
		 
		 Map<Integer,String> stationsAndIds = new HashMap<Integer,String>();
		 
		 Set<String> stations = new HashSet<String>();

		 try (Session session = driver.session()) {
	        	
	        	Query query = new Query("Match (n) return n.name as name;");
	        	//next().asMap().forEach((k,v)->  interm.add(v.toString()));
	        	
	        	session.run(query).list().forEach(x-> stations.add(x.get("name").asString()));;
	        	
	        	}
		 
		
	        
	          
		 
	        	
	        	int id= 0;
	        	for(String s : stations) {
	        		//System.out.println(s);
	        		stationsAndIds.put(id, s);
	        		id++;
	        	}
	        	
	        	//stationsAndIds.forEach((k,v)-> System.out.println("id: " +k +"; value: "+v));
	        	
	        	
	        	
	        	return stationsAndIds;
		
		 
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
    
    
    
    private void updateWeather (Map<Integer,String> stations){
    	
    	stations.forEach((k,v)-> {setCurrentWeather(v);
    							  setFutureWeather(v);
    		});
    	
    	
    	
    }
    
    private void outputPath (List<org.neo4j.driver.Record> list) {
    	double travelTime = 0.0;
    	
    	
    	list.forEach(x-> {System.out.print(x.get("name") +"-->");
    					
    					  });
    	
    	for(Record r : list){
    		travelTime += r.get("cost").asDouble();
    		
    	}
    	
    	System.out.println("\nTotal Travel Time: " + travelTime +" minutes");
    	}
    

	public void calculateRoute(String startStation, String endStation, String passengerT) {
		
		System.out.println(passengerT);
		//setCurrentWeather(startStation);
		//setCurrentWeather(endStation);
		
		System.out.println("Calculating route. Beep Boop");
//		System.out.println("Start station: " + startStation);
//		System.out.println("End station: " + endStation);
		
		//updateWeather(getStations());
		
		 try ( Session session = driver.session())
	        {
			 	
			 	
			 
	           String query1 = "MATCH (start:Station {name: '" + startStation+ "'}), (end:Station {name: '"+endStation+"'})\r\n"
	           		+ "CALL gds.alpha.shortestPath.stream({\r\n"
	           		+ "  nodeQuery: 'MATCH (s:Station) WHERE s.aktuellesWetter = 1 AND s.zukünftigesWetter = 1 RETURN id(s) as id',\r\n"
	           		+ "  relationshipQuery: 'MATCH (s: Station)-[r:FERNBAHN|SBAHN|UBAHN]-(t: Station) RETURN id(s) as source, id(t) as target, r.distanz as cost',\r\n"
	           		+ "     startNode: start,\r\n"
	           		+ "     endNode: end,\r\n"
	           		+ "    relationshipWeightProperty: 'cost',\r\n"
	           		+ "  validateRelationships: false\r\n"
	           		+ "  })\r\n"
	           		+ "  YIELD nodeId, cost\r\n"
	           		+ "   RETURN gds.util.asNode(nodeId).name AS name, cost";
	           
	           String query2 ="BREAK IT ALL!";
	           
	           String query3 = "Match (n) return n.name as name;";
	           
	           
	           List<org.neo4j.driver.Record> list =  session.run(query1).list();
	           System.out.println(list.size());
	           outputPath(list);
	           //System.out.println(result.toString());
	           
	        }
		


       

		
		// TODO Auto-generated method stub
		
	}
}
