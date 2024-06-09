package de.eldecker.dhbw.spring.websockets.ws;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


/**
 * Konfiguration für WebSockets.
 * <br><br>
 *
 * In einer mit <i>Spring Boot</i> erstellten Anwendung wird standardmäßig
 * für WebSockets dieselbe Portnummer wie für HTTP verwendet, nämlich
 * {@code 8080}.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * STOMP-Endpunkt {@code /ws} registrieren. "STOMP" steht für
     * "Simple Text Oriented Messaging Protocol", welches hier über
     * WebSockets verwendet wird.
     */
    @Override
    public void registerStompEndpoints( StompEndpointRegistry registry ) {

        registry.addEndpoint( "/mein_ws" );
    }

    /**
     * Broker für {@code /topic} aktivieren.
     */
    @Override
    public void configureMessageBroker( MessageBrokerRegistry config ) {

        config.enableSimpleBroker( "/topic", "/queue" );
        config.setApplicationDestinationPrefixes( "/app" );
    }

}
