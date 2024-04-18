package com.lelar.controller;

import com.lelar.collector.api.Collector;
import com.lelar.dto.tournament.TournamentRequest;
import com.lelar.dto.tournament.TournamentResponse;
import com.lelar.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tournament")
public class TournamentController {
    private final Collector<TournamentRequest, TournamentResponse> collector;

    @PostMapping(value = "/getBetween", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public TournamentResponse getTournaments(@RequestBody TournamentRequest request) throws ApplicationException {
        System.out.println(request);
        return collector.collect(request);
    }
}
