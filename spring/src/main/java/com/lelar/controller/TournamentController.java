package com.lelar.controller;

import com.lelar.service.get.api.ObtainDataProcessor;
import com.lelar.dto.tournament.get.GetTournamentRequest;
import com.lelar.dto.tournament.get.GetTournamentResponse;
import com.lelar.dto.tournament.update.UpdateTournamentRequest;
import com.lelar.dto.tournament.update.UpdateTournamentResponse;
import com.lelar.exception.ApplicationException;
import com.lelar.service.update.api.UpdateDataService;
import com.lelar.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tournament")
public class TournamentController {
    private final ObtainDataProcessor<GetTournamentRequest, GetTournamentResponse> obtainDataProcessor;
    private final UpdateDataService<UpdateTournamentRequest> updateService;

    @PostMapping(value = "/getBetween", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public GetTournamentResponse getTournaments(@RequestBody GetTournamentRequest request) throws ApplicationException {
        return obtainDataProcessor.process(request);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UpdateTournamentResponse updateTournament(
        @RequestBody UpdateTournamentRequest request,
        @RequestHeader(Constants.SESSION_ID_HEADER) String sessionId
    ) throws ApplicationException {
        boolean update = updateService.update(request);
        return new UpdateTournamentResponse();
    }

}
