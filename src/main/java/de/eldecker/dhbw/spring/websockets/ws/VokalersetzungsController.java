package de.eldecker.dhbw.spring.websockets.ws;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import de.eldecker.dhbw.spring.websockets.model.VokalersetzungInput;


/**
 * Controller für STOMP auf Basis von WebSockets: Zu jedem als JSON vom Client empfangenen
 * {@link VokalersetzungInput}-Objekt wird das "Übersetzungsergebnis" zurückgesendet.
 * <br><br>
 *
 * siehe auch <a href="https://spring.io/guides/gs/messaging-stomp-websocket/">dieses Tutorial</a>
 */
@Controller
public class VokalersetzungsController {

    private final static Logger LOG = LoggerFactory.getLogger( VokalersetzungsController.class );
    
    /** 
     * Map bildet Session-ID auf Zähler ab; um zu kontrollieren, dass für eine Sitzung nicht
     * mehr als eine bestimmte Anzahl "Übersetzungen" durchgeführt werden. 
     */
    private Map<String,Integer> _sessionAufZaehlerMap = new HashMap<>( 10 );
    
    
    /**
     * Controller-Methode für STOMP, die im vom Client gesendeten Text Vokalersetzungen vornimmt.
     * 
     * @param inputObjekt Objekt mit Text und Zielvokal
     *  
     * @return Ergebnis String mit "Übersetzungsergebnis", 
     *         z.B. "Helle Werld" für {@code inputObjekt.text="Hello World"} und 
     *         {@code inputObjekt.vokal="e"}
     */
    @MessageMapping( "/vokalersetzung_input" )
    @SendTo( "/topic/vokalersetzungs_output" )
    public String vokaleErsetzen( VokalersetzungInput inputObjekt,
                                  @Header("simpSessionId") String sessionId )  {
    
        final int anzahlRequests = getRequestZaehler( sessionId );
        if ( anzahlRequests > 3 ) {
            
            return "Sie haben die 3 Übersetzungen schon verbraucht";
        }
        
        final String inputText = inputObjekt.text();
        final char   vokal     = inputObjekt.vokal();
        
        LOG.info( "Im folgenden Text sind alle Vokale durch \"{}\" zu ersetzen: \"{}\"", 
                  vokal, inputText );
        
        final String textZumClient = inputText.replaceAll( "[aeiou]", vokal + "" ); // regexp für alle Vokale

        LOG.info( "Sende Ergebnis Vokalersetzung zum Client: \"{}\"", textZumClient ); 
       
        return textZumClient;
    }

    
    /**
     * Erhöht den Zähler für die WebSocket/STOMP-Sitzung.
     * 
     * @param sessionId Session-ID
     * 
     * @return Um {@code +1} erhöhter Zähler; wenn {@code sessionId} noch
     *         nicht bekannt war, dann wird ein Zähler angelegt und {@code 1}
     *         zurückgegeben.
     */
    private int getRequestZaehler( String sessionId ) {
        
        if ( _sessionAufZaehlerMap.containsKey( sessionId ) ) {
            
            int zaehler = _sessionAufZaehlerMap.get( sessionId );
            zaehler++;
            _sessionAufZaehlerMap.put( sessionId, zaehler );
            
            LOG.info( "Request Nr. {} für SessionId=\"{}\".", zaehler, sessionId );
            
            return zaehler;
            
        } else {
            
            LOG.info( "Neuen Zaehler fuer SessionID=\"{}\" angelegt.", sessionId );
            _sessionAufZaehlerMap.put( sessionId, 1 );
            return 1;
        }
    }
    
}
