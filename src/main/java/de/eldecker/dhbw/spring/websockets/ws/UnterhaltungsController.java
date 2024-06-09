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


@Controller
public class UnterhaltungsController {

    private final static Logger LOG = LoggerFactory.getLogger( UnterhaltungsController.class );
    
    /** Set mit allen Kanalnamen (ein Element kann in einem Set nicht mehrfach vorkommen). */
    private Set<String> _kanalSet = new HashSet<>( 10 );
    
    
    @MessageMapping( "/chat/{channel}" )
    @SendTo( "/topic/unterhaltung/{channel}" )
    public String sendMessage( @DestinationVariable String kanal, 
                               ChatNachricht chatNachricht ) {
        
        if ( _kanalSet.contains( kanal ) ) {
        
            LOG.info( "Nachricht auf bestehendem Kanal \"{}\" empfangen: {}", 
                      kanal, chatNachricht );            
        } else {
            
            _kanalSet.add( kanal );
            LOG.info( "Nachricht auf neuem  Kanal \"{}\" empfangen: {}", 
                      kanal, chatNachricht );            

            LOG.info( "Es gibt jetzt {} Kanäle.", _kanalSet.size() );
        }                
        
        return chatNachricht.toString();
    }
    
}
