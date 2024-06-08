package com.lelar.controller.rest.classic;

import com.lelar.dto.BaseResponse;
import com.lelar.dto.tournament.classic.get.GetClassicTournamentResponse;
import com.lelar.dto.tournament.classic.update.UpdateClassicTournamentRequest;
import com.lelar.dto.tournament.get.GetTournamentDetailResponse;
import com.lelar.dto.tournament.get.GetTournamentRequest;
import com.lelar.dto.tournament.update.UpdateBeachTournamentRequest;
import com.lelar.exception.ApplicationException;
import com.lelar.service.get.api.ObtainDataProcessor;
import com.lelar.service.update.api.UpdateDataService;
import com.lelar.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tournament/classic")
public class ClassicTournamentController {
    private final ObtainDataProcessor<GetTournamentRequest, GetClassicTournamentResponse> obtainDataProcessor;
    private final UpdateDataService<UpdateClassicTournamentRequest> updateService;

    @PostMapping(value = "/get", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<GetClassicTournamentResponse> getTournaments(
        @RequestBody GetTournamentRequest request
    ) throws ApplicationException {
        return BaseResponse.successResponse(obtainDataProcessor.process(request));
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<Long> updateTournament(
        @RequestBody UpdateClassicTournamentRequest request,
        @RequestHeader(Constants.SESSION_ID_HEADER) String sessionId
    ) throws ApplicationException {
        updateService.update(request);
        return BaseResponse.emptySuccessResponse();
    }

}
