package cbd52.server.services;

import java.util.Arrays;
import java.util.List;

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
import cbd52.server.models.Province;
import cbd52.server.repositories.DateDataRepository;
import cbd52.server.utils.DateUtil;

@Service
public class UpdateDatabaseService {

	@Autowired
	private DateDataRepository dateDataRepository;

	public UpdateDatabaseService() {
		HttpClient httpClient = HttpClients.custom()
				.setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build()).build();
		Unirest.setHttpClient(httpClient);
	}
	
	
	public void updateDB() {
		String lastDayInDB = dateDataRepository.findLastDay();
		String yesterday = new DateUtil().getYesterdaysStringDate();
		
		if(!lastDayInDB.equals(yesterday)) {
			this.populateDB(lastDayInDB, yesterday);
		}
	}

	public void populateDB(String dateFrom, String dateTo) {
		try {
			String url = "https://api.covid19tracking.narrativa.com/api/country/spain?date_from=" + dateFrom + "&date_to=" + dateTo;
			
			JSONObject response = Unirest.get(url).asJson().getBody().getObject();
			JSONObject dates = response.getJSONObject("dates");
			
			for (int i = 0; i < dates.keySet().size(); i++) {
				String date = (String) dates.keySet().toArray()[i];
				System.out.println("Updating the Database with Data from: " + date);
				
				// Country
				JSONObject spain = response.getJSONObject("dates").getJSONObject(date).getJSONObject("countries").getJSONObject("Spain");
				DateData dData = new DateData(date);
				
				dData.setConfirmed(spain.getInt("today_confirmed"));
				dData.setNewConfirmed(spain.getInt("today_new_confirmed"));
				dData.setRecovered(spain.getInt("today_recovered"));
				dData.setNewRecovered(spain.getInt("today_new_recovered"));
				dData.setHospitalised(spain.getInt("today_total_hospitalised_patients"));
				dData.setNewHospitalised(spain.getInt("today_new_total_hospitalised_patients"));
				dData.setIcu(spain.getInt("today_intensive_care"));
				dData.setNewIcu(spain.getInt("today_new_intensive_care"));
				dData.setDeaths(spain.getInt("today_deaths"));
				dData.setNewDeaths(spain.getInt("today_new_deaths"));
				// Autonomies
				JSONArray autonomies = spain.getJSONArray("regions");
				for (int j = 0; j < autonomies.length(); j++) {
					JSONObject autonomyJson = autonomies.getJSONObject(j);
					Autonomy autonomy = new Autonomy(autonomyJson.getString("name"));

					autonomy.setConfirmed(getIntFromJson(autonomyJson, "today_confirmed"));
					autonomy.setNewConfirmed(getIntFromJson(autonomyJson, "today_new_confirmed"));
					autonomy.setRecovered(getIntFromJson(autonomyJson, "today_recovered"));
					autonomy.setNewRecovered(getIntFromJson(autonomyJson, "today_new_recovered"));
					autonomy.setHospitalised(getIntFromJson(autonomyJson, "today_total_hospitalised_patients"));
					autonomy.setNewHospitalised(getIntFromJson(autonomyJson, "today_new_total_hospitalised_patients"));
					autonomy.setIcu(getIntFromJson(autonomyJson, "today_intensive_care"));
					autonomy.setNewIcu(getIntFromJson(autonomyJson, "today_new_intensive_care"));
					autonomy.setDeaths(getIntFromJson(autonomyJson, "today_deaths"));
					autonomy.setNewDeaths(getIntFromJson(autonomyJson, "today_new_deaths"));

					dData.addAutonomy(autonomy);

					// Provinces
					JSONArray provinces = autonomyJson.getJSONArray("sub_regions");
					// Like Madrid
					if (provinces.length() == 0) {
						Province province = new Province(autonomy.getName());
						province.setConfirmed(autonomy.getConfirmed());
						province.setNewConfirmed(autonomy.getNewConfirmed());
						province.setRecovered(autonomy.getRecovered());
						province.setNewRecovered(autonomy.getNewRecovered());
						province.setHospitalised(autonomy.getHospitalised());
						province.setNewHospitalised(autonomy.getNewHospitalised());
						province.setIcu(autonomy.getIcu());
						province.setNewIcu(autonomy.getNewIcu());
						province.setDeaths(autonomy.getDeaths());
						province.setNewDeaths(autonomy.getNewDeaths());
						
						autonomy.addProvince(province);
					} else {
						for (int k = 0; k < provinces.length(); k++) {
							JSONObject provinceJson = provinces.getJSONObject(k);
							Province province = new Province(provinceJson.getString("name"));

							province.setConfirmed(getIntFromJson(provinceJson, "today_confirmed"));
							province.setNewConfirmed(getIntFromJson(provinceJson, "today_new_confirmed"));
							province.setRecovered(getIntFromJson(provinceJson, "today_recovered"));
							province.setNewRecovered(getIntFromJson(provinceJson, "today_new_recovered"));
							province.setHospitalised(getIntFromJson(provinceJson, "today_total_hospitalised_patients"));
							province.setNewHospitalised(getIntFromJson(provinceJson, "today_new_total_hospitalised_patients"));
							province.setIcu(getIntFromJson(provinceJson, "today_intensive_care"));
							province.setNewIcu(getIntFromJson(provinceJson, "today_new_intensive_care"));
							province.setDeaths(getIntFromJson(provinceJson, "today_deaths"));
							province.setNewDeaths(getIntFromJson(provinceJson, "today_new_deaths"));

							autonomy.addProvince(province);
						}
					}
				}
				this.organiceProvinces(dData.getAutonomies());
				dData = this.dateDataRepository.save(dData);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void organiceProvinces(List<Autonomy> autonomies) {
		for(Autonomy autonomy: autonomies) {
			switch(autonomy.getName()) {
				case "Extremadura":
					Province badajoz = this.getNewProvince(autonomy.getProvinces(), "Badajoz", Arrays.asList("Área de Salud de Badajoz", "Área de Salud de Mérida", "Área de Salud de Don Benito-Villanueva de la Serena", "Área de Salud de Llerena-Zafra"));
					Province caceres = this.getNewProvince(autonomy.getProvinces(), "Cáceres", Arrays.asList("Área de Salud de Cáceres", "Área de Salud de Plasencia", "Área de Salud Navalmoral", "Área de Salud de Coria"));
					autonomy.setProvinces(Arrays.asList(caceres, badajoz));
					break;
				case "Canarias":
					Province lasPalmas = this.getNewProvince(autonomy.getProvinces(), "Las Palmas", Arrays.asList("Lanzarote", "Fuerteventura", "Gran Canaria"));
					Province tenerife = this.getNewProvince(autonomy.getProvinces(), "Santa Cruz de Tenerife", Arrays.asList("La Palma", "La Gomera", "El Hierro"));
					autonomy.setProvinces(Arrays.asList(lasPalmas, tenerife));
					break;
				case "Galicia":
					Province laCoruña = this.getNewProvince(autonomy.getProvinces(), "La Coruña", Arrays.asList("Área sanitaria A Coruña", "Área sanitaria Santiago", "Área sanitaria Ferrol"));
					Province lugo = this.getNewProvince(autonomy.getProvinces(), "Lugo", Arrays.asList("Área sanitaria Lugo"));
					Province orense = this.getNewProvince(autonomy.getProvinces(), "Orense", Arrays.asList("Área sanitaria Ourense"));
					Province pontevedra = this.getNewProvince(autonomy.getProvinces(), "Pontevedra", Arrays.asList("Área sanitaria Pontevedra", "Área sanitaria Vigo"));
					autonomy.setProvinces(Arrays.asList(laCoruña, lugo, orense, pontevedra));
					break;
			}
		}
		
	}

	private Province getNewProvince(List<Province> oldProvinces, String newProvince, List<String> oldProvinceNames) {
		Province res = new Province(newProvince);
		Integer confirmed, newConfirmed, recovered, newRecovered, hospitalised, newHospitalised, icu, newIcu, deaths, newDeaths;
		confirmed = newConfirmed = recovered = newRecovered = hospitalised = newHospitalised = icu = newIcu = deaths = newDeaths = null;
		
		for(Province p : oldProvinces) {
			if(oldProvinceNames.contains(p.getName())) {
				if(p.getConfirmed() != null) {
					if(confirmed == null) {
						confirmed =  p.getConfirmed();
					}else {
						confirmed +=  p.getConfirmed();
					}
				}
				if(p.getNewConfirmed() != null) {
					if(newConfirmed == null) {
						newConfirmed =  p.getNewConfirmed();
					}else {
						newConfirmed +=  p.getNewConfirmed();
					}
				}
				if(p.getRecovered() != null) {
					if(recovered == null) {
						recovered =  p.getRecovered();
					}else {
						recovered +=  p.getRecovered();
					}
				}
				if(p.getNewRecovered() != null) {
					if(newRecovered == null) {
						newRecovered =  p.getNewRecovered();
					}else {
						newRecovered +=  p.getNewRecovered();
					}
				}
				if(p.getHospitalised() != null) {
					if(hospitalised == null) {
						hospitalised =  p.getHospitalised();
					}else {
						hospitalised +=  p.getHospitalised();
					}
				}
				if(p.getNewHospitalised() != null) {
					if(newHospitalised == null) {
						newHospitalised =  p.getNewHospitalised();
					}else {
						newHospitalised +=  p.getNewHospitalised();
					}
				}
				if(p.getIcu() != null) {
					if(icu == null) {
						icu =  p.getIcu();
					}else {
						icu +=  p.getIcu();
					}
				}
				if(p.getNewIcu() != null) {
					if(newIcu == null) {
						newIcu =  p.getNewIcu();
					}else {
						newIcu +=  p.getNewIcu();
					}
				}
				if(p.getDeaths() != null) {
					if(deaths == null) {
						deaths =  p.getDeaths();
					}else {
						deaths +=  p.getDeaths();
					}
				}
				if(p.getNewDeaths() != null) {
					if(newDeaths == null) {
						newDeaths =  p.getNewDeaths();
					}else {
						newDeaths +=  p.getNewDeaths();
					}
				}
			}
		}
		res.setConfirmed(confirmed);
		res.setNewConfirmed(newConfirmed);
		res.setRecovered(recovered);
		res.setNewRecovered(newRecovered);
		res.setHospitalised(hospitalised);
		res.setNewHospitalised(newHospitalised);
		res.setIcu(icu);
		res.setNewIcu(newIcu);
		res.setDeaths(newDeaths);
		res.setNewDeaths(newDeaths);
		
		return res;
	}

	private Integer getIntFromJson(JSONObject json, String name) {
		Integer res = null;
		try {
			Object o = json.get(name);
			if (!o.equals(JSONObject.NULL))
				res = json.getInt(name);
		}catch(Exception e){/*System.out.println(json.getString("name") + " has not the following property: " + name);*/}
		
		return res;
	}

}
