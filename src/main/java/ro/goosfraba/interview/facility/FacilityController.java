package ro.goosfraba.interview.facility;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ro.goosfraba.interview.constants.ValidationConstants;
import ro.goosfraba.interview.facility.dto.CreateFacilityDTO;
import ro.goosfraba.interview.facility.dto.FacilityDTO;

import java.util.List;
import java.util.stream.Collectors;

@Validated
@Slf4j
@RestController
@RequestMapping("/facility")
@RequiredArgsConstructor
public class FacilityController {
    private final FacilityService service;
    @PostMapping
    public Mono<ResponseEntity<FacilityDTO>> createParkingFacility(
            @RequestBody
            @Valid
            Mono<CreateFacilityDTO> payload) {
        return service.saveFacility(payload)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{facilityId}")
    public Mono<ResponseEntity<FacilityDTO>> getParkingFacility(
            @PathVariable(name = "facilityId")
            @Valid
            @Pattern(regexp = ValidationConstants.UUID_PATTERN)
            String facilityId) {
        return service.getFacility(facilityId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping
    public Mono<ResponseEntity<List<FacilityDTO>>> getAllParkingFacilitiesFromCity(
            @RequestParam(name = "cityid")
            @Valid
            @NotNull
            @Pattern(regexp = ValidationConstants.UUID_PATTERN)
            String cityId) {
        return service.getAllFacilitiesFromCity(cityId)
                .collect(Collectors.toList())
                .map(ResponseEntity::ok);
    }
}