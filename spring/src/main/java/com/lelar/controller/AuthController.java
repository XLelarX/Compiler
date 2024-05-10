package com.lelar.controller;

import com.lelar.dto.login.register.RegisterRequest;
import com.lelar.service.get.api.ObtainDataProcessor;
import com.lelar.dto.BaseResponse;
import com.lelar.dto.login.LoginRequest;
import com.lelar.dto.login.LoginResponse;
import com.lelar.exception.ApplicationException;
import com.lelar.mapper.SessionDataMapper;
import com.lelar.session.storage.api.SessionStorage;
import com.lelar.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final SessionStorage sessionStorage;
    private final ObtainDataProcessor<LoginRequest, LoginResponse> obtainDataProcessor;
    private final ObtainDataProcessor<RegisterRequest, LoginResponse> registerService;

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResponse<LoginResponse> login(@RequestBody LoginRequest request, @RequestHeader(Constants.SESSION_ID_HEADER) String sessionId) throws ApplicationException {
        LoginResponse response = obtainDataProcessor.process(request);

        sessionStorage.put(sessionId, SessionDataMapper.INSTANCE.map(response));

        return BaseResponse.successResponse(response);
    }

    @GetMapping(path = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<Void> logout(@RequestHeader(Constants.SESSION_ID_HEADER) String sessionId) {
        sessionStorage.clean(sessionId);

        return BaseResponse.emptySuccessResponse();
    }

    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResponse<LoginResponse> register(@RequestBody RegisterRequest request, @RequestHeader(Constants.SESSION_ID_HEADER) String sessionId) throws ApplicationException {
        LoginResponse response = registerService.process(request);

        sessionStorage.put(sessionId, SessionDataMapper.INSTANCE.map(response));

        return BaseResponse.successResponse(response);
    }

}
