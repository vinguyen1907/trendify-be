package com.project.uit.trendify.user.controller;

import com.project.uit.trendify.common.lib.request.SignInRequest;
import com.project.uit.trendify.common.lib.request.SignUpRequest;
import com.project.uit.trendify.common.lib.response.AuthenticationResponse;
import com.project.uit.trendify.service.interfaces.IAuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
public class AuthenticationController {
    private final IAuthenticationService authenticationService;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> signIn(
            @RequestBody SignInRequest request
    ) {
        logger.info("Sign in request: " + request.toString());
        var response = authenticationService.signIn(request);
        logger.info("Sign in response: " + response.toString());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> signUp(
            @Valid @RequestBody SignUpRequest request
    ) {
        logger.info("Sign up request: " + request.toString());
        return ResponseEntity.ok(authenticationService.signUp(request));
    }
}
