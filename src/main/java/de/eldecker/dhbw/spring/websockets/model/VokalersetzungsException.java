package de.eldecker.dhbw.spring.websockets.model;


/**
 * Eigene Exception-Klasse für Fehler bei Vokalersetzung.
 */
@SuppressWarnings("serial")
public class VokalersetzungsException extends Exception {

    /**
     * Konstruktor, um eine Fehlermeldung zu übergeben.
     *
     * @param nachricht Fehlermeldung
     */
    public VokalersetzungsException( String nachricht ) {

        super( nachricht );
    }

}
