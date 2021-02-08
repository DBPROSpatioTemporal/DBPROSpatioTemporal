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

	public long getAktuellesWetter() {
		return aktuellesWetter;
	}

	public void setAktuellesWetter(long aktuellesWetter) {
		this.aktuellesWetter = aktuellesWetter;
	}

	public double getBreitengrad() {
		return breitengrad;
	}

	public void setBreitengrad(double breitengrad) {
		this.breitengrad = breitengrad;
	}

	public double getLaengengrad() {
		return laengengrad;
	}

	public void setLaengengrad(double laengengrad) {
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

	public long getZukünftigesWetter() {
		return zukuenftigesWetter;
	}

	public void setZukünftigesWetter(long zukünftigesWetter) {
		this.zukuenftigesWetter = zukünftigesWetter;
	}

	public String getÜberdacht() {
		return ueberdacht;
	}

	public void setÜberdacht(String überdacht) {
		this.ueberdacht = überdacht;
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

	long aktuellesWetter;
	double breitengrad;
	double laengengrad;
	String name;
	String rollstuhl;
	long zukuenftigesWetter;
	String ueberdacht;
	
	@Relationship(type="UBAHN", direction=Relationship.UNDIRECTED)
	private List<Station> ubahnVerbindungen;
	
	@Relationship(type="FERNBAHN", direction=Relationship.UNDIRECTED)
	private List<Station> fernbahnVerbindungen;
	
	@Relationship(type="SBAHN", direction=Relationship.UNDIRECTED)
	private List<Station> sbahnVerbindungen;
	
	public Station() {
		
	}
	
	

}
