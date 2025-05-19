package de.eldecker.dhbw.spring.websockets.db;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


/**
 * Diese Klasse sorgt dafür, dass die Attribute vom Typ {@code LocalDateTime}
 * als String im gut lesbaren Format gespeichert werden, z.B. {@code 23. Mai 2024, 23:10:03}.
 * 
 * @param <LocalDateTime> Datentyp für Attribute in Entity-Klassen, auf denen dieser Konverter
 *                        angewendet wird (wegen {@code autoApply = true} in der Annotation
 *                        {@code Converter} wird dies automatisch für alle Attribute von
 *                        diesem Typ in Entity-Klassen gemacht.
 *                        
 * @param <String> Typ der Datenbankspalte
 */
@Converter( autoApply = true )
public class LocalDateTimeStringConverter implements AttributeConverter<LocalDateTime, String> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "dd.MM.yyyy, HH:mm:ss" );

    
    /**
     * Diese Methode wird vor dem Speichern eines Attributs vom Typ {@code LocalDateTime} auf die Datenbank
     * aufgerufen, um es in einen String umzuwandeln.
     */
    @Override
    public String convertToDatabaseColumn( LocalDateTime attribute ) {
        
        return attribute != null ? attribute.format( formatter ) : null;
    }

    /**
     * Diese Methode wird aufgerufen, wenn ein Attributwert, der beim Speichern in einen String umgewandelt
     * wurde, wieder eingelesen werden soll.
     */
    @Override
    public LocalDateTime convertToEntityAttribute( String dbData ) {
        
        try {
            
            return dbData != null ? LocalDateTime.parse( dbData, formatter ) : null;
            
        } catch ( DateTimeParseException ex ) {
            
            throw new IllegalArgumentException( "Ungültiges Datumsformat", ex);
        }
    }
    
}