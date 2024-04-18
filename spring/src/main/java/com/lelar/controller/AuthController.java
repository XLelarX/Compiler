package com.lelar.controller;

import com.lelar.collector.api.Collector;
import com.lelar.dto.BaseResponse;
import com.lelar.dto.login.LoginRequest;
import com.lelar.dto.login.LoginResponse;
import com.lelar.exception.ApplicationException;
import com.lelar.mapper.SessionDataMapper;
import com.lelar.session.storage.api.SessionStorage;
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
    private final Collector<LoginRequest, LoginResponse> collector;

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public LoginResponse login(@RequestBody LoginRequest request, @RequestHeader("Session-Id") String sessionId) throws ApplicationException {
        LoginResponse response = collector.collect(request);

        sessionStorage.put(sessionId, SessionDataMapper.INSTANCE.map(response));

        return response;
    }

    @GetMapping(path = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<String> logout(@RequestHeader("Session-Id") String sessionId) {
        sessionStorage.clean(sessionId);

        return BaseResponse.<String>builder()
                .success(true)
                .build();
    }

}
