package cbd52.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cbd52.server.repositories.DateDataRepository;

@RestController
@RequestMapping("/v1")
public class TotalDataController {
	
	@Autowired
	private DateDataRepository dateDataRepository;
	
    //TODO
    @RequestMapping(value="/totalData", method=RequestMethod.GET, produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public String TotalData() {
    	
        return "ole";
    }
}