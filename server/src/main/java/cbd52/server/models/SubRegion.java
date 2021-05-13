package cbd52.server.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "subRegion")
public class SubRegion {
	
	@Id
	String name;
	
	Integer todayTotalConfirmed;
	Integer todayTotalDeaths;
	Integer todayNewConfirmed;
	Integer todayNewDeaths;
		
}