import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import OpenWeatherMap.OpenWeatherMap;
import User.User;
import net.aksingh.owmjapis.api.APIException;
import Neo4J.neo4jAPI;
/**
 * class functions as user interface
 *
 */
public class App {

    public static void main(String[] args) throws APIException {
    	
    	neo4jAPI neo4j = new neo4jAPI(); 
        String startStation = null;
    	String endStation = null;
    	String passengerT = "";

    	Map<Integer, String> stations = neo4j.getStations();
    	Map<Integer,String> passengerType = new HashMap<>();
    	passengerType.put(1, "Family with stroller");
    	passengerType.put(2, "Luggage");
    	passengerType.put(3, "wheelchair");
    	passengerType.put(1, "Family with small kids");
    	passengerType.put(4, "no restriction");
    	
    	Scanner scanner = new Scanner(System.in);
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
        		User user = new User();
        		while (true) {
        			System.out.println("Please select all attributes that apply to you. When done type DONE:");
        			passengerType.forEach((k,v)->System.out.println("Passenger Type ID: " + k +"            Passenger Type: " + v));
            		System.out.println("Please enter Passenger Type or type DONE");
            		passengerT = scanner.nextInt());
            		switch(passengerT) {
            		case 1:
            			user.setHasStroller(true);
            			break;
            		case 2:
            			user.setHasLuggage(true);
            			break;
            		case 3:
            			user.setHasWheelchair(true);
            			break;
            		case 4:
            			user.setHasChildren(false);
            			user.setHasLuggage(false);
            			user.setHasStroller(false);
            			user.setHasWheelchair(false);
            			break;
            		}	
        		}
        	}
        }
        
       neo4j.closeDriver();
       scanner.close();
    }
}
