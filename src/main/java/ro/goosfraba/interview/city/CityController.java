package ro.goosfraba.interview.city;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ro.goosfraba.interview.city.dto.CityDTO;
import ro.goosfraba.interview.city.dto.CreateCityDTO;
import ro.goosfraba.interview.constants.ValidationConstants;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/city")
@RequiredArgsConstructor
@Validated
public class CityController {
    private final CityService service;

    @PutMapping
    public Mono<ResponseEntity<CityDTO>> createCity(@RequestBody @Valid Mono<CreateCityDTO> cityData) {
        return service.saveCity(cityData)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{cityId}")
    public Mono<ResponseEntity<CityDTO>> getCityById(
            @PathVariable("cityId")
            @Valid
            @Pattern(regexp = ValidationConstants.UUID_PATTERN,
                    message = "City ID is not the correct format.")
            String cityId) {
        return service
                .getCityById(cityId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/code/{code}")
    public Mono<ResponseEntity<CityDTO>> getCityByCode(
            @PathVariable("code")
            @Valid
            @Size(min = 1, max = 4, message = "City code is not the correct format.")
            String code) {
        return service.getCityByCode(code)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping
    public Mono<ResponseEntity<List<CityDTO>>> getAllCities() {
        return service.getAllCities()
                .collect(Collectors.toList())
                .map(ResponseEntity::ok);
    }
}