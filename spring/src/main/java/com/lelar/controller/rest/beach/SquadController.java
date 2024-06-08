package com.lelar.controller.rest.beach;

import com.lelar.dto.BaseResponse;
import com.lelar.dto.squad.update.UpdateSquadRequest;
import com.lelar.exception.ApplicationException;
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
@RequestMapping("/squad")
public class SquadController {
    private final UpdateDataProcessor<UpdateSquadRequest> updateSquadDataProcessor;
    private final SessionStorage sessionStorage;

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<Long> updateSquad(
        @RequestBody UpdateSquadRequest request,
        @RequestHeader(Constants.SESSION_ID_HEADER) String sessionId
    ) throws ApplicationException {
        PermissionUtils.checkPermission(
            sessionStorage.pull(sessionId),
            Permissions.BEACH_TOURNAMENT_SQUAD_UPDATE
        );
        return BaseResponse.successResponse(updateSquadDataProcessor.update(request));
    }

}
