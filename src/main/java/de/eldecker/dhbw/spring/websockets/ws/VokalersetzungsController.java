package de.eldecker.dhbw.spring.websockets.ws;

import de.eldecker.dhbw.spring.websockets.model.VokalersetzungsException;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import de.eldecker.dhbw.spring.websockets.model.VokalersetzungInput;


/**
 * Controller für STOMP auf Basis von WebSockets: Zu jedem als JSON vom Client empfangenen
 * {@link VokalersetzungInput}-Objekt wird das "Übersetzungsergebnis" zurückgesendet.
 * <br><br>
 *
 * <b>Referenzen:</b>
 * <ul>
 * <li><a href="https://spring.io/guides/gs/messaging-stomp-websocket/">Offizielles Tutorial auf <i>spring.io</i></a></li>
 * <li><a href="https://www.baeldung.com/spring-websockets-sendtouser" >Tutorial auf <i>baeldung.com</i></a></li>
 * </ul>
 */
@Controller
public class VokalersetzungsController {

    private final static Logger LOG = LoggerFactory.getLogger( VokalersetzungsController.class );

    /**
     * Map bildet Sitzungs-ID auf Zähler ab; um zu kontrollieren, dass für eine Sitzung nicht
     * mehr als eine bestimmte Anzahl "Übersetzungen" durchgeführt werden.
     * Beispiel für Sitzungs-ID: {@code e8656a71-eb77-aa66-c50a-ff68cda8d989}
     */
    private Map<String,Integer> _sessionAufZaehlerMap = new HashMap<>( 10 );


    /**
     * Controller-Methode für STOMP, die im vom Client gesendeten Text Vokalersetzungen vornimmt.
     * Alle Vokale im übergebenen Text werden durch einen bestimmten anderen Vokal ersetzt
     * (Prinzip "Drei Chinesen mit dem Kontrabass").
     * <br><br>
     *
     * <b>Beispiel:</b>
     * <ul>
     * <li>Input: {@code inputObjekt.text="Hello World"} und {@code inputObjekt.vokal="e"}</li>
     * <li>Output: "Helle Werld"</li>
     * </ul>
     *
     * @param inputObjekt Objekt mit Text und Zielvokal
     *
     * @param sessionId ID der Nutzersitzung, z.B. {@code e8656a71-eb77-aa66-c50a-ff68cda8d989}
     *
     * @return Ergebnis String mit "Übersetzungsergebnis", wird an STOMP-Client geschickt.
     *
     * @throws VokalersetzungsException wenn mehr als drei Anfragen pro Sitzung gestellt werden
     */
    @MessageMapping( "/vokalersetzung_input" )
    @SendToUser( "/queue/vokalersetzungs_output" )
    public String vokaleErsetzen( VokalersetzungInput inputObjekt,
                                  @Header("simpSessionId") String sessionId )
            throws VokalersetzungsException {

        final int anzahlRequests = getRequestZaehler( sessionId );
        if ( anzahlRequests > 3 ) {

            throw new VokalersetzungsException( "Sie haben die 3 Übersetzungen schon verbraucht." );
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
     * Exception-Handler für Vokalersetzungsfehler. Es wird eine Fehlermeldung
     * an das Topic {@code /queue/vokalersetzungs_fehler} gesendet.
     *
     * @param ex Exception-Objekt
     *
     * @param sessionId ID der Nutzersitzung, in der der Fehler aufgetreten ist,
     *                  z.B. {@code e8656a71-eb77-aa66-c50a-ff68cda8d989}
     */
    @MessageExceptionHandler
    @SendToUser( "/queue/vokalersetzungs_fehler" )
    public String exceptionBehandeln( Throwable ex,
                                      @Header("simpSessionId") String sessionId ) {

        LOG.error( "Fehler bei Vokalersetzungs in Sitzung \"{}\": {}",
                   sessionId, ex.getMessage() );

        return ex.getMessage();
    }


    /**
     * Erhöht den Zähler für die WebSocket/STOMP-Sitzung. Der Zähler wird benötigt,
     * um zu gewährleisten, dass pro Sitzung nur eine bestimmte Anzahl von
     * "Übersetzungen" ausgeführt werden.
     *
     * @param sessionId ID der Nutzersitzung, z.B. {@code e8656a71-eb77-aa66-c50a-ff68cda8d989}
     *
     * @return Um {@code +1} erhöhter Zähler für die Sitzung; wenn {@code sessionId}
     *         noch nicht bekannt war, dann wird ein Zähler angelegt und {@code 1}
     *         zurückgegeben.
     */
    private int getRequestZaehler( String sessionId ) {

        if ( _sessionAufZaehlerMap.containsKey( sessionId ) ) {

            int zaehler = _sessionAufZaehlerMap.get( sessionId );
            zaehler++;
            _sessionAufZaehlerMap.put( sessionId, zaehler );

            LOG.info( "Request Nr. {} für SessionId=\"{}\".",
                      zaehler, sessionId );

            return zaehler;

        } else {

            LOG.info( "Neuen Zaehler fuer SessionID=\"{}\" angelegt.",
                      sessionId );

            _sessionAufZaehlerMap.put( sessionId, 1 );

            return 1;
        }
    }

}
