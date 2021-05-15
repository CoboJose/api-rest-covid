package cbd52.server.models;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Autonomy {
	
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
	
	List<Province> provinces;
	
	public Autonomy(String name) {
		this.name = name;
		this.provinces = new ArrayList<Province>();
	}
	
	public Autonomy() {
	}

	public void addProvince(Province province) {
		this.provinces.add(province);
	}
}
