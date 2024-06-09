package de.eldecker.dhbw.spring.websockets.ws;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import de.eldecker.dhbw.spring.websockets.model.ChatNachricht;


/**
 * Controller für Chat in verschiedenen Kanälen mit STOMP über WebSockets.
 */
@Controller
public class UnterhaltungsController {

    private final static Logger LOG = LoggerFactory.getLogger( UnterhaltungsController.class );
    
    /** Set mit allen Kanalnamen (ein Element kann in einem Set nicht mehrfach vorkommen). */
    private Set<String> _kanalSet = new HashSet<>( 10 );
    
    
    /**
     * Methode empfängt Nachrichten von einem Client und sendet sie an {@code kanal} weiter.
     * 
     * @param kanal Name Chat-Kanal
     * 
     * @param chatNachricht Objekt enthält Nickname und Nachricht; wenn Kanal ganz neu ist,
     *                      dann wird die Nachricht geändert.
     * 
     * @return {@code chatNachricht}
     */
    @MessageMapping( "/chat/{kanal}" )
    @SendTo( "/topic/unterhaltung/{kanal}" )
    public ChatNachricht sendMessage( @DestinationVariable String kanal, 
                                      ChatNachricht chatNachricht ) {
        
        if ( _kanalSet.contains( kanal ) ) {
        
            LOG.info( "Nachricht auf bestehendem Kanal \"{}\" empfangen: {}", 
                      kanal, chatNachricht );            
        } else {
            
            _kanalSet.add( kanal );
            LOG.info( "Nachricht auf neuem  Kanal \"{}\" empfangen: {}", 
                      kanal, chatNachricht );            

            chatNachricht.setNachricht( "Hat einen neuen Kanal gestartet" );
            
            LOG.info( "Es gibt jetzt {} Kanäle.", _kanalSet.size() );
        }                
        
        return chatNachricht;
    }
    
}
