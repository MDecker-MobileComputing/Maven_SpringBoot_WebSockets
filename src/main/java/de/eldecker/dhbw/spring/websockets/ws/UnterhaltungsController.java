package de.eldecker.dhbw.spring.websockets.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import de.eldecker.dhbw.spring.websockets.model.ChatNachricht;
import de.eldecker.dhbw.spring.websockets.service.ChatService;


/**
 * Controller für Chat in verschiedenen Kanälen mit STOMP über WebSockets.
 */
@Controller
public class UnterhaltungsController {

    private final static Logger LOG = LoggerFactory.getLogger( UnterhaltungsController.class );

    /** Service-Bean für Persistenz */
    @Autowired
    private ChatService _chatService;


    /**
     * Methode empfängt Nachrichten von einem Client und sendet sie an {@code kanalName} weiter.
     *
     * @param kanalName Name Chat-Kanal
     *
     * @param chatNachricht Objekt enthält Nickname und Nachricht; wenn Kanal ganz neu ist,
     *                      dann wird die Nachricht geändert.
     *
     * @return {@code chatNachricht}
     */
    @MessageMapping( "/chat/{kanalName}" )
    @SendTo( "/topic/unterhaltung/{kanalName}" )
    public ChatNachricht verberiteNachricht( @DestinationVariable String kanalName,
                                             ChatNachricht chatNachricht ) {

        if ( _chatService.kanalSchonVorhanden( kanalName) ) {

            LOG.info( "Nachricht auf bestehendem Kanal \"{}\" empfangen: {}",
                      kanalName, chatNachricht );

            _chatService.neuerChatBeitrag( kanalName, chatNachricht );

        } else {

            LOG.info( "Nachricht auf neuem  Kanal \"{}\" empfangen: {}",
                      kanalName, chatNachricht );

            _chatService.neuerChatKanal( kanalName );

            chatNachricht.setNachricht( "Hat einen neuen Kanal gestartet" );
        }

        return chatNachricht;
    }

}
