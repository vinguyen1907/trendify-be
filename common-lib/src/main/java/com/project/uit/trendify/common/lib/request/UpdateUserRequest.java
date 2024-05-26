package com.project.uit.trendify.common.lib.request;

import com.project.uit.trendify.common.lib.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    private String name;
    private Gender gender;
    private Integer age;
    private String fcmToken;
}
