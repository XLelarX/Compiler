package com.lelar.instance;

import com.lelar.database.entity.PictureFormatEntity;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;

@Data
public class PictureInstance {
    private Long id;
    private String localPath;
    private String serverPath;
    private String format;
}
