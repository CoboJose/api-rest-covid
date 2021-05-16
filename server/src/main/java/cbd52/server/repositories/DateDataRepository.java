package cbd52.server.repositories;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import cbd52.server.models.DateData;

@Repository
public interface DateDataRepository extends MongoRepository<DateData, String>{
    
	@Aggregation(pipeline = { "{$sort:{timestamp:-1}}", "{$limit:1}", "{$project: {_id:1}}" })
	public String findLastDay();
}
