package de.eldecker.dhbw.spring.websockets.model;


@SuppressWarnings("serial")
public class ChatException extends Exception {

    /**
     * Konstruktor, um eine Fehlermeldung zu übergeben.
     *
     * @param nachricht Fehlermeldung
     */
    public ChatException( String nachricht ) {

        super( nachricht );
    }
    
}
