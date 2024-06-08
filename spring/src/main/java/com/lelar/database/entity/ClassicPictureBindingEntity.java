package com.lelar.database.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.relational.core.mapping.Table;

import static com.lelar.database.entity.ClassicPictureBindingEntity.Names.TABLE_NAME;

@Data
@Accessors(chain = true)
@Table(TABLE_NAME)
public class ClassicPictureBindingEntity extends CommonPictureBindingEntity<ClassicTournamentEntity> {

    public interface Names {
        String TABLE_NAME = "CLASSIC_PICTURE_BINDINGS";
    }
}