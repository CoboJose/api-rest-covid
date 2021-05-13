package cbd52.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cbd52.server.models.Model;
import cbd52.server.models.ModelsResponse;
import cbd52.server.repositories.ModelRepository;
import cbd52.server.services.UpdateDatabaseService;

@RestController
@RequestMapping("/v1")
public class TestController {

    @Autowired
    ModelRepository modelRepository;

    @Autowired
    UpdateDatabaseService updateDatabaseService;

    @RequestMapping(value="/models", method=RequestMethod.GET, produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public ModelsResponse getModels() {
        updateDatabaseService.updateStockQuotes();
        return new ModelsResponse(modelRepository.findAll());
    }

    @RequestMapping(value="/models", method=RequestMethod.POST, produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public Model createModel(@RequestBody Model newModel) {
        System.out.println(newModel);
        return modelRepository.save(newModel);
    }


}