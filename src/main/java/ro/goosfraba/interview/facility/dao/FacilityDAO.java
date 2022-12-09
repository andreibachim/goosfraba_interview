package ro.goosfraba.interview.facility.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import ro.goosfraba.interview.enums.VehicleType;

@Table("facility")
@Data
@AllArgsConstructor
public class FacilityDAO implements Persistable<String> {
    @PersistenceCreator
    public FacilityDAO(String id, String name, VehicleType type, String cityId, Integer capacity, Integer availableCapacity) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.cityId = cityId;
        this.capacity = capacity;
        this.availableCapacity = availableCapacity;
    }

    @Id
    private final String id;
    private final String name;
    private final VehicleType type;
    @Column(value = "city_id")
    private final String cityId;
    private final Integer capacity;
    @ReadOnlyProperty
    @Column(value = "available_capacity")
    private final Integer availableCapacity;

    @Transient
    @JsonIgnore
    private boolean isNew;

    @Override
    @Transient
    public boolean isNew() {
        return isNew;
    }
}
