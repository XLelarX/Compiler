package com.lelar.service.get;

import com.lelar.service.get.api.ObtainDataProcessor;
import com.lelar.dto.PictureRequest;
import com.lelar.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class PictureProcessor implements ObtainDataProcessor<PictureRequest, InputStream> {

    @Override
    public InputStream process(PictureRequest request) throws ApplicationException {
        return getClass().getResourceAsStream(request.getPath());
    }
}
