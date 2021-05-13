package cbd52.server.controllers;

import java.util.Optional;

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
    public DateData DateData(@RequestParam String date) {
        DateData res = null;
    	// Check if the data for that day already exists in the database, if not create it.
    	Optional<DateData> dateDataOptional = dateDataRepository.findById(date);
    	if(dateDataOptional.isPresent()) {
    		res =  dateDataOptional.get();
    	}else {
    		res = updateDatabaseService.updateDateDataDB(date);
    	}
    	
        return res;
    }
}