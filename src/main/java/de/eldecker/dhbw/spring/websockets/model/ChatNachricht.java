package de.eldecker.dhbw.spring.websockets.model;


/**
 * Modell-Klasse für einzelne Chat-Nachricht vom Client. 
 */
public record ChatNachricht( String nickname, 
                             String nachricht )  {
}
