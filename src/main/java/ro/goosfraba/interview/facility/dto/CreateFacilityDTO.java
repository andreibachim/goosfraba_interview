package ro.goosfraba.interview.facility.dto;

import jakarta.validation.constraints.*;
import ro.goosfraba.interview.constants.ValidationConstants;
import ro.goosfraba.interview.enums.VehicleType;

public record CreateFacilityDTO(
        @NotEmpty(message = "Facility 'name' parameter is missing")
        @Size(max = ValidationConstants.NAME_MAX_SIZE, message = "Facility name exceed the maximum character limit.")
        String name,
        @NotEmpty
        @Pattern(regexp = ValidationConstants.UUID_PATTERN, message = "City ID parameter has incorrect format.")
        String cityId,
        @Min(1)
        @NotNull
        Integer capacity,
        @NotNull
        VehicleType type
) {
}
