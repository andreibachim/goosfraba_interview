package ro.goosfraba.interview.vehicle;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ro.goosfraba.interview.city.CityService;
import ro.goosfraba.interview.city.dto.CityDTO;
import ro.goosfraba.interview.facility.FacilityService;
import ro.goosfraba.interview.vehicle.dao.VehicleDAO;
import ro.goosfraba.interview.vehicle.dto.CreateVehicleDTO;
import ro.goosfraba.interview.vehicle.dto.UnparkVehicleDTO;
import ro.goosfraba.interview.vehicle.dto.VehicleDTO;
import ro.goosfraba.interview.vehicle.exceptions.AlreadyParkedException;
import ro.goosfraba.interview.vehicle.exceptions.NotEnoughCapacityException;
import ro.goosfraba.interview.vehicle.exceptions.ParkingTypeMismatchException;
import ro.goosfraba.interview.vehicle.exceptions.VehicleNotParkedException;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository repository;
    private final CityService cityService;
    private final FacilityService facilityService;

    public Mono<VehicleDTO> createVehicle(Mono<CreateVehicleDTO> vehicleData) {
        return vehicleData
                .map(this::fromCreateVehicleDTO)
                .flatMap(repository::save)
                .map(this::fromDAO);

    }

    public Flux<VehicleDTO> findVehiclesByCityCode(String cityCode) {
        return cityService.getCityByCode(cityCode)
                .map(CityDTO::id)
                .flatMapMany(repository::findAllByCityId)
                .map(this::fromDAO);

    }

    public Mono<VehicleDTO> parkVehicle(String vehicleId, String facilityId) {
        return Mono
                .zip(repository.findById(vehicleId),
                        facilityService.getFacility(facilityId))
                .filter(tuple -> tuple.getT1().getType() == tuple.getT2().type())
                .switchIfEmpty(Mono.error(new ParkingTypeMismatchException()))
                .filter(tuple -> tuple.getT2().availableCapacity() > 0)
                .switchIfEmpty(Mono.error(new NotEnoughCapacityException()))
                .filter(tuple -> !tuple.getT1().getIsParked())
                .switchIfEmpty(Mono.error(new AlreadyParkedException()))
                .flatMap(tuple -> repository.save(new VehicleDAO(
                        tuple.getT1().getId(),
                        tuple.getT1().getType(),
                        tuple.getT1().getCityId(),
                        true,
                        tuple.getT2().id(),
                        false)))
                .map(this::fromDAO);
    }

    public Mono<VehicleDTO> unparkVehicle(UnparkVehicleDTO data) {
        return repository.findById(data.vehicleId())
                .filter(VehicleDAO::getIsParked)
                .switchIfEmpty(Mono.error(new VehicleNotParkedException()))
                .flatMap(vehicle -> repository.save(new VehicleDAO(
                        vehicle.getId(),
                        vehicle.getType(),
                        vehicle.getCityId(),
                        false,
                        null,
                        false)))
                .map(this::fromDAO);
    }

    private VehicleDAO fromCreateVehicleDTO(CreateVehicleDTO vehicleDTO) {
        return new VehicleDAO(
                UUID.randomUUID().toString(),
                vehicleDTO.type(),
                vehicleDTO.cityId(),
                false,
                null,
                true
        );
    }

    private VehicleDTO fromDAO(VehicleDAO vehicleDAO) {
        return new VehicleDTO(
                vehicleDAO.getId(),
                vehicleDAO.getType(),
                vehicleDAO.getCityId(),
                vehicleDAO.getIsParked(),
                vehicleDAO.getFacilityId()
        );
    }
}
