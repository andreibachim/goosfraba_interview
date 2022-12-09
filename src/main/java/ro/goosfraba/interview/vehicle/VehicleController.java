package ro.goosfraba.interview.vehicle;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ro.goosfraba.interview.vehicle.dto.CreateVehicleDTO;
import ro.goosfraba.interview.vehicle.dto.ParkVehicleDTO;
import ro.goosfraba.interview.vehicle.dto.UnparkVehicleDTO;
import ro.goosfraba.interview.vehicle.dto.VehicleDTO;
import ro.goosfraba.interview.vehicle.exceptions.AlreadyParkedException;
import ro.goosfraba.interview.vehicle.exceptions.NotEnoughCapacityException;
import ro.goosfraba.interview.vehicle.exceptions.ParkingTypeMismatchException;

import java.util.List;
import java.util.stream.Collectors;

@Validated
@Slf4j
@RestController
@RequestMapping("/vehicle")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService service;

    @PutMapping
    public Mono<ResponseEntity<VehicleDTO>> createVehicle(
            @RequestBody
            @Valid
            Mono<CreateVehicleDTO> payload
    ) {
        return service.createVehicle(payload)
                .map(ResponseEntity::ok);
    }

    @GetMapping
    public Mono<ResponseEntity<List<VehicleDTO>>> findVehiclesFromCityCode(
            @RequestParam(value = "citycode") String cityCode
    ) {
        return service.findVehiclesByCityCode(cityCode)
                .collect(Collectors.toList())
                .map(ResponseEntity::ok);
    }

    @PostMapping("/park")
    public Mono<ResponseEntity<VehicleDTO>> parkVehicle(@RequestBody @Valid ParkVehicleDTO payload) {
      return service.parkVehicle(payload.vehicleId(), payload.facilityId())
              .map(ResponseEntity::ok)
              .defaultIfEmpty(ResponseEntity.notFound().build())
              .onErrorReturn(AlreadyParkedException.class, ResponseEntity.badRequest().build())
              .onErrorReturn(ParkingTypeMismatchException.class, ResponseEntity.badRequest().build())
              .onErrorReturn(NotEnoughCapacityException.class, ResponseEntity.badRequest().build());
    }

    @PostMapping("/unpark")
    public Mono<ResponseEntity<VehicleDTO>> unparkVehicle(@RequestBody @Valid UnparkVehicleDTO payload) {
        return service.unparkVehicle(payload)
                .map(ResponseEntity::ok);
    }
}
