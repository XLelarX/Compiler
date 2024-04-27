package com.lelar.controller;

import com.lelar.service.get.api.GetService;
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
    private final GetService<PictureRequest, InputStream> getService;

    @PostMapping(
        value = "/getByPath",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.IMAGE_JPEG_VALUE
    )
    @ResponseBody
    public byte[] getPicture(@RequestBody PictureRequest request) throws ApplicationException, IOException {
        InputStream in = getService.get(request);

        return in.readAllBytes();
    }
}
