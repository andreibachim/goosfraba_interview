package ro.goosfraba.interview.city.dao;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

@Table("city")
@Data
@RequiredArgsConstructor
public class CityDAO implements Persistable<String> {
    @Id
    private final String id;
    private final String name;
    private final String code;

    @Override
    @Transient
    public boolean isNew() {
        return true;
    }
}
