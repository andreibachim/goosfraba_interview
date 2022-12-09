package ro.goosfraba.interview.city.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import ro.goosfraba.interview.constants.ValidationConstants;

public record CreateCityDTO(
        @Size(max = ValidationConstants.NAME_MAX_SIZE, message = "City name exceeds max character limit.")
        @NotEmpty(message = "City 'name' parameter not present.")
        String name,
        @Size(max = 4, message = "Country code exceeds maximum character limit.")
        @NotEmpty(message = "City 'code' parameter not present.")
        String code) {
}