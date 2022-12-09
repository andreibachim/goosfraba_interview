package ro.goosfraba.interview.vehicle.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import ro.goosfraba.interview.constants.ValidationConstants;
import ro.goosfraba.interview.enums.VehicleType;

public record CreateVehicleDTO(
        @NotEmpty
        @Pattern(regexp = ValidationConstants.UUID_PATTERN,
                 message = "City ID parameter does not match the expected format")
        String cityId,
        VehicleType type) {
}
