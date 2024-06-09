package de.eldecker.dhbw.spring.websockets.model;


/**
 * Eigene Exception-Klasse für Vokalersetzungsfehler.
 */
public class VokalersetzungsException extends Exception {

    /**
     * Konstruktor, um eine Fehlermeldung zu übergeben.
     *
     * @param message Fehlermeldung
     */
    public VokalersetzungsException( String message ) {

        super( message );
    }

}
