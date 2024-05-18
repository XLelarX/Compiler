package com.lelar.database.entity;

import com.lelar.database.annotation.Sequence;
import com.lelar.database.entity.id.IdentifierEntity;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import static com.lelar.database.entity.PictureEntity.Names.FORMAT_ID;
import static com.lelar.database.entity.PictureEntity.Names.LOCAL_PATH;
import static com.lelar.database.entity.PictureEntity.Names.SEQUENCE_NAME;
import static com.lelar.database.entity.PictureEntity.Names.SERVER_PATH;
import static com.lelar.database.entity.PictureEntity.Names.TABLE_NAME;

@Data
@Accessors(chain = true)
@Table(TABLE_NAME)
@Sequence(SEQUENCE_NAME)
public class PictureEntity extends IdentifierEntity {

    @Column(LOCAL_PATH)
    private String localPath;

    @Column(SERVER_PATH)
    private String serverPath;

    @Column(FORMAT_ID)
    private AggregateReference<PictureFormatEntity, Long> formatId;

    interface Names {
        String TABLE_NAME = "PICTURES";

        String SEQUENCE_NAME = "SEQ_PK_PICTURE_ID";
        String LOCAL_PATH = "LOCAL_PATH";
        String SERVER_PATH = "SERVER_PATH";
        String FORMAT_ID = "FORMAT_ID";
    }

}
