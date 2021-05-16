package cbd52.server.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
    public DateData DateData(@RequestParam String date) {
    	// Update the database if yesterdays date is different from the last data date.
    	updateDatabaseService.updateDB();
    	
    	Optional<DateData> res = dateDataRepository.findById(date);
    	if(res.isEmpty()){throw new ResponseStatusException(HttpStatus.NOT_FOUND);}
    	return res.get();
    }
    
    //TODO: add a counter of number the page has been visited for using the update mongodb function with the inc (page 20 of Practica).
    //TODO: Use pipelines like uin page 25 of Practica, for for example return the total cases from one day to the next one.
    //TODO: Query for days with more cases than x.
    //TODO: Query the top 3 Incidency provinces of a date.
    //TODO: Query the top day of some indicator.
}