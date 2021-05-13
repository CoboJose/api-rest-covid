package cbd52.server.models;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Document(collection = "dateData")
public class DateData {

	@Id
	String dateString;
	Date date;
	String updatedAt;
	int todayConfirmed;
	
}