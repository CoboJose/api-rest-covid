package cbd52.server.models;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Province {
	
	@Id
	String name;
	
	Integer todayTotalConfirmed;
	Integer todayTotalDeaths;
	Integer todayNewConfirmed;
	Integer todayNewDeaths;
		
}