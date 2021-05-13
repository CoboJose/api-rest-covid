package cbd52.server.services;

import java.sql.Date;
import java.time.LocalDate;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mashape.unirest.http.Unirest;

import cbd52.server.models.DateData;
import cbd52.server.models.TotalData;
import cbd52.server.repositories.DateDataRepository;
import cbd52.server.repositories.TotalDataRepository;

@Service
public class UpdateDatabaseService {
	
	@Autowired
	private DateDataRepository dateDataRepository;
	@Autowired
	private TotalDataRepository totalDataRepository;
	
	public UpdateDatabaseService() {
		HttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build()).build();
        Unirest.setHttpClient(httpClient);
	}
	
	public DateData updateDateDataDB(String date) {
        DateData res = new DateData();
		try {
        	String url = "https://api.covid19tracking.narrativa.com/api/" + date + "/country/spain";
            JSONObject response = Unirest.get(url).asJson().getBody().getObject();
            
            res.setDateString(date);
            res.setDate(Date.valueOf(LocalDate.parse(date)));
            res.setUpdatedAt(response.getString("updated_at"));
            res.setTodayConfirmed(response.getJSONObject("dates").getJSONObject(date).getJSONObject("countries").getJSONObject("Spain").getInt("today_confirmed"));
            
            res = this.dateDataRepository.save(res);
           
            /*while (keys.hasNext()) {
        	   String key = keys.next().toString();
				JSONObject stock = stocks.getJSONObject(key);
				System.out.println(stock);
				saveToDatabase(stock);
			}*/
        	
        } catch (Exception e) {
            e.printStackTrace();
        }
		return res;
    }
	
	public TotalData updateTotalDataDB(String date) {
        TotalData res = new TotalData();
		try {
            
            res.setDateString(date);
            res.setDate(Date.valueOf(LocalDate.parse(date)));
            
            res = this.totalDataRepository.save(res);
        	
        } catch (Exception e) {
            e.printStackTrace();
        }
		return res;
    }
}
