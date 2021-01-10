# DBPRO_SpatioTemporal

How to turn .graphml file into cypher script:

1. Put .graphml into import folder of Ne04j Desktop database
  - Open Neo4j Desktop and click on Projects overview
  - start a new database or use already existing one
  - click on three dots in top right corner and select manage
  - Go on plugisn and download APOC
  - Click on settings and add following lines
        apoc.import.file.enabled=true
        apoc.export.file.enabled=true
  - Restart DB if neccessary/if asked
  - Run following procedure to load .graphml file into db
        CALL apoc.import.graphml("complete-graph.graphml", {})
  -To export graph as cypher queries run
        CALL apoc.export.cypher.all("cypher.txt", {})
        
        
ToDo:
   move to Neo4j OGM instead of hard coding with cypher queries
