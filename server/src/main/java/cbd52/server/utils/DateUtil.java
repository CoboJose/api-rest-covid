package cbd52.server.utils;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateUtil {
	
	public DateUtil(){
	}
	
	public Integer getTimestampFromString(String date) {
		return (int) ((Timestamp.valueOf(date + " 00:00:00").getTime())/1000);
	}
	
	public String getYesterdaysStringDate() {
		return LocalDate.now(ZoneId.of("Europe/Paris")).minusDays(1).toString();
	}
	
	public String getBeforeDaysFromStringDate(String date, int days) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return LocalDate.parse(date, formatter).minusDays(days).toString();
	}

	public String getDateStringFromTimeStamp(Integer timestamp) {
		return Instant.ofEpochSecond(timestamp).atZone(ZoneId.systemDefault()).toLocalDate().toString();
	}
	
}
