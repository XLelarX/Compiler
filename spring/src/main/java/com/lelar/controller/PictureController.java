package com.lelar.controller;

import com.lelar.collector.api.Collector;
import com.lelar.dto.PictureRequest;
import com.lelar.dto.tournament.TournamentRequest;
import com.lelar.dto.tournament.TournamentResponse;
import com.lelar.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pictures")
public class PictureController {
    private final Collector<TournamentRequest, TournamentResponse> collector;

    @GetMapping(
        value = "/get",
        produces = MediaType.IMAGE_JPEG_VALUE
    )
    @ResponseBody
    public byte[] getTournaments() throws ApplicationException, IOException {
        InputStream in = getClass()
            .getResourceAsStream("/pictures/image.jpg");
        return in.readAllBytes();
    }
}
