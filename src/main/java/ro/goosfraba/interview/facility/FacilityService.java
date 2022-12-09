package ro.goosfraba.interview.facility;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ro.goosfraba.interview.facility.dao.FacilityDAO;
import ro.goosfraba.interview.facility.dto.CreateFacilityDTO;
import ro.goosfraba.interview.facility.dto.FacilityDTO;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FacilityService {
    private final FacilityRepository repository;
    public Mono<FacilityDTO> saveFacility(Mono<CreateFacilityDTO> facilityData) {
        return facilityData
                .map(this::fromCreateFacilityDTO)
                .flatMap(repository::save)
                .map(this::fromDAO);
    }

    public Mono<FacilityDTO> getFacility(String facilityId) {
        return repository.findById(facilityId)
                .map(this::fromDAO);
    }

    public Flux<FacilityDTO> getAllFacilitiesFromCity(String cityId) {
        return repository.findAllByCityId(cityId)
                .map(this::fromDAO);
    }

    private FacilityDAO fromCreateFacilityDTO(CreateFacilityDTO facilityData) {
        return new FacilityDAO(
                UUID.randomUUID().toString(),
                facilityData.name(),
                facilityData.type(),
                facilityData.cityId(),
                facilityData.capacity(),
                facilityData.capacity(),
                true
        );
    }

    private FacilityDTO fromDAO(FacilityDAO facilityDAO) {
        return new FacilityDTO(facilityDAO.getId(),
                facilityDAO.getName(),
                facilityDAO.getType(),
                facilityDAO.getCityId(),
                facilityDAO.getCapacity(),
                facilityDAO.getAvailableCapacity());
    }
}
