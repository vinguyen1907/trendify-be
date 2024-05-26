package com.project.uit.trendify.config;

import com.project.uit.trendify.controller.CartEventHandler;
import com.project.uit.trendify.service.interfaces.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer  {
    private final CartEventHandler cartEventHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        System.out.println("Register handler");
        registry.addHandler(cartEventHandler, "/ws")
                .setAllowedOrigins("*");
    }
}
