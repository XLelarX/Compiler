package com.lelar.controller;

import com.lelar.service.get.api.ObtainDataProcessor;
import com.lelar.dto.PictureRequest;
import com.lelar.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pictures")
public class PictureController {
    private final ObtainDataProcessor<PictureRequest, InputStream> obtainDataProcessor;

    @PostMapping(
        value = "/getByPath",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.IMAGE_JPEG_VALUE
    )
    @ResponseBody
    public byte[] getPicture(@RequestBody PictureRequest request) throws ApplicationException, IOException {
        InputStream in = obtainDataProcessor.process(request);

        return in.readAllBytes();
    }
}
