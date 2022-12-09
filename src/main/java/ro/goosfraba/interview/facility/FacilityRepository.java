package ro.goosfraba.interview.facility;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ro.goosfraba.interview.facility.dao.FacilityDAO;
public interface FacilityRepository extends ReactiveCrudRepository<FacilityDAO, String> {

    @Query("select f.*, f.capacity - (select count(*) from vehicle where is_parked = true and facility_id = f.id) " +
            "as available_capacity from facility as f where f.city_id = $1")
    Flux<FacilityDAO> findAllByCityId(String cityId);

    @Override
    @Query("select f.*, f.capacity - (select count(*) from vehicle where is_parked = true and facility_id = $1) " +
            "as available_capacity from facility as f where f.id = $1")
    Mono<FacilityDAO> findById(String facilityId);
}
