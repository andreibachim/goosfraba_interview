package ro.goosfraba.interview.vehicle.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import ro.goosfraba.interview.enums.VehicleType;

public record VehicleDTO(String id, VehicleType type, String cityId, boolean isParked,
                         @JsonInclude(JsonInclude.Include.NON_NULL) String facilityId) {
}
