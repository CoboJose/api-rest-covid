package cbd52.server.services;


import java.util.Iterator;

import com.mashape.unirest.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import cbd52.server.models.Model;
import cbd52.server.repositories.ModelRepository;

@Service
public class UpdateDatabaseService {
	
	@Autowired
	private ModelRepository modelRepository;
	
	private static String API_URL = "https://api.covid19tracking.narrativa.com/api/2021-05-11/country/spain";
	
	UpdateDatabaseService(){
		HttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setCookieSpec(CookieSpecs.STANDARD).build())
                .build();
        Unirest.setHttpClient(httpClient);
	}
	
//	@Scheduled(fixedRate = 60000)
    public void updateStockQuotes() {
        try {
            HttpResponse<JsonNode> stocksResponse = Unirest.get(API_URL)
//                    .queryString("symbols", "AAPL, FB, BK, GOOGL, AMZN, SNAP, MSFT, IBM, MS")
//                    .queryString("types", "quote")
                    .asJson();
            JSONObject stocks = stocksResponse.getBody().getObject();
            JSONObject countryStats = stocks.getJSONObject("dates").getJSONObject("2021-05-11").getJSONObject("countries").getJSONObject("Spain");
            JSONArray regionStats = stocks.getJSONObject("dates").getJSONObject("2021-05-11").getJSONObject("countries").getJSONObject("Spain").getJSONArray("regions");
            for(int i = 0; i < regionStats.length(); i++) {
            	
            }
            
            System.out.println(regionStats);
            
           
//           while (keys.hasNext()) {
//        	   String key = keys.next().toString();
//				JSONObject stock = stocks.getJSONObject(key);
//				System.out.println(stock);
//				//saveToDatabase(stock);
//			}
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }
	
//	private void saveToDatabase(JSONObject stock) {
//		Model model = new Model();
//		System.out.println(stock);
//	}
}
