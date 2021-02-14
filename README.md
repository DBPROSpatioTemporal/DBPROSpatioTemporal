# DBPRO_SpatioTemporal
## Starting Database:
1. Download Neo4j Desktop: https://neo4j.com/download-center/#desktop
2. Open Neo4j desktop, naviagate into the **projects** folder.
3. Add the **APOC** and **Graph Data Science Library** plug ins. (APOC is needed to import the .graphml data. Graph Data Science Library holds Yen's k shortest paths algorithm)
4. Click *Add Database*, select *create a local database* and give an arbitrary password and create the database. Authentication will be removed later for ease of use

## Applying settings and preparing import of data
Once Startin Database has been completed the database has been set up. Now to prepare it use\
1. Click on the 3 dots in the top right corner of the card representing your newly running database
2. Select manage and select *Settings*
3. **Set** `dbms.security.auth_enabled=true` **to false**.
4. Add the following settings\
`dbms.connector.bolt.enabled=true`
`apoc.import.file.enabled=true`
`apoc.export.file.enabled=true`
5. Above *Settings* click on the downwards arrow next to *Open Folder*
6. Select *Import*.This will open part of the filesystem Neo4j uses on your computer.
7. Within the now opened folder, place `complete_database.graphml` found in this repo.
8. Close folder and click *Start Database*

## Importing Data into running Neo4j database
1. Once the database is running navigate to *Graph Apps* and select *Neo4j Browser*
2. This will open the Neo4j browser, allowing one to input Cypher queries!
3. To import the data execute this cypher query: `CALL apoc.import.graphml("complete_database.graphml", {readLabels: true})`\

**And thats it! There should now be a locally running Neo4j instance with our data running on your system. Time to interact with it using our application!**

## Starting application
First, make sure that the Neo4j database is up and running\
The main method is located in package `appEntry`


