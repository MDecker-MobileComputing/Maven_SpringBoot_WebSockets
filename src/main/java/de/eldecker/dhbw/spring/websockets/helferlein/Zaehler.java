package de.eldecker.dhbw.spring.websockets.helferlein;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * Bean-Klasse, die einen einfachen Ganzzahlzähler repräsentiert.
 * Damit verschiedene Dinge gezählt werden können, hat diese Bean
 * den <b>Scope "prototype"</b> (siehe Annotation {@code Scope}), 
 * damit jeder Verwender eine eigene Instanz bekommt.
 */
@Component
@Scope("prototype")
public class Zaehler {

    /** Zähler. */
    private int _zaehler = 0; 
    
    
    /**
     * Zähler um {@code +1} erhöhen.
     * 
     * @return Wert nach Erhöhung Zähler
     */
    public int inkrement() {
        
        return ++_zaehler;
    }
    
    
    /**
     * Getter für aktuellen Zählenwert.
     * 
     * @return Zählerwert, mindestens {@code 0}
     */
    public int getWert() {
        
        return _zaehler;
    }
    
}
