package com.project.uit.trendify.service.interfaces;

import com.project.uit.trendify.common.lib.request.SignInRequest;
import com.project.uit.trendify.common.lib.request.SignUpRequest;
import com.project.uit.trendify.common.lib.response.AuthenticationResponse;

public interface IAuthenticationService {
    AuthenticationResponse signIn(SignInRequest request);
    AuthenticationResponse signUp(SignUpRequest request);
}
