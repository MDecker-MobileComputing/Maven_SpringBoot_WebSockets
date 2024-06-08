package de.eldecker.dhbw.spring.websockets.ws;

import static org.springframework.messaging.simp.stomp.StompHeaderAccessor.wrap;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;


/**
 * Diese Service-Bean enthält Event-Handler für den Aufbau und die Beendung von
 * Verbindungen durch WebSocket-Clients.  
 */
@Service
public class WebSocketEventListener {

    private final static Logger LOG = LoggerFactory.getLogger( WebSocketEventListener.class );
    
    
    /** 
     * Set, das die IDs der aktiven Sitzungen speichert.
     * In einem Set kann ein Objekt nicht mehrfach enthalten sein. 
     */
    private Set<String> _sitzungsSet = new HashSet<>( 10 );
    
    
    /**
     * Event-Handler für Verbindung neuer WebSocket-Client, schreibt Sitzungs-ID auf
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
     * Event-Handler für den Fall, dass ein WebSocket-Client die Verbindung
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
