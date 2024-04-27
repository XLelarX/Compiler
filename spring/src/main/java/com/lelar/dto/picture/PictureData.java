package com.lelar.dto.picture;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PictureData {
    private String serverPath;
    private String localPath;
    private String format;
}
