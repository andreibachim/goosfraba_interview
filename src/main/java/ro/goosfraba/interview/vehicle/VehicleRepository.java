package ro.goosfraba.interview.vehicle;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ro.goosfraba.interview.vehicle.dao.VehicleDAO;

public interface VehicleRepository extends ReactiveCrudRepository<VehicleDAO, String> {
    @Query(value = "select * from vehicle where city_id=$1")
    Flux<VehicleDAO> findAllByCityId(String cityId);

    @Query(value = "update vehicle set is_parked = true, facility_id = $2 where id = $1")
    Mono<VehicleDAO> parkVehicle(String vehicleId, String facilityId);
}
