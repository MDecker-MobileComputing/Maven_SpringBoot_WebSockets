package de.eldecker.dhbw.spring.websockets.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


/**
 * Controller für STOMP auf Basis von WebSockets, der zu jeder Textnachricht vom
 * Client (Browser) eine Antwort zurückgesendet wird.
 * <br><br>
 *
 * siehe auch <a href="https://spring.io/guides/gs/messaging-stomp-websocket/">dieses Tutorial</a>
 */
@Controller
public class VokalersetzungsController {

    private final static Logger LOG = LoggerFactory.getLogger( VokalersetzungsController.class );
    
    @MessageMapping("/vokalersetzung_input")
    @SendTo("/topic/vokalersetzungs_output")
    public String vokaleErsetzen( String textVomClient ) throws Exception {
    
        final String textZumClient = textVomClient.replaceAll( "[aeiou]", "o" );
        
        LOG.info( "textVomClient=\"{}\", textZumClient=\"{}\".", 
                  textVomClient, textZumClient );
        
        return textZumClient;
    }

}
