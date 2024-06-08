package de.eldecker.dhbw.spring.websockets.ws;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;


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
     * STOMP-Endpunkt {@code /ws} registrieren.
     */
    @Override
    public void registerStompEndpoints( StompEndpointRegistry registry ) {

        registry.addEndpoint( "/ws" ).withSockJS();
    }

}
