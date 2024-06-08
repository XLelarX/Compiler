package com.lelar.database.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.relational.core.mapping.Table;

import static com.lelar.database.entity.PictureBindingEntity.Names.TABLE_NAME;

@Data
@Accessors(chain = true)
@Table(TABLE_NAME)
public class PictureBindingEntity extends CommonPictureBindingEntity<TournamentEntity> {
    public interface Names {
        String TABLE_NAME = "PICTURE_BINDINGS";
    }
}