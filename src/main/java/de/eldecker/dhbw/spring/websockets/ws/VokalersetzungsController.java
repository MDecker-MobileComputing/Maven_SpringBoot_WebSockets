package de.eldecker.dhbw.spring.websockets.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import de.eldecker.dhbw.spring.websockets.model.VokalersetzungInput;


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
    
    
    /**
     * Controller-Methode für STOMP, die im vom Client gesendeten Text Vokalersetzungen vornimmt.
     * 
     * @param textVomClient 
     * @return

     */
    @MessageMapping("/vokalersetzung_input")
    @SendTo("/topic/vokalersetzungs_output")
    public String vokaleErsetzen( VokalersetzungInput inputObjekt )  {
    
        final String inputText = inputObjekt.text();
        final String vokal     = inputObjekt.vokal();
        
        LOG.info( "Im folgenden Text sind alle Vokale durch \"{}\" zu ersetzen: \"{}\"", 
                  vokal, inputText );
        
        final String textZumClient = inputText.replaceAll( "[aeiou]", vokal ); // regexp für alle Vokale
                                
        return textZumClient;
    }

}
