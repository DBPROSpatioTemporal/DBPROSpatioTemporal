package appEntry;

import java.util.HashMap; 
import java.util.Map;
import java.util.Scanner;

import org.neo4j.driver.exceptions.ClientException;

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
    	int passengerT;
    	boolean done = false;

    	Map<Integer, String> stations = neo4j.getStations();
    	Map<Integer,String> passengerType = new HashMap<>();
    	passengerType.put(1, "Family with stroller");
    	passengerType.put(2, "Luggage");
    	passengerType.put(3, "wheelchair");
    	passengerType.put(4, "Family with small kids");
    	passengerType.put(5, "no restriction");
    	passengerType.put(6, "DONE");
    	
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
        		User user = new User();
        		System.out.println("Please enter your age");
        		int age = scanner.nextInt();
        		user.setAge(age);
        		stations.forEach((k,v)->System.out.println("Station ID: " + k +"            Station Name: " + v));
        		System.out.println("Please type ID of START station");
        		startStation = stations.get(scanner.nextInt());
        		System.out.println("Please type ID of END station");
        		endStation = stations.get(scanner.nextInt());
        		
        		while (true) {
        			System.out.println("Please select all attributes that apply to you. When done select DONE:");
        			passengerType.forEach((k,v)->System.out.println("Selection: " + k +"-------- " + v));
            		System.out.println("Please enter Selection");
            		passengerT = scanner.nextInt();
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
            			user.setHasChildren(true);
            			break;
            		case 5:
            			user.setHasChildren(false);
            			user.setHasLuggage(false);
            			user.setHasStroller(false);
            			user.setHasWheelchair(false);
            			break;
            		case 6:
            			done = true;
            			break;
            		}
            		if(done)
            			
            			break;
        		}
        		//System.out.println("Created user:\n"+ user.toString());
        		System.out.println("Start Station: " + startStation +"\nEnd Station: " + endStation);
        		String result ="";
        		try {
        		done = false;
        		result = neo4j.calculateRoute(startStation, endStation, user);
        		}
        		catch(ClientException e) {
        			result = "Start or End Station isnt wheelchair accesible!";
        		}
        		System.out.println(result);
        	}
        }
        
       System.out.println("shuting down...");
       scanner.close();
       return;
    }
}
