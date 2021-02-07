package DomainModel;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
@RelationshipEntity(type="SBAHN")
public class RelationshipTypeSBAHN {
	
	@Id @GeneratedValue
	Long id;
	
	String distanz;
	String fahrzeit;
	
	@StartNode
	Station startStation;
	@EndNode
	Station endStation;

}
