package com.project.uit.trendify.config;

import com.project.uit.trendify.common.lib.entity.User;
import com.project.uit.trendify.common.lib.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
@RequiredArgsConstructor
public class WebSocketBrokerConfig implements WebSocketMessageBrokerConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketBrokerConfig.class);
    private final JwtService jwtService;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOrigins("*");
//                .withSockJS();
//        registry.addEndpoint("/gs-guide-websocket");
    }

//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        registration.interceptors(new ChannelInterceptor() {
//            @Override
//            public Message<?> preSend(Message<?> message, MessageChannel channel) {
//                StompHeaderAccessor accessor =
//                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
//                logger.info("Headers: {}", accessor);
//
//                assert accessor != null;
//                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
//
//                    String authorizationHeader = accessor.getFirstNativeHeader("Authorization");
//                    assert authorizationHeader != null;
//                    String token = authorizationHeader.substring(7);
//                    logger.info("Token: {}", token);
//                    String username = jwtService.extractUserEmail(token);
//                    Long userId = jwtService.extractUserId(token);
//                    logger.info("Email: {}, id: {}", username, userId);
//                    UserDetails userDetails = User.builder()
//                            .email(username)
//                            .id(userId)
//                            .build();
////                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//
//                    accessor.setUser(usernamePasswordAuthenticationToken);
//                }
//                return message;
//            }
//        });
//    }
}