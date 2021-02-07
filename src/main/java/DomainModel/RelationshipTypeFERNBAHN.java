package DomainModel;

import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type="FERNBAHN")
public class RelationshipTypeFERNBAHN {
	@Id @GeneratedValue
	Long id;
	
	String distanz;
	String fahrzeit;
	
	@StartNode
	Station startStation;
	@EndNode
	Station endStation;

}
