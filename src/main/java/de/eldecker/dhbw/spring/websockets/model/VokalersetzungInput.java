package de.eldecker.dhbw.spring.websockets.model;


/**
 * Ein Objekt dieser Klasse repräsentiert die Daten aus dem JSON-Objekt,
 * das der Client für die Vokalersetzung schickt.
 * 
 * @param text Text, in dem der Vokal ersetzt werden soll
 * 
 * @param vokal Vokal, auf den alle Vokale in {@code text} abgebildet werden sollen
 */
public record VokalersetzungInput( String text,
                                   char   vokal ) {
}
