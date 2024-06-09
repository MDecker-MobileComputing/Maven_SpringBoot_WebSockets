package de.eldecker.dhbw.spring.websockets.model;


/**
 * Eigene Exception-Klasse für Vokalersetzungsfehler.
 */
public class VokalersetzungsException extends Exception {

    public VokalersetzungsException( String message ) {

        super( message );
    }

}