package com.lelar.controller.rest.beach;

import com.lelar.dto.BaseResponse;
import com.lelar.dto.tournament.beach.get.GetTournamentDetailResponse;
import com.lelar.processor.get.api.ObtainDataProcessor;
import com.lelar.dto.tournament.GetTournamentRequest;
import com.lelar.dto.tournament.beach.get.GetBeachTournamentResponse;
import com.lelar.dto.tournament.beach.update.UpdateBeachTournamentRequest;
import com.lelar.exception.ApplicationException;
import com.lelar.processor.update.api.UpdateDataProcessor;
import com.lelar.storage.session.api.SessionStorage;
import com.lelar.util.Constants;
import com.lelar.util.PermissionUtils;
import com.lelar.util.Permissions;
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
    private final UpdateDataProcessor<UpdateBeachTournamentRequest> updateService;
    private final SessionStorage sessionStorage;

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
        PermissionUtils.checkPermission(
            sessionStorage.pull(sessionId),
            Permissions.BEACH_TOURNAMENT_UPDATE
        );

        updateService.update(request);
        return BaseResponse.emptySuccessResponse();
    }

}
