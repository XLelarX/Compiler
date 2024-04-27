package com.lelar.controller;

import com.lelar.dto.login.RegisterRequest;
import com.lelar.service.get.api.GetService;
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
    private final GetService<LoginRequest, LoginResponse> getService;
    private final GetService<RegisterRequest, LoginResponse> registerService;

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public LoginResponse login(@RequestBody LoginRequest request, @RequestHeader(Constants.SESSION_ID_HEADER) String sessionId) throws ApplicationException {
        LoginResponse response = getService.get(request);

        sessionStorage.put(sessionId, SessionDataMapper.INSTANCE.map(response));

        return response;
    }

    @GetMapping(path = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<String> logout(@RequestHeader(Constants.SESSION_ID_HEADER) String sessionId) {
        sessionStorage.clean(sessionId);

        return BaseResponse.<String>builder()
            .success(true)
            .build();
    }

    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public LoginResponse register(@RequestBody RegisterRequest request, @RequestHeader(Constants.SESSION_ID_HEADER) String sessionId) throws ApplicationException {
        LoginResponse response = registerService.get(request);

        sessionStorage.put(sessionId, SessionDataMapper.INSTANCE.map(response));

        return response;
    }

}
