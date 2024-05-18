package com.lelar.controller.rest;

import com.lelar.dto.BaseResponse;
import com.lelar.dto.picture.get.GetPictureRequest;
import com.lelar.dto.picture.update.UpdatePictureRequest;
import com.lelar.service.get.api.ObtainDataProcessor;
import com.lelar.exception.ApplicationException;
import com.lelar.service.update.api.UpdateDataService;
import com.lelar.util.PrimitiveArrayWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/picture")
public class PictureController {
    private final ObtainDataProcessor<GetPictureRequest, PrimitiveArrayWrapper> obtainDataProcessor;
    private final UpdateDataService<UpdatePictureRequest> updateDataService;

    @PostMapping(
        value = "/getByPath",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.IMAGE_JPEG_VALUE
    )
    @ResponseBody
    public byte[] getPicture(@RequestBody GetPictureRequest request) throws ApplicationException {
        return obtainDataProcessor.process(request).getByteArray();
    }

    @PostMapping(
        value = "/store",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public BaseResponse<Void> getPicture(
        @RequestPart("image") MultipartFile image,
        @RequestParam("localPath") String localPath,
        @RequestParam("tournamentId") Long tournamentId
    ) throws ApplicationException {
        updateDataService.update(createGetPictureRequest(image, localPath, tournamentId));
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
