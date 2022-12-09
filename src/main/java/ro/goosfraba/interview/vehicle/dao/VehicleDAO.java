package ro.goosfraba.interview.vehicle.dao;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import ro.goosfraba.interview.enums.VehicleType;

@Table("vehicle")
@Data
@AllArgsConstructor
public class VehicleDAO implements Persistable<String> {

    @PersistenceCreator
    public VehicleDAO(String id, VehicleType type, String cityId, Boolean isParked, String facilityId) {
        this.id = id;
        this.type = type;
        this.cityId = cityId;
        this.isParked = isParked;
        this.facilityId = facilityId;
    }

    @Id
    private final String id;
    private final VehicleType type;
    @Column(value = "city_id")
    private final String cityId;
    @Column(value = "is_parked")
    private final Boolean isParked;
    @Column(value = "facility_id")
    private final String facilityId;

    @JsonInclude
    @Transient
    private boolean isNew;
    @Override
    @Transient
    public boolean isNew() {
        return isNew;
    }
}
