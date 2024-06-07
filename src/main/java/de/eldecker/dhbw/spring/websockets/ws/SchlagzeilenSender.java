package de.eldecker.dhbw.spring.websockets.ws;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;


@EnableScheduling
@Controller
public class SchlagzeilenSender {

    private final static Logger LOG = LoggerFactory.getLogger( SchlagzeilenSender.class );

    /** Bean, um Nachrichten per WebSocket zu versenden. */
    private final SimpMessagingTemplate _messagingTemplate;

    /**
     * Konstruktor für <i>Dependency Injection</i>
     */
    @Autowired
    public SchlagzeilenSender ( SimpMessagingTemplate messagingTemplate ) {

        _messagingTemplate = messagingTemplate;
    }



    @Scheduled( fixedDelayString = "#{zufallsDelay.getRandomDelay()}",
                initialDelayString = "#{zufallsDelay.getRandomDelay()}" )
    public void sendeSchlagzeile() {

        final String nachricht = "Nachricht erzeugt um " + (new Date());

        _messagingTemplate.convertAndSend( "/topic/schlagzeilen", nachricht );

        LOG.info( "Nachricht versendet: " + nachricht );
    }

}
