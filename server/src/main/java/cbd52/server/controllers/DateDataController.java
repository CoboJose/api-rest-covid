package cbd52.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cbd52.server.models.DateData;
import cbd52.server.repositories.DateDataRepository;
import cbd52.server.services.UpdateDatabaseService;

@RestController
@RequestMapping("/v1")
public class DateDataController {
	
	@Autowired
	private DateDataRepository dateDataRepository;
	@Autowired
	private UpdateDatabaseService updateDatabaseService;
	
    
    @RequestMapping(value="/dateData", method=RequestMethod.GET, produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public String DateData(@RequestParam String date) {
    	// Check if the data for that day already exists in the database, if not create it.
    	/*DateData res = dateDataRepository.findDateDataByDate(date);
    	if(res == null) {
    		updateDatabaseService.updateDateDataDB(date);
    		res = dateDataRepository.findDateDataByDate(date);
    	}
    	System.out.println(dateDataRepository.findAutonomyByDate(date));
        return res;*/
    	System.out.println(dateDataRepository.findAutonomyByDateAndName(date, "Baleares"));
    	return dateDataRepository.findAutonomyByDateAndName(date, "Baleares").toString();
    }
    
    //TODO: When asking for a date, return only the high level data necesary to display the map. When clicking in a province, return all the data of the province.
    //TODO: add a counter of number the page has been visited for using the update mongodb function with the inc (page 20 of Practica).
    //TODO: Use pipelines like uin page 25 of Practica, for for example return the total cases from one day to the next one.
    //TODO: Query for days with more cases than x.
    //TODO: Query asking for some calculated data like the Incidency.
    //TODO: Query the top 3 Incidency provinces of a date.
}