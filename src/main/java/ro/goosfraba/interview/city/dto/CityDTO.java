package ro.goosfraba.interview.city.dto;

import ro.goosfraba.interview.facility.dto.FacilityDTO;

import java.util.List;

public record CityDTO(String id, String name, String code, List<FacilityDTO> facilities) {
}