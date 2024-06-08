package com.lelar.controller.rest.beach;

import com.lelar.dto.BaseResponse;
import com.lelar.dto.picture.get.GetPictureRequest;
import com.lelar.dto.picture.update.UpdatePictureRequest;
import com.lelar.processor.get.api.ObtainDataProcessor;
import com.lelar.exception.ApplicationException;
import com.lelar.processor.update.api.UpdateDataProcessor;
import com.lelar.storage.session.api.SessionStorage;
import com.lelar.util.Constants;
import com.lelar.util.Permissions;
import com.lelar.util.PermissionUtils;
import com.lelar.util.PrimitiveArrayWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/picture")
public class PictureController {
    private final SessionStorage sessionStorage;
    private final ObtainDataProcessor<GetPictureRequest, PrimitiveArrayWrapper> obtainDataProcessor;
    private final UpdateDataProcessor<UpdatePictureRequest> updateDataProcessor;

    @PostMapping(
        value = "/getByPath",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.IMAGE_JPEG_VALUE
    )
    public byte[] getPicture(@RequestBody GetPictureRequest request) throws ApplicationException {
        return obtainDataProcessor.process(request).getByteArray();
    }

    @PostMapping(
        value = "/store",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public BaseResponse<Void> storePicture(
        @RequestPart("image") MultipartFile image,
        @RequestParam("localPath") String localPath,
        @RequestParam("tournamentId") Long tournamentId,
        @RequestHeader(Constants.SESSION_ID_HEADER) String sessionId
    ) throws ApplicationException {
        PermissionUtils.checkPermission(
            sessionStorage.pull(sessionId),
            Permissions.PICTURE_UPDATE
        );

        updateDataProcessor.update(createGetPictureRequest(image, localPath, tournamentId));
        return BaseResponse.emptySuccessResponse();
    }

    private UpdatePictureRequest createGetPictureRequest(
        MultipartFile image,
        String localPath,
        Long tournamentId
    ) {
        UpdatePictureRequest updatePictureRequest = new UpdatePictureRequest();
        updatePictureRequest.setFile(image);
        updatePictureRequest.setLocalPath(localPath);
        updatePictureRequest.setTournamentId(tournamentId);
        return updatePictureRequest;
    }
}
