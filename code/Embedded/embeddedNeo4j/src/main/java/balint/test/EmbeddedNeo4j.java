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
import java.nio.file.Path;

import org.neo4j.configuration.connectors.BoltConnector;
import org.neo4j.configuration.helpers.SocketAddress;
import org.neo4j.dbms.api.DatabaseManagementService;
import org.neo4j.dbms.api.DatabaseManagementServiceBuilder;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.neo4j.io.fs.FileUtils;
import java.util.Scanner;

import static org.neo4j.configuration.GraphDatabaseSettings.DEFAULT_DATABASE_NAME;
 
public class EmbeddedNeo4j
{
    private static final Path databaseDirectory = Path.of( "target/neo4j-hello-db" );
    

    public String greeting;

    // tag::vars[]
    GraphDatabaseService graphDb;
    Node firstNode;
    Node secondNode;
    Relationship relationship;
    private DatabaseManagementService managementService;
    // end::vars[]

    // tag::createReltype[]
    private enum RelTypes implements RelationshipType
    {
        KNOWS
    }
    // end::createReltype[]

    public static void main( final String[] args ) throws IOException
    {
        EmbeddedNeo4j hello = new EmbeddedNeo4j();
        
        System.out.println(databaseDirectory.toAbsolutePath().toString());
        
        hello.createDb();
       
        while(true) {
        	Scanner scanner = new Scanner(System.in);
        	System.out.println("Shut down database Y/n?");
        	String s = scanner.nextLine();
        	if (s.equals("Y")) {
        		scanner.close();
        		break;
        		}
        	}
        hello.removeData();
        hello.shutDown();
    }

    void createDb() throws IOException
    {
        FileUtils.deleteDirectory( databaseDirectory );

        // tag::startDb[]
        managementService = new DatabaseManagementServiceBuilder( databaseDirectory )
        		.setConfig( BoltConnector.enabled, true )
                .setConfig( BoltConnector.listen_address, new SocketAddress( "localhost", 7687 ) )
                .build();
        graphDb = managementService.database( DEFAULT_DATABASE_NAME );
        registerShutdownHook( managementService );
        // end::startDb[]

        // tag::transaction[]
        try ( Transaction tx = graphDb.beginTx() )
        {
        	
        	//Result result = tx.execute( "LOAD CSV WITH HEADERS FROM \"file:////Users/My_User/csv_file.csv\" AS csvLine\n" );
            // Database operations go here
            // end::transaction[]
            // tag::addData[]
            tx.execute("CREATE CONSTRAINT ON (node:`UNIQUE IMPORT LABEL`) ASSERT (node.`UNIQUE IMPORT ID`) IS UNIQUE;");
            tx.commit();
        }
        graphDb.executeTransactionally("UNWIND [{_id:0, properties:{s_bahn_gleise:\"2\", fernbahn_gleise:\"9\", name:\"Köln Hauptbahnhof\", labels:\":Station\"}}, {_id:1, properties:{s_bahn_gleise:\"4\", fernbahn_gleise:\"8\", name:\"Hamburg Hauptbahnhof\", labels:\":Station\"}}, {_id:2, properties:{s_bahn_gleise:\"2\", fernbahn_gleise:\"10\", name:\"Hannover Hauptbahnhof\", labels:\":Station\"}}, {_id:3, properties:{s_bahn_gleise:\"4\", fernbahn_gleise:\"2\", name:\"Berlin-Westkreuz\", labels:\":Station\"}}, {_id:4, properties:{s_bahn_gleise:\"4\", fernbahn_gleise:\"8\", name:\"Hamburg Altona\", labels:\":Station\"}}, {_id:5, properties:{S_Bahn_Gleise:4, name:\"Gesundbrunnen\", Fernbahn_Gleise:6, labels:\":Station\"}}, {_id:6, properties:{s_bahn_gleise:\"2\", fernbahn_gleise:\"32\", name:\"München Hauptbahnhof\", labels:\":Station\"}}, {_id:7, properties:{s_bahn_gleise:\"4\", fernbahn_gleise:\"18\", name:\"Nürnberg Hauptbahnhof\", labels:\":Station\"}}, {_id:8, properties:{s_bahn_gleise:\"2\", fernbahn_gleise:\"4\", name:\"Berlin-Spandau\", labels:\":Station\"}}, {_id:9, properties:{s_bahn_gleise:\"4\", fernbahn_gleise:\"6\", name:\"Berlin-Südkreuz\", labels:\":Station\"}}, {_id:10, properties:{s_bahn_gleise:\"2\", fernbahn_gleise:\"4\", name:\"Potsdam Hauptbahnhof\", labels:\":Station\"}}, {_id:11, properties:{s_bahn_gleise:\"4\", fernbahn_gleise:\"12\", name:\"Berlin Hauptbahnhof\", u_bahn_gleise:\"2\", labels:\":Station\"}}, {_id:12, properties:{s_bahn_gleise:\"2\", name:\"Neukölln\", labels:\":Station\"}}, {_id:13, properties:{S_Bahn_Gleise:6, name:\"Ostkreuz\", Fernbahn_Gleise:6, labels:\":Station\"}}, {_id:14, properties:{S_Bahn_Gleise:4, U_Bahn_Gleise:2, name:\"Friedrichsstraße\", labels:\":Station\", Fernbahn_Gleise:4}}] AS row\r\n"
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
        // end::transaction[]
    }

    void removeData()
    {
        try ( Transaction tx = graphDb.beginTx() )
        {
            // tag::removingData[]
            // let's remove the data
            tx.execute("MATCH (n)-[r]->(m) delete n,r,m;");
            tx.commit();
        }
    }

    void shutDown()
    {
        System.out.println();
        System.out.println( "Shutting down database ..." );
        // tag::shutdownServer[]
        managementService.shutdown();
        // end::shutdownServer[]
    }

    // tag::shutdownHook[]
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
    // end::shutdownHook[]
}