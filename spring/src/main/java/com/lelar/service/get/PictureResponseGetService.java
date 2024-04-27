package com.lelar.service.get;

import com.lelar.service.get.api.GetService;
import com.lelar.dto.PictureRequest;
import com.lelar.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class PictureResponseGetService implements GetService<PictureRequest, InputStream> {

    @Override
    public InputStream get(PictureRequest request) throws ApplicationException {
        return getClass().getResourceAsStream(request.getPath());
    }
}
