package com.lelar.database.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PictureEntity {
    private String localPath;
    private String serverPath;
    private String format;
}
