package cbd52.server.models;

import lombok.Data;

@Data
public class Province {
	
	String name;
	
	Integer confirmed;
	Integer newConfirmed;
	
	Integer recovered;
	Integer newRecovered;
	
	Integer hospitalised;
	Integer newHospitalised;
	
	Integer icu;
	Integer newIcu;
	
	Integer deaths;
	Integer newDeaths;
	
	public Province(String name) {
		this.name = name;
	}
	
	public Province() {
	}	
}