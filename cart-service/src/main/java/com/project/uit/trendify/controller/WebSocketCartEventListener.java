package com.project.uit.trendify.controller;

import com.project.uit.trendify.common.lib.entity.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.context.event.EventListener;

@Component
@RequiredArgsConstructor
public class WebSocketCartEventListener {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketCartEventListener.class);
    private final CartController cartController;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) throws InterruptedException {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) headerAccessor.getUser();
        User user = (User) usernamePasswordAuthenticationToken.getPrincipal();
        logger.info("Connect: " + headerAccessor.getUser());
        logger.info("Connect -- Session ID: {}, User ID: {}", sessionId, user.getId());
        Long userId = user.getId();
//        cartController.sendInitialCartData(sessionId, userId);
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        logger.info("Disconnected: " + sessionId);
        logger.info("Close status: " + event.getCloseStatus());
    }
}
