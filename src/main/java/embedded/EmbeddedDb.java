package embedded;

import static org.neo4j.configuration.GraphDatabaseSettings.DEFAULT_DATABASE_NAME;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import OpenWeatherMap.OpenWeatherMap;
import org.neo4j.configuration.connectors.BoltConnector;
import org.neo4j.configuration.helpers.SocketAddress;
import org.neo4j.dbms.api.DatabaseManagementService;
import org.neo4j.dbms.api.DatabaseManagementServiceBuilder;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.neo4j.io.fs.FileUtils;

public class EmbeddedDb {
	
	
	 private static final Path databaseDirectory = Path.of( "target/neo4j-hello-db" );
	 
	 private GraphDatabaseService graphDb;
	 private DatabaseManagementService managementService;
	 
	 private OpenWeatherMap weatherAPI = new OpenWeatherMap();
	 
	 
	 public GraphDatabaseService getDb() {
		 return this.graphDb;
	 }
	 
	 public DatabaseManagementService getManagementService() {
		 return this.managementService;
	 }
	 
	 private void updateWeather() {
		 System.out.print("updating Weather");
//		 for()
//		 weatherAPI.requestWeather(Location);
	 }
	 
	 public Map<Integer,String> getStations() {
		 
		 Map<Integer,String> stations = new HashMap<Integer,String>();
		 
		 Set<String> interm = new HashSet<String>();

	        try ( Transaction tx = this.graphDb.beginTx() ){
	        	
	        	Result result = tx.execute("Match (n) return n.name;");
	        	
	        	
	        	while(result.hasNext()) {
	        		
	        		Map<String, Object> row = result.next();
	        		
	        		
	        		row.forEach((k,v)->  interm.add(v.toString()));
	        			
	        		}
	             }
	        
	          
		 
	        	
	        	int id= 0;
	        	for(String s : interm) {
	        		//System.out.println(s);
	        		stations.put(id, s);
	        		id++;
	        	}
	        	
	        	//stations.forEach((k,v)-> System.out.println("id: " +k +"; value: "+v));
	        	
	        	
	        	
	        	return stations;
		
		 
	 }
	 
	    
	 public void createDb() throws IOException
	    {
	        FileUtils.deleteDirectory( databaseDirectory );

	        // tag::startDb[]
	       managementService = new DatabaseManagementServiceBuilder( databaseDirectory )
	        		.setConfig( BoltConnector.enabled, true )
	                .setConfig( BoltConnector.listen_address, new SocketAddress( "localhost", 7687 ) )
	                .build();
	        graphDb = managementService.database( DEFAULT_DATABASE_NAME );
	        registerShutdownHook( managementService );
	      
	    }
	 
	 public void addData() {
	        //fills database with data.
	      
	        try ( Transaction tx = this.graphDb.beginTx() )
	        {
	            tx.execute("CREATE CONSTRAINT ON (node:`UNIQUE IMPORT LABEL`) ASSERT (node.`UNIQUE IMPORT ID`) IS UNIQUE;");
	            tx.commit();
	        }
	        graphDb.executeTransactionally("UNWIND [{_id:0, properties:{überdacht:\"true\", s_bahn_gleise:\"2\", fernbahn_gleise:\"9\", rollstuhl:\"yes\", name:\"Köln Hauptbahnhof\", labels:\":Station\"}}, {_id:1, properties:{überdacht:\"true\", s_bahn_gleise:\"4\", fernbahn_gleise:\"8\", rollstuhl:\"yes\", name:\"Hamburg Hauptbahnhof\", labels:\":Station\"}}, {_id:2, properties:{überdacht:\"true\", s_bahn_gleise:\"2\", fernbahn_gleise:\"10\", rollstuhl:\"yes\", name:\"Hannover Hauptbahnhof\", labels:\":Station\"}}, {_id:3, properties:{überdacht:\"true\", s_bahn_gleise:\"4\", fernbahn_gleise:\"2\", rollstuhl:\"yes\", name:\"Berlin-Westkreuz\", labels:\":Station\"}}, {_id:4, properties:{überdacht:\"true\", s_bahn_gleise:\"4\", fernbahn_gleise:\"8\", rollstuhl:\"limited\", name:\"Hamburg Altona\", labels:\":Station\"}}, {_id:5, properties:{überdacht:\"true\", s_bahn_gleise:\"4\", fernbahn_gleise:\"6\", name:\"Gesundbrunnen\", rollstuhl:\"yes\", labels:\":Station\"}}, {_id:6, properties:{überdacht:\"true\", s_bahn_gleise:\"2\", fernbahn_gleise:\"32\", rollstuhl:\"yes\", name:\"München Hauptbahnhof\", labels:\":Station\"}}, {_id:7, properties:{überdacht:\"true\", s_bahn_gleise:\"4\", fernbahn_gleise:\"18\", rollstuhl:\"yes\", name:\"Nürnberg Hauptbahnhof\", labels:\":Station\"}}, {_id:8, properties:{überdacht:\"no\", s_bahn_gleise:\"2\", fernbahn_gleise:\"4\", name:\"Berlin-Spandau\", rollstuhl:\"yes\", labels:\":Station\"}}, {_id:9, properties:{überdacht:\"true\", s_bahn_gleise:\"4\", fernbahn_gleise:\"6\", rollstuhl:\"yes\", name:\"Berlin-Südkreuz\", labels:\":Station\"}}, {_id:10, properties:{überdacht:\"true\", s_bahn_gleise:\"2\", fernbahn_gleise:\"4\", rollstuhl:\"yes\", name:\"Potsdam Hauptbahnhof\", labels:\":Station\"}}, {_id:11, properties:{überdacht:\"true\", s_bahn_gleise:\"4\", fernbahn_gleise:\"12\", rollstuhl:\"yes\", name:\"Berlin Hauptbahnhof\", u_bahn_gleise:\"2\", labels:\":Station\"}}, {_id:12, properties:{überdacht:\"limited\", s_bahn_gleise:\"2\", name:\"Neukölln\", rollstuhl:\"no\", labels:\":Station\"}}, {_id:13, properties:{überdacht:\"true\", s_bahn_gleise:\"6\", fernbahn_gleise:\"6\", name:\"Ostkreuz\", rollstuhl:\"yes\", labels:\":Station\"}}, {_id:14, properties:{überdacht:\"true\", s_bahn_gleise:\"4\", fernbahn_gleise:\"4\", rollstuhl:\"yes\", name:\"Friedrichsstraße\", u_bahn_gleise:\"2\", labels:\":Station\"}}] AS row\r\n"
	        		+ "CREATE (n:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`: row._id}) SET n += row.properties;");
	        graphDb.executeTransactionally("UNWIND [{start: {_id:8}, end: {_id:3}, properties:{distanz:10, label:\"SBAHN\"}}, {start: {_id:9}, end: {_id:11}, properties:{distanz:8, label:\"SBAHN\"}}, {start: {_id:3}, end: {_id:11}, properties:{distanz:4, label:\"SBAHN\"}}, {start: {_id:10}, end: {_id:3}, properties:{distanz:30, label:\"SBAHN\"}}, {start: {_id:4}, end: {_id:1}, properties:{distanz:6, label:\"SBAHN\"}}, {start: {_id:5}, end: {_id:14}, properties:{distanz:5, label:\"SBAHN\"}}, {start: {_id:12}, end: {_id:9}, properties:{distanz:4, label:\"SBAHN\"}}, {start: {_id:9}, end: {_id:3}, properties:{distanz:10, label:\"SBAHN\"}}, {start: {_id:14}, end: {_id:13}, properties:{distanz:5, label:\"SBAHN\"}}, {start: {_id:14}, end: {_id:11}, properties:{distanz:2, label:\"SBAHN\"}}, {start: {_id:9}, end: {_id:14}, properties:{distanz:7, label:\"SBAHN\"}}, {start: {_id:5}, end: {_id:13}, properties:{distanz:7, label:\"SBAHN\"}}, {start: {_id:12}, end: {_id:13}, properties:{distanz:4, label:\"SBAHN\"}}, {start: {_id:3}, end: {_id:5}, properties:{distanz:7, label:\"SBAHN\"}}] AS row\r\n"
	        		+ "MATCH (start:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`: row.start._id})\r\n"
	        		+ "MATCH (end:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`: row.end._id})\r\n"
	        		+ "CREATE (start)-[r:SBAHN]->(end) SET r += row.properties;\r\n");
	        	
	        graphDb.executeTransactionally("UNWIND [{start: {_id:8}, end: {_id:12}, properties:{distanz:20, label:\"UBAHN\"}}] AS row\r\n"
	        		+ "MATCH (start:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`: row.start._id})\r\n"
	        		+ "MATCH (end:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`: row.end._id})\r\n"
	        		+ "CREATE (start)-[r:UBAHN]->(end) SET r += row.properties;\r\n");
	        graphDb.executeTransactionally("UNWIND [{start: {_id:0}, end: {_id:1}, properties:{distanz:357, label:\"FERNBAHN\"}}, {start: {_id:0}, end: {_id:2}, properties:{distanz:249, label:\"FERNBAHN\"}}, {start: {_id:0}, end: {_id:7}, properties:{distanz:337, label:\"FERNBAHN\"}}, {start: {_id:7}, end: {_id:9}, properties:{distanz:438, label:\"FERNBAHN\"}}, {start: {_id:2}, end: {_id:8}, properties:{distanz:235, label:\"FERNBAHN\"}}, {start: {_id:8}, end: {_id:11}, properties:{distanz:13, label:\"FERNBAHN\"}}, {start: {_id:1}, end: {_id:2}, properties:{distanz:151, label:\"FERNBAHN\"}}, {start: {_id:9}, end: {_id:11}, properties:{distanz:8, label:\"FERNBAHN\"}}, {start: {_id:4}, end: {_id:1}, properties:{distanz:6, label:\"FERNBAHN\"}}, {start: {_id:1}, end: {_id:8}, properties:{distanz:278, label:\"FERNBAHN\"}}, {start: {_id:6}, end: {_id:7}, properties:{distanz:173, label:\"FERNBAHN\"}}, {start: {_id:7}, end: {_id:2}, properties:{distanz:472, label:\"FERNBAHN\"}}, {start: {_id:10}, end: {_id:11}, properties:{distanz:34, label:\"FERNBAHN\"}}, {start: {_id:14}, end: {_id:13}, properties:{distanz:5, label:\"FERNBAHN\"}}, {start: {_id:14}, end: {_id:11}, properties:{distanz:2, label:\"FERNBAHN\"}}, {start: {_id:9}, end: {_id:14}, properties:{distanz:7, label:\"FERNBAHN\"}}] AS row\r\n"
	        		+ "MATCH (start:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`: row.start._id})\r\n"
	        		+ "MATCH (end:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`: row.end._id})\r\n"
	        		+ "CREATE (start)-[r:FERNBAHN]->(end) SET r += row.properties;");
	        graphDb.executeTransactionally("MATCH (n:`UNIQUE IMPORT LABEL`)  WITH n LIMIT 20000 REMOVE n:`UNIQUE IMPORT LABEL` REMOVE n.`UNIQUE IMPORT ID`;");
	        graphDb.executeTransactionally("DROP CONSTRAINT ON (node:`UNIQUE IMPORT LABEL`) ASSERT (node.`UNIQUE IMPORT ID`) IS UNIQUE;");
	        
	        
	        
	    }
	    
	    

	    public void removeData()
	    {
	        try ( Transaction tx = this.graphDb.beginTx() )
	        {
	            // tag::removingData[]
	            // let's remove the data
	            tx.execute("MATCH (n)-[r]->(m) delete n,r,m;");
	            tx.commit();
	        }
	    }
	    
	    
	    private static void registerShutdownHook( final DatabaseManagementService managementService )
	    {
	        // Registers a shutdown hook for the Neo4j instance so that it
	        // shuts down nicely when the VM exits (even if you "Ctrl-C" the
	        // running application).
	        Runtime.getRuntime().addShutdownHook( new Thread()
	        {
	            @Override
	            public void run()
	            {
	                managementService.shutdown();
	            }
	        } );
	    }
	    
	    public void shutDown()
	    {
	        System.out.println();
	        System.out.println( "Shutting down database ..." );
	        // tag::shutdownServer[]
	        managementService.shutdown();
	        // end::shutdownServer[]
	    }

		public void calculateRoute(String startStation, String endStation) {
			
			System.out.println("Calculating route. Beep Boop");
			System.out.println("Start station: " + startStation);
			System.out.println("End station: " + endStation);
			
			 try ( Transaction tx = this.graphDb.beginTx() )
		        {
				 	Map<String,Object> params = new HashMap<>();
				 	params.put( "startStation", startStation );
				 	params.put( "endStation", endStation );
				 	
				 	
		           String query = "MATCH (from:Station { name:\"$startStation\" }), (to:Station { name: \"$endStation\"}) , path = (from)-[:FERNBAHN*]->(to)\r\n"
		            		+ "RETURN path AS shortestPath,\r\n"
		            		+ "    reduce(distance = 0, r in relationships(path) | distance+r.distance) AS totalDistance\r\n"
		            		+ "    ORDER BY totalDistance ASC\r\n"
		            		+ "    LIMIT 1";
		           Result result = tx.execute(query, params);
		           System.out.println(result.toString());
		           
		        }
			 
			 updateWeather();
			
			
		}

}
