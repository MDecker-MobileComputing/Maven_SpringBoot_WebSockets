package de.eldecker.dhbw.spring.websockets.model;

import java.util.Objects;


/**
 * Modell-Klasse für einzelne Chat-Nachricht vom Client and Server und
 * vom Server an alle Clients.
 */
public class ChatNachricht {
    
    /**
     * Name des Chat-Teilnehmers, der die Nachricht auf den Kanal geschickt hat.                 
     */
    private String _nickname = "";
    
    /**
     * Inhalt der Nachricht.
     */
    private String _nachricht = "";
    
    
    /**
     * Default-Konstruktor
     */
    public ChatNachricht() {
        
        _nickname  = "";
        _nachricht = "";
    }
    

    /**
     * Konstruktor, um beide Attribute zu setzen
     * 
     * @param nickname Chat-Teilnehmer
     * 
     * @param nachricht Eigentlicher Inhalt (Text)
     */
    public ChatNachricht( String nickname, String nachricht ) {
    
        _nickname  = nickname;
        _nachricht = nachricht;
    }


    public String getNickname() {
        
        return _nickname;
    }


    public void setNickname( String nickname ) {
        
        _nickname = nickname;
    }


    public String getNachricht() {
        
        return _nachricht;
    }


    public void setNachricht( String nachricht ) {
        
        _nachricht = nachricht;
    }
    
    @Override
    public String toString() {
        
        return "Nachricht von \"" + _nickname + "\": \"" + _nachricht + "\"";
    }

    @Override
    public int hashCode() {
        
        return Objects.hash( _nickname, _nachricht );
    }

    @Override
    public boolean equals( Object obj ) {
        
        if ( obj == this ) { return true; }
        
        if ( obj == null ) { return false; }
        
        if ( obj instanceof ChatNachricht anderes ) {

            return Objects.equals( _nickname , anderes._nickname  ) &&                    
                   Objects.equals( _nachricht, anderes._nachricht );
            
        } else {
            
            return false;
        }
    }
            
}
