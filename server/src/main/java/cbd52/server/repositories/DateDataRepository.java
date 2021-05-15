package cbd52.server.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import cbd52.server.models.DateData;

@Repository
public interface DateDataRepository extends MongoRepository<DateData, String>{
    
}
