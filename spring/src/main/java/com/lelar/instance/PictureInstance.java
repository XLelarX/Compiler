package com.lelar.instance;

import lombok.Data;

@Data
public class PictureInstance {
    private Long id;
    private String localPath;
    private String serverPath;
    private String format;
}
