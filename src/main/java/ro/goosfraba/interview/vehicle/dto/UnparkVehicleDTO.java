package ro.goosfraba.interview.vehicle.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import ro.goosfraba.interview.constants.ValidationConstants;

public record UnparkVehicleDTO(
        @NotBlank(message = "Vehicle ID is missing.")
        @Pattern(regexp = ValidationConstants.UUID_PATTERN, message = "Vehicle ID incorrect format.")
        String vehicleId) {
}
