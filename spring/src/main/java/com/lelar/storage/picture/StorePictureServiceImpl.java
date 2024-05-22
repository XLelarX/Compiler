package com.lelar.storage.picture;

import com.lelar.storage.picture.api.StorePictureService;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class StorePictureServiceImpl implements StorePictureService {
    private final static String PICTURE_BASE_PATH = "/Users/lelarious/Desktop/java_projects/spring-volleyball-app/spring/src/main/resources/pictures/%s.%s";

    @Override
    public Pair<String, String> storePicture(MultipartFile multipartFile) throws IOException {
        Pattern pattern = Pattern.compile("image/(\\w+)");
        Matcher matcher = pattern.matcher(multipartFile.getContentType());
        String fileType = "";
        if (matcher.find()) {
            fileType = matcher.group(1);
        }
        String fileName = UUID.randomUUID().toString().replace("-", "");

        String fileFullName = String.format(PICTURE_BASE_PATH, fileName, fileType);

        Files.write(Path.of(fileFullName), multipartFile.getInputStream().readAllBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);

        return Pair.of(fileType, fileFullName);
    }

    @Override
    public byte[] getPicture(String serverPath) throws IOException {
        return Files.readAllBytes(Path.of(serverPath));
    }

}
