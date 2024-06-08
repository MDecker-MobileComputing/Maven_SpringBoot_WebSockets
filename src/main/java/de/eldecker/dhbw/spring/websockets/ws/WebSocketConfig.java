package de.eldecker.dhbw.spring.websockets.ws;


import org.springframework.context.annotation.Configuration;

import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;


/**
 * Konfiguration für Websockets.
 * <br><br>
 *
 * In einer mit <i>Spring Boot</i> erstellten Anwendung wird standardmäßig
 * für Websockets dieselbe Portnummer wie für HTTP verwendet, nämlich
 * {@value 8080}.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * STOMP-Endpunkt {@code /ws} registrieren.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        registry.addEndpoint( "/ws" ).withSockJS();
    }


    @Override
    public void configureMessageBroker( MessageBrokerRegistry config ) {

        config.enableSimpleBroker( "/topic" ); // Enabling a simple in-memory broker
        config.setApplicationDestinationPrefixes( "/app" ); // Setting application destination prefixes
    }

}
