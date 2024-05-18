//package com.lelar.controller.mvc;
//
//import com.lelar.dto.BaseResponse;
//import com.lelar.dto.login.LoginRequest;
//import com.lelar.dto.login.LoginResponse;
//import com.lelar.exception.ApplicationException;
//import com.lelar.mapper.SessionDataMapper;
//import com.lelar.util.Constants;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//@Controller
//@RequestMapping("/mvc")
//@RequiredArgsConstructor
//public class AuthController {
//
//    @RequestMapping(serverPath = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseBody
//    public BaseResponse<LoginResponse> login(@Validated @RequestBody LoginRequest request, @RequestHeader(Constants.SESSION_ID_HEADER) String sessionId) throws ApplicationException {
//        LoginResponse response = obtainDataProcessor.process(request);
//
//        sessionStorage.put(sessionId, SessionDataMapper.INSTANCE.map(response));
//
//        return BaseResponse.successResponse(response);
//    }
//
//    @GetMapping(serverPath = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
//    public BaseResponse<Void> logout(@RequestHeader(Constants.SESSION_ID_HEADER) String sessionId) {
//        sessionStorage.clean(sessionId);
//
//        return BaseResponse.emptySuccessResponse();
//    }
//}
