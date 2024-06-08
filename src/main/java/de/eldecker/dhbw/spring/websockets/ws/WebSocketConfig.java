package de.eldecker.dhbw.spring.websockets.ws;

import static org.springframework.messaging.simp.stomp.StompHeaderAccessor.wrap;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
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
    
    private final static Logger LOG = LoggerFactory.getLogger( WebSocketConfig.class );
    
    /** Collection, die die IDs der aktiven Sitzungen speichert. */
    private Set<String> _sitzungsSet = new HashSet<>( 10 );

    /**
     * STOMP-Endpunkt {@code /ws} registrieren.
     */
    @Override
    public void registerStompEndpoints( StompEndpointRegistry registry ) {

        registry.addEndpoint( "/ws" ).withSockJS();
    }
    
    
    /**
     * Event-Handler für Verbindung neuer Websocket-Client, schreibt Sitzungs-ID auf
     * Logger.
     * 
     * @param event Event für neue Sitzung
     */
    @EventListener
    public void handleSessionConnected( SessionConnectEvent event ) {
        
        final StompHeaderAccessor sha = wrap( event.getMessage() );
        
        final String sitzungsID = sha.getSessionId(); 
        
        final boolean nochNichtDa = _sitzungsSet.add(sitzungsID);
        if ( ! nochNichtDa ) {
            
            LOG.warn( "Sitzungs-ID \"{}\" für \"neue\" Verbindung war schon bekannt!", 
                      sitzungsID );             
        }
        
        LOG.info( "Neuer Client mit Sitzungs-ID \"{}\" hat sich verbunden.", sitzungsID );                         
        
        loggeAnzahlVerbindungen();
    }
    
    
    /**
     * Event-Handler für den Fall, dass ein Websocket-Client die Verbindung
     * beendet.
     * 
     * @param event Event für beendete Sitzung
     */
    @EventListener
    public void handleSessionDisconnect( SessionDisconnectEvent event ) {
        
        final StompHeaderAccessor sha = wrap( event.getMessage() );
        
        final String sitzungsID = sha.getSessionId();
                
        final boolean wurdeEntfernt = _sitzungsSet.remove( sitzungsID );
        if ( ! wurdeEntfernt ) {
            
            LOG.warn( "Sitzungs-ID \"{}\" von beendeter Verbindung war nicht bekannt!", 
                      sitzungsID );
        }
        
        LOG.info( "Client mit Sitzungs-ID \"{}\" hat Verbindung beendet.", sitzungsID );                   
        
        loggeAnzahlVerbindungen();
    }    

    
    /**
     * Schreibt aktuelle Anzahl der Verbindungen auf den Logger.
     */
    private void loggeAnzahlVerbindungen() {
        
        LOG.info( "Aktuelle Anzahl Verbindungen: {}", _sitzungsSet.size() );
    }
    
}
