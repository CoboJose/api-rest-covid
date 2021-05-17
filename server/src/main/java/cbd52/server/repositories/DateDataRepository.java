package cbd52.server.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import cbd52.server.models.DateData;

@Repository
public interface DateDataRepository extends MongoRepository<DateData, String>{
    
	@Aggregation(pipeline = { "{$sort:{timestamp:-1}}", "{$limit:1}", "{$project: {_id:1}}" })
	public String findLastDay();
	
	@Query("{timestamp: {$gte:?0, $lte:?1}}")
	public List<DateData> getByRange(int from, int to);
}
