package com.lelar.controller.rest.classic;

import com.lelar.dto.BaseResponse;
import com.lelar.dto.tournament.classic.get.GetClassicTournamentResponse;
import com.lelar.dto.tournament.classic.update.UpdateClassicTournamentRequest;
import com.lelar.dto.tournament.GetTournamentRequest;
import com.lelar.processor.get.api.ObtainDataProcessor;
import com.lelar.processor.update.api.UpdateDataProcessor;
import com.lelar.storage.session.api.SessionStorage;
import com.lelar.util.Constants;
import com.lelar.util.Permissions;
import com.lelar.util.PermissionUtils;
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
    private final SessionStorage sessionStorage;
    private final ObtainDataProcessor<GetTournamentRequest, GetClassicTournamentResponse> obtainDataProcessor;
    private final UpdateDataProcessor<UpdateClassicTournamentRequest> updateService;

    @PostMapping(value = "/get", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<GetClassicTournamentResponse> getTournaments(
        @RequestBody GetTournamentRequest request
    ) {
        return BaseResponse.successResponse(obtainDataProcessor.process(request));
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<Long> updateTournament(
        @RequestBody UpdateClassicTournamentRequest request,
        @RequestHeader(Constants.SESSION_ID_HEADER) String sessionId
    ) {
        PermissionUtils.checkPermission(
            sessionStorage.pull(sessionId),
            Permissions.CLASSIC_TOURNAMENT_UPDATE
        );

        updateService.update(request);
        return BaseResponse.emptySuccessResponse();
    }

}
