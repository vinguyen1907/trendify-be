package com.project.uit.trendify.common.lib.response;

import com.project.uit.trendify.common.lib.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AuthenticationResponse {
    private String accessToken;
    private String tokenType;
    private UserDTO user;
}
