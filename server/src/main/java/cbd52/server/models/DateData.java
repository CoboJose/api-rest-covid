package cbd52.server.models;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;


@Data
@Document(collection = "dateData")
public class DateData {

	@Id
	String dateString;
	Date date;
	String updatedAt;
	int todayConfirmed;
	
	List<Autonomy> autonomies;
	
	public DateData(String date) {
		this.dateString = date;
		this.date = Date.from(LocalDate.parse(date).atStartOfDay(ZoneId.of("Europe/Paris")).plusHours(1).toInstant());
		this.autonomies = new ArrayList<Autonomy>();
	}
	
	public DateData() {
	}

	public void addAutonomy(Autonomy autonomy) {
		this.autonomies.add(autonomy);
	}
}