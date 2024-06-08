package com.lelar.controller.rest.beach;

import com.lelar.dto.BaseResponse;
import com.lelar.dto.tournament.get.GetTournamentDetailResponse;
import com.lelar.service.get.api.ObtainDataProcessor;
import com.lelar.dto.tournament.get.GetTournamentRequest;
import com.lelar.dto.tournament.get.GetBeachTournamentResponse;
import com.lelar.dto.tournament.update.UpdateBeachTournamentRequest;
import com.lelar.exception.ApplicationException;
import com.lelar.service.update.api.UpdateDataService;
import com.lelar.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tournament/beach")
public class BeachTournamentController {
    private final ObtainDataProcessor<GetTournamentRequest, GetBeachTournamentResponse> obtainDataProcessor;
    private final ObtainDataProcessor<Long, GetTournamentDetailResponse> obtainDetailDataProcessor;
    private final UpdateDataService<UpdateBeachTournamentRequest> updateService;

    @PostMapping(value = "/get", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<GetBeachTournamentResponse> getTournaments(@RequestBody GetTournamentRequest request) throws ApplicationException {
        return BaseResponse.successResponse(obtainDataProcessor.process(request));
    }

    @GetMapping(value = "/getDetails", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<GetTournamentDetailResponse> getTournaments(
        @RequestParam("id") Long id
    ) throws ApplicationException {
        return BaseResponse.successResponse(obtainDetailDataProcessor.process(id));
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<Void> updateTournament(
        @RequestBody UpdateBeachTournamentRequest request,
        @RequestHeader(Constants.SESSION_ID_HEADER) String sessionId
    ) throws ApplicationException {
        updateService.update(request);
        return BaseResponse.emptySuccessResponse();
    }

}
