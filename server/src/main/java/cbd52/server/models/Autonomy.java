package cbd52.server.models;
import java.time.LocalDate;

import lombok.Data;

@Data
public class Autonomy {
	String name;
	Integer todayTotalConfirmed;
	Integer todayTotalDeaths;
	Integer todayNewConfirmed;
	Integer todayNewDeaths;
	LocalDate dateUpdate;
	
	//List<SubRegion> subRegions;
}
