package cbd52.server.utils;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;

public class DateUtil {
	
	public DateUtil(){
	}
	
	public Integer getTimestampFromString(String date) {
		return (int) ((Timestamp.valueOf(date + " 00:00:00").getTime())/1000);
	}
	
	public String getYesterdaysStringDate() {
		return LocalDate.now(ZoneId.of("Europe/Paris")).minusDays(1).toString();
	}
	
}
