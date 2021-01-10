package balint.test;



/*
 * Licensed to Neo4j under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Neo4j licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */


import java.io.IOException;

import java.util.Map;
import java.util.Scanner;


import database.EmbeddedDb;


public class EmbeddedNeo4j

{
	
   
    public static void main( final String[] args ) throws IOException
    
    {
    	String startStation = null;
    	String endStation = null;
        EmbeddedDb db = new EmbeddedDb();
        db.createDb();
        db.addData();
        Map<Integer, String> stations = db.getStations();
        
        Scanner scanner = new Scanner(System.in);
       
        while(true) {
        	
        	System.out.println("Type shutdown to shut down database");
        	System.out.println("Type route to calculate a route");
        	String s = scanner.nextLine();
        	if (s.equals("shutdown")) {
        		
        		break;
        		}
        	if(s.equals("route")) {
        		
        		stations.forEach((k,v)->System.out.println("Station ID: " + k +"            Station Name: " + v));
        		System.out.println("Please type ID of START station");
        		startStation = stations.get(scanner.nextInt());
        		System.out.println("Please type ID of END station");
        		endStation = stations.get(scanner.nextInt());
        		
        		db.calculateRoute(startStation,endStation);
        		
        		}
        	
        	}
        
        scanner.close();
        db.removeData();
        db.shutDown();
    }

   
    
}