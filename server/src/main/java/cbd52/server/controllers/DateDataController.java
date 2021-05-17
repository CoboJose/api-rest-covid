package cbd52.server.controllers;

import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Aggregates.limit;
import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Aggregates.project;
import static com.mongodb.client.model.Aggregates.sort;
import static com.mongodb.client.model.Aggregates.unwind;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.computed;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Sorts.descending;
import static com.mongodb.client.model.Projections.exclude;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Aggregates.count;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.bson.BsonNull;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;

import cbd52.server.models.DateData;
import cbd52.server.models.TopNewConfirmedProvince;
import cbd52.server.models.TotalData;
import cbd52.server.repositories.DateDataRepository;
import cbd52.server.services.UpdateDatabaseService;
import cbd52.server.utils.DateUtil;

@RestController
@RequestMapping("/v1")
public class DateDataController {
	
	@Autowired
	private DateDataRepository dateDataRepository;
	@Autowired
	private UpdateDatabaseService updateDatabaseService;
	
	@Autowired MongoTemplate mongo;
	
    
    @RequestMapping(value="/dateData", method=RequestMethod.GET, produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public DateData DateData(@RequestParam String date) {
    	// Update the database if yesterdays date is different from the last data date.
    	updateDatabaseService.updateDB();
    	
    	Optional<DateData> res = dateDataRepository.findById(date);
    	if(res.isEmpty()){throw new ResponseStatusException(HttpStatus.NOT_FOUND);}
    	return res.get();
    }
    
    @RequestMapping(value="/totalData", method=RequestMethod.GET, produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public TotalData TotalData() {
    	MongoCollection<Document> c = mongo.getCollection("dateData");
    	List<Bson> pipeline = Arrays.asList(group(new BsonNull(), sum("confirmed", "$newConfirmed"), sum("recovered", "$newRecovered"), sum("hospitalised", "$newHospitalised"), sum("icu", "$newIcu"), sum("deaths", "$newDeaths")));

    	return c.aggregate(pipeline, TotalData.class).first();
    }
    
    @RequestMapping(value="/top3Provinces", method=RequestMethod.GET, produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<TopNewConfirmedProvince> Top3Provinces() {
    	MongoCollection<Document> c = mongo.getCollection("dateData");
    	String yesterday = new DateUtil().getYesterdaysStringDate();
    	List<Bson> pipeline = Arrays.asList(match(eq("_id", yesterday)), unwind("$autonomies"), unwind("$autonomies.provinces"), sort(descending("autonomies.provinces.newConfirmed")), limit(3), project(fields(computed("name", "$autonomies.provinces.name"), computed("newConfirmed", "$autonomies.provinces.newConfirmed"), excludeId())));
    	
    	
    	AggregateIterable<TopNewConfirmedProvince> result = c.aggregate(pipeline, TopNewConfirmedProvince.class);
    	
    	List<TopNewConfirmedProvince> res = new ArrayList<TopNewConfirmedProvince>();
    	Iterator<TopNewConfirmedProvince> i = result.iterator();
    	while(i.hasNext()) {res.add(i.next());}
    	
        return res;
    }
    
    @RequestMapping(value="/topDateBy", method=RequestMethod.GET, produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public DateData TopDateBy(@RequestParam String by) {
    	if(!by.equals("Confirmed") && !by.equals("Deaths") && !by.equals("Recovered") && !by.equals("Hospitalised") && !by.equals("Icu")) {
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    	}
    	MongoCollection<Document> c = mongo.getCollection("dateData");
    	List<Bson> pipeline = Arrays.asList(sort(descending("new" + by)), limit(1), project(exclude("autonomies")));
    	DateData res = c.aggregate(pipeline, DateData.class).first();
    	res.setDate(new DateUtil().getDateStringFromTimeStamp(res.getTimestamp()));
    	
        return res;
    }
    
    @RequestMapping(value="/DaysWithMoreThan", method=RequestMethod.GET, produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public Integer DaysWithMoreThan(@RequestParam String by, int more) {
    	if(!by.equals("Confirmed") && !by.equals("Deaths") && !by.equals("Recovered") && !by.equals("Hospitalised") && !by.equals("Icu")) {
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    	}
    	MongoCollection<Document> c = mongo.getCollection("dateData");
    	List<Bson> pipeline = Arrays.asList(match(gte("new"+by, more)), count("count"));
    	Document res = c.aggregate(pipeline).first();
    	
        return res.getInteger("count");
    }
    
    @RequestMapping(value="/DateDataRange", method=RequestMethod.GET, produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<DateData> DateDataRange(@RequestParam String from, String to) {
        return dateDataRepository.getByRange(new DateUtil().getTimestampFromString(from), new DateUtil().getTimestampFromString(to));
    }
    
}