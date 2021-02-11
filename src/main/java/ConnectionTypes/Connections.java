package ConnectionTypes;
/**
 * used as a holder for user input regarding connections he/she wants to use
 * @author Balint
 *
 */
public class Connections {
	
	public Connections(boolean sBAHN, boolean uBAHN, boolean fERNBAHN) {
		super();
		SBAHN = sBAHN;
		UBAHN = uBAHN;
		FERNBAHN = fERNBAHN;
	}
	public Connections() {
		this.FERNBAHN = false;
		this.UBAHN = false;
		this.SBAHN = false;
	}
	private boolean SBAHN;
	private boolean UBAHN;
	private boolean FERNBAHN;
	
	public boolean isSBAHN() {
		return SBAHN;
	}
	public void setSBAHN(boolean sBAHN) {
		SBAHN = sBAHN;
	}
	public boolean isUBAHN() {
		return UBAHN;
	}
	public void setUBAHN(boolean uBAHN) {
		UBAHN = uBAHN;
	}
	public boolean isFERNBAHN() {
		return FERNBAHN;
	}
	public void setFERNBAHN(boolean fERNBAHN) {
		FERNBAHN = fERNBAHN;
	}
	
	
	public String generateConnectionConditions() {
		String connectionConditions = "";
	if(this.FERNBAHN && this.UBAHN && this.SBAHN)
		connectionConditions += "FERNBAHN|SBAHN|UBAHN";
	if((this.FERNBAHN && this.UBAHN && !(this.SBAHN)))
			connectionConditions += "FERNBAHN|UBAHN";
	if((this.FERNBAHN && this.SBAHN && !(this.UBAHN)))
		connectionConditions += "FERNBAHN|SBAHN";
	if((this.SBAHN && this.UBAHN && !(this.FERNBAHN)))
		connectionConditions += "SBAHN|UBAHN";
	if((this.SBAHN && !(this.UBAHN) && !(this.FERNBAHN)))
		connectionConditions += "SBAHN";
	if((this.UBAHN && !(this.SBAHN) && !(this.FERNBAHN)))
		connectionConditions += "UBAHN";
	if((this.FERNBAHN && !(this.UBAHN) && !(this.SBAHN)))
		connectionConditions += "FERNBAHN";
	
	return connectionConditions;	
	}

	public String toString() {
		String s ="Selected connections: \n";
		if(this.SBAHN)
			s+= "SBAHN\n";
		if(this.UBAHN)
			s+="UBAHN\n";
		if(this.FERNBAHN)
			s+="FERNBAHN\n";
		return s;
	}
	

}
