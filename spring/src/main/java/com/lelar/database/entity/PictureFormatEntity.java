package com.lelar.database.entity;

import com.lelar.database.annotation.Sequence;
import com.lelar.database.entity.id.IdentifierEntity;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import static com.lelar.database.entity.PictureFormatEntity.Names.FORMAT;
import static com.lelar.database.entity.PictureFormatEntity.Names.SEQUENCE_NAME;
import static com.lelar.database.entity.PictureFormatEntity.Names.TABLE_NAME;

@Data
@Accessors(chain = true)
@Table(TABLE_NAME)
@Sequence(SEQUENCE_NAME)
public class PictureFormatEntity extends IdentifierEntity {

    @Column(FORMAT)
    private String format;

    interface Names {
        String TABLE_NAME = "PICTURE_FORMATS";

        String SEQUENCE_NAME = "SEQ_PK_PICTURE_FORMATS_ID";

        String FORMAT = "FORMAT";
    }

}
