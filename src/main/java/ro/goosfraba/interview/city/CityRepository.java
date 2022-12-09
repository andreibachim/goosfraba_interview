package ro.goosfraba.interview.city;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import ro.goosfraba.interview.city.dao.CityDAO;

@Repository
public interface CityRepository extends ReactiveCrudRepository<CityDAO, String> {
    @Query(value = "select * from city where code=$1")
    Mono<CityDAO> findByCode(String cityCode);
}
