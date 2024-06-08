package de.eldecker.dhbw.spring.websockets.ws;

import de.eldecker.dhbw.spring.websockets.helferlein.Zaehler;
import de.eldecker.dhbw.spring.websockets.helferlein.ZufallsDelay;
import de.eldecker.dhbw.spring.websockets.logik.SchlagzeilenErzeuger;
import de.eldecker.dhbw.spring.websockets.model.Schlagzeile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Diese Klasse sendet regelmäßig eine zufällige Schlagzeile an alle Abonnenten.
 */
@EnableScheduling
@Controller
public class SchlagzeilenSender {

    private final static Logger LOG = LoggerFactory.getLogger( SchlagzeilenSender.class );

    /** Bean, um Nachrichten per WebSocket zu versenden. */
    private final SimpMessagingTemplate _messagingTemplate;

    /** Bean, um zufällige Delay-Zeiten zu erzeugen. */
    private final ZufallsDelay _zufallsDelay;
    
    /** Bean für die Erzeugung der zufälligen Negativ-Schlagzeilen. */
    private final SchlagzeilenErzeuger _schlagzeilenErzeugen;
    
    /** Bean, um Java-Objekt mit Jackson in JSON zumzuwandeln. */ 
    private final ObjectMapper _objectMapper;
    
    /** 
     * Instanz einer Prototype-Bean um die Anzahl der verschickten Nachrichten zu zählen.
     */
    private final Zaehler _zaehler;


    /**
     * Konstruktor für <i>Dependency Injection</i>
     */
    @Autowired
    public SchlagzeilenSender ( SimpMessagingTemplate messagingTemplate,
                                ZufallsDelay          zufallsDelay, 
                                SchlagzeilenErzeuger  schlagzeilenErzeuger,
                                ObjectMapper          objectMapper,
                                Zaehler               zaehler ) {

        _messagingTemplate    = messagingTemplate;
        _zufallsDelay         = zufallsDelay;
        _schlagzeilenErzeugen = schlagzeilenErzeuger;
        _objectMapper         = objectMapper;
        _zaehler              = zaehler;
    }


    /**
     * Die Methode wird regelmäßig ausgeführt, um eine zufällig ausgewählte Schlagzeile
     * über WebSocket an alle Abonnenten von {@code /topic/schlagzeilen} zu senden.
     * <br><br>
     *
     * Annotation {@code Scheduled} für periodische Ausführung von Methoden:
     * <ul>
     * <li>{@code fixedDelay}: Diese Zeit beginnt, sobald der vorherige Methodenaufruf
     * beendet ist; in der Methode wird eine zufällige Wartezeit eingebaut,
     * so dass die Methode nicht exakt alle 5 Sekunden aufgerufen wird. Es gibt auch
     * nocht {@code fixedRate}, bei der die Methode exakt alle 5 Sekunden aufgerufen wird,
     * egal wie lange die Ausführung dauert.
     * </li>
     * <li>{@code initialDelay}: Zeit, die nach dem Start der Anwendung gewartet wird,
     * bevor die Methode zum ersten Mal aufgerufen wird.
     * </li>
     * </ul>
     */
    @Scheduled( fixedDelay = 5_000, initialDelay = 5_000 )
    public void sendeSchlagzeile() {

        final Schlagzeile schlagzeile = _schlagzeilenErzeugen.erzeugeZufallsSchlagzeile();
        
        String jsonPayload = "";
        try {

            jsonPayload = _objectMapper.writeValueAsString( schlagzeile );
            
            _messagingTemplate.convertAndSend( "/topic/schlagzeilen", jsonPayload );
                        
            final int anzahlNachrichten = _zaehler.inkrement();
            
            LOG.info( "Nachricht Nr. {} versendet: {}", 
                      anzahlNachrichten, schlagzeile.schlagzeile() );
            
            final int delayZeit = _zufallsDelay.getZufallsDelayZeit(); 
            Thread.sleep( delayZeit );                        
        } 
        catch ( JsonProcessingException ex ) {
            
            LOG.error( "Fehler beim Konvertieren von Schlagzeile nach JSON: " + ex.getMessage() );            
        } 
        catch ( InterruptedException ex ) {
        
            LOG.error( "Fehler beim Warten: " + ex.getMessage() );
        }
    }

}
