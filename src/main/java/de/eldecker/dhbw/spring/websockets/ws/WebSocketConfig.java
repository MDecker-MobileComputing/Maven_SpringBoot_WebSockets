package de.eldecker.dhbw.spring.websockets.ws;


import org.springframework.context.annotation.Configuration;

import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;


/**
 * Konfiguration für Websockets.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        registry.addEndpoint( "/ws" ).withSockJS(); // STOMP-Endpunkt registrieren
    }


    @Override
    public void configureMessageBroker( MessageBrokerRegistry config ) {

        config.enableSimpleBroker( "/topic" ); // Enabling a simple in-memory broker
        config.setApplicationDestinationPrefixes( "/app" ); // Setting application destination prefixes
    }

}
