package cbd52.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cbd52.server.services.UpdateDatabaseService;

@RestController
@RequestMapping("/v1")
public class PopulateDBController {
	
	@Autowired
	private UpdateDatabaseService updateDatabaseService;
	
	@RequestMapping(value="/populateDB", method=RequestMethod.GET, produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public String PopulateDB(@RequestParam String dateFrom, @RequestParam String dateTo) {
    	updateDatabaseService.populateDB(dateFrom, dateTo);
        return "Database Populated succesfully";
    }
}
