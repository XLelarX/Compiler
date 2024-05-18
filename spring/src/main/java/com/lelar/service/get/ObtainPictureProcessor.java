package com.lelar.service.get;

import com.lelar.dto.picture.get.GetPictureRequest;
import com.lelar.exception.StorePictureException;
import com.lelar.service.get.api.ObtainDataProcessor;
import com.lelar.exception.ApplicationException;
import com.lelar.storage.picture.api.StorePictureService;
import com.lelar.util.PrimitiveArrayWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ObtainPictureProcessor implements ObtainDataProcessor<GetPictureRequest, PrimitiveArrayWrapper> {

    private final StorePictureService storePictureService;

    @Override
    public PrimitiveArrayWrapper process(GetPictureRequest request) throws ApplicationException {
        try {
            return new PrimitiveArrayWrapper(storePictureService.getPicture(request.getServerPath()));
        } catch (IOException e) {
            e.printStackTrace();
            throw new StorePictureException();
        }
    }
}
