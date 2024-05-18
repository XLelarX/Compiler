package com.lelar.database.entity.id;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;

@Data
public class IdentifierEntity implements Persistable<Long> {
    @Id
    private Long id;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return id == null;
    }

}
