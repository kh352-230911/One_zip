package com.sh.onezip.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class StompConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        registry.addEndpoint("/stomp")
                .withSockJS(); // websocket 미지원 브라우져 처리
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry){
        // 1. simple broker 전용
        registry.enableSimpleBroker("/sub"); // subcribe
        // 2. @MessageMapping Handler url 등록
        registry.setApplicationDestinationPrefixes("/pub"); // publisher
    }
}
