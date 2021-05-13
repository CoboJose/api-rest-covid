package cbd52.server.controllers;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cbd52.server.models.TotalData;
import cbd52.server.repositories.TotalDataRepository;
import cbd52.server.services.UpdateDatabaseService;

@RestController
@RequestMapping("/v1")
public class TotalDataController {
	
	@Autowired
	private TotalDataRepository totalDataRepository;
	@Autowired
	private UpdateDatabaseService updateDatabaseService;
	
    //TODO
    @RequestMapping(value="/totalData", method=RequestMethod.GET, produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public TotalData TotalData() {
    	TotalData res = null;
    	String date = LocalDate.now().toString();
    	System.out.println(date);
    	// Check if the data for that day already exists in the database, if not create it.
    	Optional<TotalData> totalDataOptional = totalDataRepository.findById(date);
    	if(totalDataOptional.isPresent()) {
    		res =  totalDataOptional.get();
    	}else {
    		res = updateDatabaseService.updateTotalDataDB(date);
    	}
    	
        return res;
    }
}