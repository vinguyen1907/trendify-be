package com.project.uit.trendify.common.lib.dto;

import com.project.uit.trendify.common.lib.enums.Gender;
import com.project.uit.trendify.common.lib.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String email;
    private String name;
    private String imageUrl;
    private Gender gender;
    private Integer age;
    private String fcmToken;
    private Role role;
}
