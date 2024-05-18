package com.lelar.dto.picture.update;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdatePictureRequest {
    private MultipartFile file;
    private String localPath;
    private Long tournamentId;
}
