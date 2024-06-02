package com.project.uit.trendify.common.lib.util;

import com.project.uit.trendify.common.lib.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ExtractTokenUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExtractTokenUtil.class);

    public Long getUserIdFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            User userDetails = (User) authentication.getPrincipal();
            Long userId = userDetails.getId();
            LOGGER.info("User ID extracted from token: " + userId);
            return userId;
        }
        throw new UsernameNotFoundException("User not found");
    }
}
