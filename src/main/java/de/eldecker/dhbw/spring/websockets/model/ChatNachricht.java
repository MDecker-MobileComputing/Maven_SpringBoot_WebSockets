package de.eldecker.dhbw.spring.websockets.model;


/**
 * Modell-Klasse für einzelne Chat-Nachricht vom Client and Server und
 * vom Server an alle Clients.
 * 
 * @param nickname Name des Chat-Teilnehmers, der die Nachricht auf den Kanal
 *                 geschickt hat
 * 
 * @param nachricht Inhalt der Nachricht
 */
public record ChatNachricht( String nickname, 
                             String nachricht )  {
}
