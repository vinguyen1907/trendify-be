package com.project.uit.trendify.user.service;

import com.project.uit.trendify.common.lib.entity.UserBuilder;
import com.project.uit.trendify.common.lib.service.JwtService;
import com.project.uit.trendify.common.lib.enums.TokenType;
import com.project.uit.trendify.common.lib.request.SignInRequest;
import com.project.uit.trendify.common.lib.request.SignUpRequest;
import com.project.uit.trendify.common.lib.response.AuthenticationResponse;
import com.project.uit.trendify.service.interfaces.IAuthenticationService;
import com.project.uit.trendify.common.lib.entity.User;
import com.project.uit.trendify.user.exception_handler.custom_exception.DuplicateEmailException;
import com.project.uit.trendify.common.lib.repository.UserRepository;
import com.project.uit.trendify.user.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {
    private final UserRepository userRepository;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final PasswordUtil passwordUtil;
    private final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    @Override
    public AuthenticationResponse signIn(SignInRequest request) {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            var user = userRepository
                    .findByEmail(request.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("This account is not found"));
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .tokenType(TokenType.BEARER.getTokenType())
                    .user(user.toDTO())
                    .build();
        } catch (UsernameNotFoundException e) {
            logger.error("This account is not found", e);
            throw new UsernameNotFoundException("This account is not found", e);
        } catch (BadCredentialsException e) {
            logger.error("User not found or Incorrect password", e);
            throw new BadCredentialsException("User not found or Incorrect password", e);
        }
    }

    @Override
    public AuthenticationResponse signUp(SignUpRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("Password and confirm password are not matched");
        }

        boolean isEmailExist = userRepository.existsByEmail(request.getEmail());
        if (isEmailExist) {
            throw new DuplicateEmailException();
        }

        var user = UserBuilder.builder()
                .email(request.getEmail())
                .password(passwordUtil.encodePassword(request.getPassword()))
                .name(request.getName())
                .role(request.getRole())
                .build();
        User savedUser = userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .tokenType(TokenType.BEARER.getTokenType())
                .user(savedUser.toDTO())
                .build();
    }
}
