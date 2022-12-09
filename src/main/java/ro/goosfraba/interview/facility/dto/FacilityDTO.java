package ro.goosfraba.interview.facility.dto;

import ro.goosfraba.interview.enums.VehicleType;

public record FacilityDTO(
        String id,
        String name,
        VehicleType type,
        String cityId,
        Integer capacity,
        Integer availableCapacity
) {
}
