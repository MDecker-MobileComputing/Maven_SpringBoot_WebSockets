package de.eldecker.dhbw.spring.websockets.ws;

import de.eldecker.dhbw.spring.websockets.helferlein.ZufallsDelay;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;


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


    /**
     * Konstruktor für <i>Dependency Injection</i>
     */
    @Autowired
    public SchlagzeilenSender ( SimpMessagingTemplate messagingTemplate,
                                ZufallsDelay          zufallsDelay ) {

        _messagingTemplate = messagingTemplate;
        _zufallsDelay      = zufallsDelay;
    }


    /**
     * Die Methode wird regelmäßig ausgeführt, um eine zufällig ausgewählte Schlagzeile
     * über Websocket an alle Abonnenten zu senden.
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

        final String nachricht = "Nachricht erzeugt um " + (new Date());
        _messagingTemplate.convertAndSend( "/topic/schlagzeilen", nachricht );
        LOG.info( "Nachricht versendet: " + nachricht );

        try {

            Thread.sleep( _zufallsDelay.getZufallsDelayZeit() );
        }
        catch ( InterruptedException ex ) {

            LOG.error( "Fehler beim Warten: " + ex.getMessage() );
        }
    }

}
