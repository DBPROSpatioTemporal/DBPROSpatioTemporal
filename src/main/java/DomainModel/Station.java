package DomainModel;

import java.util.List;

import org.neo4j.ogm.annotation.*;


@NodeEntity
public class Station {
	
	@Id @GeneratedValue
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAktuellesWetter() {
		return aktuellesWetter;
	}

	public void setAktuellesWetter(String aktuellesWetter) {
		this.aktuellesWetter = aktuellesWetter;
	}

	public String getBreitengrad() {
		return breitengrad;
	}

	public void setBreitengrad(String breitengrad) {
		this.breitengrad = breitengrad;
	}

	public String getLaengengrad() {
		return laengengrad;
	}

	public void setLaengengrad(String laengengrad) {
		this.laengengrad = laengengrad;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRollstuhl() {
		return rollstuhl;
	}

	public void setRollstuhl(String rollstuhl) {
		this.rollstuhl = rollstuhl;
	}

	public String getZukünftigesWetter() {
		return zukünftigesWetter;
	}

	public void setZukünftigesWetter(String zukünftigesWetter) {
		this.zukünftigesWetter = zukünftigesWetter;
	}

	public String getÜberdacht() {
		return überdacht;
	}

	public void setÜberdacht(String überdacht) {
		this.überdacht = überdacht;
	}

	public List<Station> getUbahnVerbindungen() {
		return ubahnVerbindungen;
	}

	public void setUbahnVerbindungen(List<Station> ubahnVerbindungen) {
		this.ubahnVerbindungen = ubahnVerbindungen;
	}

	public List<Station> getFernbahnVerbindungen() {
		return fernbahnVerbindungen;
	}

	public void setFernbahnVerbindungen(List<Station> fernbahnVerbindungen) {
		this.fernbahnVerbindungen = fernbahnVerbindungen;
	}

	public List<Station> getSbahnVerbindungen() {
		return sbahnVerbindungen;
	}

	public void setSbahnVerbindungen(List<Station> sbahnVerbindungen) {
		this.sbahnVerbindungen = sbahnVerbindungen;
	}

	String aktuellesWetter;
	String breitengrad;
	String laengengrad;
	String name;
	String rollstuhl;
	String zukünftigesWetter;
	String überdacht;
	
	@Relationship(type="UBAHN", direction=Relationship.UNDIRECTED)
	private List<Station> ubahnVerbindungen;
	
	@Relationship(type="FERNBAHN", direction=Relationship.UNDIRECTED)
	private List<Station> fernbahnVerbindungen;
	
	@Relationship(type="SBAHN", direction=Relationship.UNDIRECTED)
	private List<Station> sbahnVerbindungen;
	
	public Station() {
		
	}
	
	

}
