package com.project.uit.trendify.user.controller;

import com.project.uit.trendify.common.lib.dto.ProductDTO;
import com.project.uit.trendify.common.lib.dto.UserDTO;
import com.project.uit.trendify.common.lib.request.UpdateUserRequest;
import com.project.uit.trendify.service.interfaces.IUserService;
import com.project.uit.trendify.common.lib.entity.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final IUserService userService;

    @GetMapping("/user")
    public ResponseEntity<UserDTO> getUserById() {
        try {
            Long userId = getUserIdFromToken();
            return ResponseEntity.ok(userService.getUserById(userId));
        } catch (Exception e) {
            logger.error("Get User by ID ERROR: " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(
            @PathVariable String email
    ) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable Long userId,
            @RequestBody UpdateUserRequest request
    ) {
        return ResponseEntity.ok(userService.updateUser(userId, request));
    }

    @GetMapping("/user/recommend")
    public ResponseEntity<List<ProductDTO>> recommendUser() {
        logger.info("Get recommend products request");
        Long userId = getUserIdFromToken();
        List<ProductDTO> products = userService.getRecommendProducts(userId);
        logger.info("Response to recommend products request" + products);
        return ResponseEntity.ok(products);
    }

    private Long getUserIdFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            User userDetails = (User) authentication.getPrincipal();
            Long userId = userDetails.getId();
            logger.info("Get User by ID: " + userId);
            return userId;
        }
        throw new UsernameNotFoundException("User not found");
    }

//    public String getCurrentAuthToken() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication instanceof JwtAuthenticationToken) {
//            JwtAuthenticationToken jwtAuthToken = (JwtAuthenticationToken) authentication;
//            return jwtAuthToken.getToken().getTokenValue();
//        }
//
//        return null; // hoặc xử lý khi không có token
//    }
}
