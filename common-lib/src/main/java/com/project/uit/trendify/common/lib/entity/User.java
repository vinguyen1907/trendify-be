package com.project.uit.trendify.common.lib.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.uit.trendify.common.lib.dto.UserDTO;
import com.project.uit.trendify.common.lib.enums.Gender;
import com.project.uit.trendify.common.lib.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String email;

    @JsonIgnore
    private String password;

    private String name;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Integer age;

    private String fcmToken;

    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    void onCreate() {
        this.createdAt = this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public UserDTO toDTO() {
        return new UserDTO(
                this.id,
                this.email,
                this.name,
                this.imageUrl,
                this.gender,
                this.age,
                this.fcmToken,
                this.role
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // TODO: Change this to implement account expiration
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // TODO: Change this to implement account expiration
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


//    public static class Builder {
//        private Long id;
//        private String email;
//        private String password;
//        private String name;
//        private String imageUrl;
//        private Gender gender;
//        private Integer age;
//        private String fcmToken;
//        private Role role;
//
//        public Builder id(Long id) {
//            this.id = id;
//            return this;
//        }
//
//        public Builder email(String email) {
//            this.email = email;
//            return this;
//        }
//
//        public Builder password(String password) {
//            this.password = password;
//            return this;
//        }
//
//        public Builder name(String name) {
//            this.name = name;
//            return this;
//        }
//
//        public Builder imageUrl(String imageUrl) {
//            this.imageUrl = imageUrl;
//            return this;
//        }
//
//        public Builder gender(Gender gender) {
//            this.gender = gender;
//            return this;
//        }
//
//        public Builder age(Integer age) {
//            this.age = age;
//            return this;
//        }
//
//        public Builder fcmToken(String fcmToken) {
//            this.fcmToken = fcmToken;
//            return this;
//        }
//
//        public Builder role(Role role) {
//            this.role = role;
//            return this;
//        }
//
//        public User build() {
//            User user = new User();
//            user.id = this.id;
//            user.email = this.email;
//            user.password = this.password;
//            user.name = this.name;
//            user.imageUrl = this.imageUrl;
//            user.gender = this.gender;
//            user.age = this.age;
//            user.role = this.role;
//            return user;
//        }
//    }
}
