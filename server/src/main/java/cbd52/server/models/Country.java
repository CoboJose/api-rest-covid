package cbd52.server.models;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "country")
public class Country {
	
	@Id
	String name;
	Integer todayTotalConfirmed;
	Integer todayTotalDeaths;
	Integer todayNewConfirmed;
	Integer todayNewDeaths;
	LocalDate dateUpdate;
	
	List<Region> regions;
	
}
