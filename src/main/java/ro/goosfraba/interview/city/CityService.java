package ro.goosfraba.interview.city;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ro.goosfraba.interview.city.dao.CityDAO;
import ro.goosfraba.interview.city.dto.CityDTO;
import ro.goosfraba.interview.city.dto.CreateCityDTO;
import ro.goosfraba.interview.facility.FacilityService;
import ro.goosfraba.interview.facility.dto.FacilityDTO;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository repository;

    private final FacilityService facilityService;

    public Mono<CityDTO> saveCity(Mono<CreateCityDTO> cityData) {
        return cityData
                .map(this::fromCreateCityDTO)
                .flatMap(repository::save)
                .flatMap(city -> facilityService.getAllFacilitiesFromCity(city.getId())
                        .collect(Collectors.toList()).zipWith(Mono.just(city)))
                .map(tuple -> fromDAO(tuple.getT2(), tuple.getT1()));
    }

    public Mono<CityDTO> getCityById(String cityId) {
        return repository.findById(cityId)
                .zipWith(facilityService.getAllFacilitiesFromCity(cityId).collect(Collectors.toList()),
                        this::fromDAO);
    }

    public Mono<CityDTO> getCityByCode(String cityCode) {
        return repository.findByCode(cityCode)
                .flatMap(city -> facilityService.getAllFacilitiesFromCity(city.getId())
                        .collect(Collectors.toList()).zipWith(Mono.just(city)))
                .map(tuple -> fromDAO(tuple.getT2(), tuple.getT1()));
    }

    public Flux<CityDTO> getAllCities() {
        return repository.findAll()
                .flatMap(city -> facilityService.getAllFacilitiesFromCity(city.getId())
                        .collect(Collectors.toList()).zipWith(Mono.just(city)))
                .map(tuple -> fromDAO(tuple.getT2(), tuple.getT1()));
    }

    private CityDAO fromCreateCityDTO(CreateCityDTO cityData) {
        return new CityDAO(UUID.randomUUID().toString(), cityData.name(), cityData.code());
    }

    private CityDTO fromDAO(CityDAO cityDAO, List<FacilityDTO> facilities) {
        return new CityDTO(cityDAO.getId(), cityDAO.getName(), cityDAO.getCode(), facilities);
    }
}
