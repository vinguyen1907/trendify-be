package com.project.uit.trendify.common.lib.entity;

import com.project.uit.trendify.common.lib.enums.Gender;
import com.project.uit.trendify.common.lib.enums.Role;

import java.time.LocalDateTime;

public final class UserBuilder {
    private User user;

    private UserBuilder() {
        user = new User();
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public UserBuilder id(Long id) {
        user.setId(id);
        return this;
    }

    public UserBuilder email(String email) {
        user.setEmail(email);
        return this;
    }

    public UserBuilder password(String password) {
        user.setPassword(password);
        return this;
    }

    public UserBuilder name(String name) {
        user.setName(name);
        return this;
    }

    public UserBuilder imageUrl(String imageUrl) {
        user.setImageUrl(imageUrl);
        return this;
    }

    public UserBuilder gender(Gender gender) {
        user.setGender(gender);
        return this;
    }

    public UserBuilder age(Integer age) {
        user.setAge(age);
        return this;
    }

    public UserBuilder fcmToken(String fcmToken) {
        user.setFcmToken(fcmToken);
        return this;
    }

    public UserBuilder role(Role role) {
        user.setRole(role);
        return this;
    }

    public UserBuilder createdAt(LocalDateTime createdAt) {
        user.setCreatedAt(createdAt);
        return this;
    }

    public UserBuilder updatedAt(LocalDateTime updatedAt) {
        user.setUpdatedAt(updatedAt);
        return this;
    }

    public User build() {
        return user;
    }
}
