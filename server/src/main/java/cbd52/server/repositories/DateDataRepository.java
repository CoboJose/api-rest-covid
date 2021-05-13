package cbd52.server.repositories;

import java.util.List;

import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import cbd52.server.models.Autonomy;
import cbd52.server.models.DateData;

@Repository
public interface DateDataRepository extends MongoRepository<DateData, String>{
    
	@Query(value="{_id: ?0}", fields="{autonomies: {todayTotalConfirmed: 0} }")
	DateData findDateDataByDate(String date);
	
	@Aggregation(pipeline = {"{$match: {_id: ?0}}", "{$unwind:{path:$autonomies}}", "{$match: {autonomies: {name:?1}}}", "{$project: {a: $autonomies, _id:0}}"})
	Autonomy findAutonomyByDateAndName(String date, String name);
}
