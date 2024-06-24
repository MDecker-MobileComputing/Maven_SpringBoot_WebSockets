package de.eldecker.dhbw.spring.websockets.model;

/**
 * Ein Objekt dieser Record-Klasse enthält eine zufällig erzeugte Schlagzeile.
 * 
 * @param schlagzeile Text der (Negativ-)Schlagzeile
 * 
 * @param istInland {@code true} wenn {@code schlagzeile} einen Ort in Deutschland
 *                  enthält, sonst {@code false} für Auslandsschlagzeile 
 */
public record Schlagzeile( String  schlagzeile, 
                           boolean istInland   
                         ) {
}
