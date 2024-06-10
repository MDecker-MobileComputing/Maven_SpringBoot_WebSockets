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
     * STOMP-Endpunkt {@code /mein_ws} registrieren. "STOMP" steht für
     * "Simple Text Oriented Messaging Protocol", welches hier über
     * WebSockets verwendet wird.
     *
     * @param registry STOMP-Endpunkt-Registrierung, der Endpunkte
     *                 hinzugefügt werden
     */
    @Override
    public void registerStompEndpoints( StompEndpointRegistry registry ) {

        registry.addEndpoint( "/mein_ws" );
    }


    /**
     * Broker für {@code /topic} (mehrere Empfänger) und {@code /queue}
     * (einzelner Empfänger) aktivieren. Außerdem wird das Prefix
     * der Destination auf {@code /app} gesetzt; wenn die Clients
     * Nachrichten senden, dann muss die Destination mit {@code /app}
     * anfangen, z.B. {@code /app/vokalersetzung_input}.
     *
     * @param registry Message Broker, der konfiguriert wird
     */
    @Override
    public void configureMessageBroker( MessageBrokerRegistry registry ) {

        registry.enableSimpleBroker( "/topic", "/queue" );
        registry.setApplicationDestinationPrefixes( "/app" );
    }

}
