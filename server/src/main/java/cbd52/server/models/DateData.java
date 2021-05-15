package cbd52.server.models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;


@Data
@Document(collection = "dateData")
public class DateData {

	@Id
	String date;
	Integer timestamp;
	
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
	
	List<Autonomy> autonomies;
	
	public DateData(String date) {
		this.date = date;
		this.timestamp  = (int) ((Timestamp.valueOf(date + " 00:00:00").getTime())/1000);
		this.autonomies = new ArrayList<Autonomy>();
	}
	
	public DateData() {
	}

	public void addAutonomy(Autonomy autonomy) {
		this.autonomies.add(autonomy);
	}
}