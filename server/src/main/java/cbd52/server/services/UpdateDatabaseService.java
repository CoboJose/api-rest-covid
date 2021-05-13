package cbd52.server.services;

import java.sql.Date;
import java.time.LocalDate;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mashape.unirest.http.Unirest;

import cbd52.server.models.Autonomy;
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
	
	public void updateDateDataDB(String date) {
		try {
        	String url = "https://api.covid19tracking.narrativa.com/api/" + date + "/country/spain";
            JSONObject response = Unirest.get(url).asJson().getBody().getObject();
            
            DateData dData = new DateData(date);
            dData.setUpdatedAt(response.getString("updated_at"));
            
            //Country
            JSONObject spain = response.getJSONObject("dates").getJSONObject(date).getJSONObject("countries").getJSONObject("Spain");
            dData.setTodayConfirmed(spain.getInt("today_confirmed"));
            //Autonomies
            JSONArray autonomies = spain.getJSONArray("regions");
            for(int i = 0; i < autonomies.length(); i++) {
            	JSONObject autonomyJson = autonomies.getJSONObject(i);
            	Autonomy autonomy = new Autonomy();
            	
            	autonomy.setName(autonomyJson.getString("name"));
            	autonomy.setTodayTotalConfirmed(500);
            	
            	dData.addAutonomy(autonomy);
            }
            
            dData = this.dateDataRepository.save(dData);
        	
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	public void updateTotalDataDB(String date) {
		try {
			TotalData res = new TotalData();
            res.setDateString(date);
            res.setDate(Date.valueOf(LocalDate.parse(date)));
            
            res = this.totalDataRepository.save(res);
        	
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
