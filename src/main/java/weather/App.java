package weather;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import net.aksingh.owmjapis.api.APIException;

public class App {

    public static void main(String[] args) throws APIException {
    	
    	
        // neo4j and OpenWeatherMaps
    	owmAPI owm = new owmAPI();
        neo4jAPI neo4j = new neo4jAPI(owm);
        
        
        
        //placeholders for start and Endstations 
        String startStation = null;
    	String endStation = null;
    	String passengerT = "";
        
    	//Map of Stations and Ids. User selects stations through their ID
    	Map<Integer, String> stations = neo4j.getStations();
    	Map<Integer,String> passengerType = new HashMap<>();
    	passengerType.put(1, "Senior");
    	passengerType.put(2, "Family with stroller");
    	passengerType.put(3, "Luggage");
    	passengerType.put(4, "wheelchair");
    	passengerType.put(5, "no restriction");
    	
    	
         
        Scanner scanner = new Scanner(System.in);

        //endless loop for repreated input
        while(true) {
        	
        	
        	System.out.println("Type shutdown to shut down database");
        	System.out.println("Type route to calculate a route");
        	String s = scanner.nextLine();
        	
        	//break out of loop
        	if (s.equals("shutdown")) {
        		
        		break;
        		}
        	
        	
        	//where work is actually done
        	if(s.equals("route")) {
        		
        		stations.forEach((k,v)->System.out.println("Station ID: " + k +"            Station Name: " + v));
        		System.out.println("Please type ID of START station");
        		startStation = stations.get(scanner.nextInt());
        		System.out.println("Please type ID of END station");
        		endStation = stations.get(scanner.nextInt());
        		passengerType.forEach((k,v)->System.out.println("Passenger Type ID: " + k +"            Passenger Type: " + v));
        		System.out.println("Please enter Passenger Type");
        		passengerT = passengerType.get(scanner.nextInt());
        		
                
                neo4j.calculateRoute(startStation,endStation,passengerT);
        		
        		
        		
        		
        		}
        	
        	}
        
       neo4j.closeDriver();
       scanner.close();
    }
}
