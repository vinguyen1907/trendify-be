package com.project.uit.trendify.user.controller;

import com.project.uit.trendify.common.lib.dto.ProductDTO;
import com.project.uit.trendify.common.lib.dto.UserDTO;
import com.project.uit.trendify.common.lib.request.UpdateUserRequest;
import com.project.uit.trendify.common.lib.entity.User;
import com.project.uit.trendify.user.service.interfaces.IUserService;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
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
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final IUserService userService;
    private final Map<String, Bucket> rateLimitBuckets;

    @GetMapping("/user")
    public ResponseEntity<UserDTO> getUserById() {
        ConsumptionProbe probe = consumptionProbe();
        if (probe.isConsumed()) {
            try {
                Long userId = getUserIdFromToken();
                return ResponseEntity.ok(userService.getUserById(userId));
            } catch (Exception e) {
                logger.error("Get User by ID ERROR: " + e.getMessage());
                return ResponseEntity.notFound().build();
            } finally {
                logger.info("Get User by ID request - remaining tokens: " + probe.getRemainingTokens());
            }
        } else {
            logger.warn("Too many requests to user-service");
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }

    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(
            @PathVariable String email
    ) {
        ConsumptionProbe probe = consumptionProbe();
        if (probe.isConsumed()) {
            return ResponseEntity.ok(userService.getUserByEmail(email));
        } else {
            logger.warn("Too many requests to user-service");
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable Long userId,
            @RequestBody UpdateUserRequest request
    ) {
        ConsumptionProbe probe = consumptionProbe();
        if (probe.isConsumed()) {
            return ResponseEntity.ok(userService.updateUser(userId, request));
        } else {
            logger.warn("Too many requests to user-service");
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }
    }

    @GetMapping("/user/recommend")
    public ResponseEntity<List<ProductDTO>> recommendUser(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer size
    ) {
        ConsumptionProbe probe = consumptionProbe();
        if (probe.isConsumed()) {
            logger.info("Get recommend products request - page {} - size {}", page, size);
            Long userId = getUserIdFromToken();
            List<ProductDTO> products = userService.getRecommendProducts(userId, page, size);
            logger.info("Response to recommend products request" + products);
            return ResponseEntity.ok(products);
        } else {
            logger.warn("Too many requests to user-service");
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }
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

    ConsumptionProbe consumptionProbe() {
        Bucket bucket = rateLimitBuckets.get("user-service");
        return bucket.tryConsumeAndReturnRemaining(1);
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
