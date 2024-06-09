package de.eldecker.dhbw.spring.websockets.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import de.eldecker.dhbw.spring.websockets.model.ChatNachricht;


@Controller
public class UnterhaltsungController {

    private final static Logger LOG = LoggerFactory.getLogger( UnterhaltsungController.class );
    
    
    @MessageMapping( "/chat/{channel}" )
    @SendTo( "/topic/unterhaltung/{channel}" )
    public String sendMessage( @DestinationVariable String kanal, ChatNachricht chatNachricht ) {
        
        LOG.info( "Nachricht auf Kanal \"{}\" empfangen: {}", kanal, chatNachricht );
        
        return chatNachricht.toString();
    }
}
