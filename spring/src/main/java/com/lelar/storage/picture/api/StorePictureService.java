package com.lelar.storage.picture.api;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorePictureService {
    Pair<String, String> storePicture(MultipartFile multipartFile) throws IOException;

    byte[] getPicture(String serverPath) throws IOException;
}
