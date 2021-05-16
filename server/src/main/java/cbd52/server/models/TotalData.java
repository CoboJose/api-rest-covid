package cbd52.server.models;

import lombok.Data;

@Data
public class TotalData {
	
	Integer confirmed;
	Integer recovered;
	Integer hospitalised;
	Integer icu;
	Integer deaths;
}